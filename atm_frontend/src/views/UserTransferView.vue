<template>
    <div class="container">
      <div class="numberboard">
        <Keypad @key-click="handleKeyClick"></Keypad>
      </div>
      <div class="desktopBack">
      <atmheader></atmheader>
      <div v-show ="isShow" style="position: absolute; left:700px; top:400px; z-index: 9999;">
        <i class="el-icon-loading" style="font-size: 100px;"></i>
      </div>
      <div class="countdown-container">
        <div class="countdown">
         <div class="countdown-timer">倒计时:{{ this.countdownTime }} s</div>
        </div>
      </div>
      <label v-show = "isAmount" class="input-name2" style = "width: 600px;left: 540px;top: 320px;">请输入转账金额</label>
      <input class="input-text2" style = "height: 60px;left: 470px;top: 430px;" v-model="str"  readonly>
      <label v-show = "isCardId" class="input-name2" style = "width: 800px;left: 380px;top: 320px;">请输入对方账户(16位卡号,仅限本行)</label>
      <label class="input-name2" style="left:435px;top:800px; font-size: 40px;width: 1000px;">单次转账限额为1000元人民币</label>
      <!-- <label class="input-name2" style="left:428px;top:860px; font-size: 40px;width: 1000px;">单日转账限额为10000元人民币</label> -->
        <div>
          <el-button v-show = "isButt" class="butt" style="top: 650px; margin-left:0px;" @click="refreshAmount">
            <label class="fontStyle"><i class="el-icon-back"></i>重置金额</label>
          </el-button>
          <el-button class="butt" style="top: 850px; margin-left:0px;" @click="navigateToDeskTopAndReturnCard">
            <label class="fontStyle"><i class="el-icon-back"></i>退卡</label>
          </el-button>
          <el-button class="butt" style="top: 650px; margin-left:1130px;" @click="navigateToUserCheckBalance">
            <label class="fontStyle">查看余额<i class="el-icon-right"></i></label>
          </el-button>
          <el-button class="butt" style="top: 850px; margin-left:1130px;" @click="navigateToUserOperation">
            <label class="fontStyle">退出转账<i class="el-icon-right"></i></label>
          </el-button>
        </div>
      </div>
      <div v-if="messageDialog" class = "dialog-overlay">
        <div  class="custom-dialog" :class="{'dialog-left': dialogLeft}" style="height: 400px;">
          <!-- 对话框内容 -->
          <span class="dialog-title">重要提示</span>
          <div class="dialog-content">{{ this.messageContent }}<br>
          </div>
        </div>
      </div>
    </div>
</template>
<script>
import atmheader from '../components/atmHeader.vue'
import Keypad from '@/components/KeyPad.vue'
import request from '@/utils/request'
import '@/assets/CSS/messageDialog.css'
import '@/assets/CSS/timeCounter.css'

export default {
  components: {
    atmheader,
    Keypad
  },
  data () {
    return {
      countdownTime: 60,
      timer: null,
      str: '', // 转账金额与对方账户的传递者
      counter: 0, // 实现转账金额与对方账户值的切换
      amount: '', // 转账金额
      cardid2: '', // 对方账户
      messageContent: '',
      messageDialog: false,
      isAmount: true,
      isCardId: false,
      isShow: false,
      isButt: false // 重置金额按钮的显示
    }
  },
  mounted () {
    // ===========事件监听=========//
    this.startTimer()
    window.addEventListener('click', this.resetTimer)
    window.addEventListener('keydown', this.resetTimer)
    // ============================//
    this.isAmount = true
    this.isCardId = false
    this.isButt = false
  },
  methods: {
    navigateToDeskTopAndReturnCard () {
      // 执行页面跳转逻辑
      // 执行退卡动画
      this.$router.push({
        name: 'desktop',
        params: {
          triggerMethod: true // 条件信息，设置为 true 表示满足条件}
        }
      })
    },
    navigateToDeskTopAndEatCard () {
      if (this.$route.name !== 'desktop') {
        this.$router.push({
          name: 'desktop',
          params: {
            eatCard: true
          }
        })
      }
    },
    navigateToUserConfirmInfo (amount, cardid2) {
      this.$router.push({
        name: 'userconfirminfo',
        params: {
          amount: amount,
          cardid2: cardid2
        }
      })
    },
    navigateToUserOperation () {
      this.$router.push('/useroperation')
    },
    navigateToUserCheckBalance () {
      this.$router.push('/usercheckbalance')
    },
    refreshAmount () {
      this.counter = 0 // 恢复输入金额
      this.isAmount = true
      this.isCardId = false
      this.isButt = false
      this.str = ''
    },
    handleKeyClick (key) {
      if (key === '退格') {
        this.str = this.str.slice(0, -1)
      } else if (key === '确认') {
        if (this.counter === 0) {
          // 正在输入金额
          // 检查金额
          if (this.checkAmout() === 1) {
            this.messageContent = '输入金额不能为0或者空,请重新输入'
            this.messageDialog = true
            setTimeout(() => {
              this.messageDialog = false
            }, 3000)
          } else if (this.checkAmout() === 2) {
            this.messageContent = '超过当次转账限额'
            this.messageDialog = true
            setTimeout(() => {
              this.messageDialog = false
            }, 3000)
          } else {
            this.counter = 1 // 换值
            this.isButt = true
            this.amount = this.str
            this.str = ''
            this.isAmount = false
            this.isCardId = true
          }
        } else {
          this.cardid2 = this.str
          this.str = ''
          if (this.checkCardId2(this.cardid2) === 1) {
            // 核验cardid2是否存在，存在生成确认页面，不存在直接提示账户出错
            this.checkCardId(this.cardid2)
          } else if (this.checkCardId2(this.cardid2) === 3) {
            this.messageContent = '请输入16位银行卡号'
            this.messageDialog = true
            setTimeout(() => {
              this.messageDialog = false
            }, 3000)
          } else {
            this.messageContent = '不能给本账户转账'
            this.messageDialog = true
            setTimeout(() => {
              this.messageDialog = false
            }, 2000)
          }
        }
        // 执行确认操作
      } else {
        this.str += key
      }
    },
    navigateToUserTakeSuccess (takeAmount, recordid) {
      console.log(takeAmount)
      this.$router.push({
        name: 'usertakesuccess',
        params: {
          takeAmount: Number(takeAmount),
          recordid: recordid
        }
      })
    },
    checkAmout () {
      if (Number(this.str) === 0 && this.takeAmount === '') {
        // 是0或者空
        return 1
      } else if (Number(this.str) > 1000) {
        // 不是100 的整数
        return 2
      } else {
        return 3
      }
    },
    checkCardId2 (cardid2) {
      const cardInfo = JSON.parse(sessionStorage.getItem('cardInfo'))
      const cardId = cardInfo.cardId
      // 检查是不是16位
      if (cardid2.length === 16) {
        if (cardid2 === cardId) {
          return 2
        } else return 1
      } else {
        return 3
      }
    },
    checkCardId (cardId) {
      const url = '/card/check-id?cardid=' + cardId
      request.get(url).then(res => {
        if (res.code === '0' || res.code === '2') {
          // 账户存在生成订单
          // console.log('我是真的')
          this.navigateToUserConfirmInfo(Number(this.amount), this.cardid2)
        } else {
          this.messageContent = '转账账号非本行卡号,请仔细核对'
          this.messageDialog = true
          setTimeout(() => {
            this.messageDialog = false
          }, 3000)
        }
      })
    },
    startTimer () {
      this.timer = setInterval(() => {
        if (this.countdownTime > 0) {
          this.countdownTime--
          if (this.countdownTime === 10) {
            this.messageContent = '倒计时结束之前还未操作将会被吞卡,请执行您的操作'
            this.messageDialog = true
            this.oneORtwo = false
            setTimeout(() => {
              this.messageDialog = false
              this.cardPass = ''
            }, 3000)
          }
        } else {
          clearInterval(this.timer)
          this.navigateToDeskTopAndEatCard()
        }
      }, 1000)
    },
    resetTimer () {
      this.countdownTime = 60
    }
  },
  beforeDestroy () {
    // 在组件销毁前移除事件监听器以及计时
    clearInterval(this.timer)
    window.removeEventListener('click', this.resetTimer)
    window.removeEventListener('keydown', this.resetTimer)
  }
}
</script>
<style>
.desktopBack {
  position: relative;
  width: 1440px;
  height: 1024px;
  background: linear-gradient(118.86deg, rgb(14, 20, 112) 21.912%,rgba(23, 21, 15, 0) 98.654%);
  border: 10px solid rgb(0, 0, 0);
  border-radius: 20px;
}
.container {
    display: flex;
    justify-content: left; /* 水平居中 */
    align-items: center; /* 垂直居中 */
}

.input-name2 {
    /* label： */
    position: absolute;
    color: rgb(8, 20, 21);
    font-family:Cambria, Cochin, Georgia, Times, 'Times New Roman', serif;
    font-size: 45px;
    font-weight: 400;
    line-height: 86px;
    letter-spacing: 0px;
    text-align: left;
    text-shadow: 2px 2px 4px rgba(0, 49, 182, 0.5);
}
.input-text2 {
    position: absolute;
    width: 465px;
    background: rgb(195, 170, 170);
    border-radius: 20px;
    text-align: center;
    font-size: 45px;
}

/* 按钮 */
.butt {
    position: absolute;
    width: 272px;
    height: 87px;
    left: 20px;
    background: linear-gradient(135.00deg, rgb(118, 143, 71) 45.359%,rgba(71, 75, 143, 0) 95.359%);
    border-radius: 20px;
    text-align: center;
    display: flex;
    justify-content: center; /* 水平居中 */
    align-items: center; /* 垂直居中 */
}
/* 按钮上的字体 */
.fontStyle {
    font-family: 楷体;
    font-size: 50px;
    font-weight: 400;
    color: black;
    line-height: 86px;
    letter-spacing: 0px;
    text-align: center;
    text-shadow: 2px 2px 4px rgba(3, 75, 21, 0.5);
}

.input-container {
  position: relative;
  display: flex;
  justify-content: center; /* 水平居中 */
  flex-direction: column;
  width: 70%;
}

.number-input {
  position: relative;
  margin-left: 20px;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  margin-bottom: 10px;
  height: 50px;
  width: 20%;
  background-color: cornflowerblue;
  font-size: 40px;
}

.custom-dialog .el-dialog__header {
  background-color: rgb(172, 140, 140);
}

.custom-dialog .el-dialog__body {
  background-color: rgb(172, 140, 140);
}

.custom-dialog .el-dialog__footer {
  background-color: rgb(172, 140, 140);
  text-align: center;
}
.dialog-content {
  font-size: 72px;
  font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
  color: rgb(224, 32, 32);
  text-align: center;
}

.numberboard {
    /* 输入金额盘背景 */
    position: absolute;
    width: 628px;
    height: 780px;
    left: 1600px;
    top: 194px;
    background: linear-gradient(90.00deg, rgb(179, 139, 139),rgba(255, 255, 255, 0) 100%);
    opacity: 0.54;
    border-radius: 20px;
    border: 6px solid #c0c0c0;
    border-style: rgb(31, 32, 51);
    display: flex;
    justify-content: center; /* 水平居中 */
    align-items: center; /* 垂直居中 */
}
</style>
