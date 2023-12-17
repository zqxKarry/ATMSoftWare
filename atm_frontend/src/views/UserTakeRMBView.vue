<template>
    <div class="container">
      <div class="numberboard">
        <KeyPad @key-click="handleKeyClick"></KeyPad>
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
      <label class="input-name1">请选择或者输入取款金额</label>
      <input class="input-text1" v-model="takeAmount"  readonly>
      <label class="input-name1" style="left:440px;top:800px; font-size: 40px;">本ATM机只提供100元人民币面额</label>
      <label class="input-name1" style="left:435px;top:860px; font-size: 40px;width: 1000px;">单次取款最大额度为1000元人民币</label>
        <div>
          <el-button class="butt" style="top: 450px;margin-left: 0px;" @click="return100">
            <label class="fontStyle">100</label>
          </el-button>
          <el-button class="butt" style="top: 650px;margin-left: 0px;" @click="return500">
            <label class="fontStyle">500</label>
          </el-button>
          <el-button class="butt" style="top: 850px;margin-left: 0px;" @click="return1000">
            <label class="fontStyle">1000</label>
          </el-button>
          <el-button class="butt" style="top: 850px; margin-left:1130px;" @click="finished">
            <label class="fontStyle">确认<i class="el-icon-right"></i></label>
          </el-button>
          <el-button class="butt" style="top: 650px; margin-left:1130px;" @click="navigateToUserOperation">
            <label class="fontStyle">返回<i class="el-icon-right"></i></label>
          </el-button>
        </div>
      </div>
      <div v-if="countMoneyDialog" class = "dialog-overlay">
        <div class="custom-dialog" :class="{'dialog-left': dialogLeft}" style="height: 500px;">
          <span class="dialog-title">正在点钞</span>
          <div style="height: 400px;">
            <i class="el-icon-loading" style="font-size: 50px;"></i>
            <!-- 对话框内容 -->
            <div style="height:30px"></div>
            <div style="display:flex">
              <div style="width:35%"></div>
              <countmoney ref="countmoneyref"></countmoney>
              <div style="width:30%"></div>
            </div>
          </div>
      </div>
      </div>
      <div v-if="messageDialog" class = "dialog-overlay">
        <div class="custom-dialog" :class="{'dialog-left': dialogLeft}" style="height: 400px;">
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
import countmoney from '../components/countMoney.vue'
import request from '@/utils/request'
import KeyPad from '@/components/KeyPad.vue'
import '@/assets/CSS/messageDialog.css'
import '@/assets/CSS/timeCounter.css'

export default {
  components: {
    atmheader,
    countmoney,
    KeyPad
  },
  data () {
    return {
      countdownTime: 60,
      timer: null,
      takeAmount: '',
      countMoneyDialog: false,
      messageContent: '',
      messageDialog: false,
      countMoneyTime: 2000,
      isShow: false
    }
  },
  mounted () {
    // ===========事件监听=========//
    this.startTimer()
    window.addEventListener('click', this.resetTimer)
    window.addEventListener('keydown', this.resetTimer)
    // ============================//
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
    navigateToUserOperation () {
      this.$router.push('/useroperation')
    },
    return100 () {
      this.takeAmount = '100'
    },
    return500 () {
      this.takeAmount = '500'
    },
    return1000 () {
      this.takeAmount = '1000'
    },
    handleKeyClick (key) {
      if (key === '退格') {
        this.takeAmount = this.takeAmount.slice(0, -1)
      } else if (key === '确认') {
        this.isShow = true
        setTimeout(() => {
          this.finished()
        }, 2000)
        // 执行确认操作
      } else {
        this.takeAmount += key
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
    async finished () {
    //   await this.beginCountMoney()
      this.isShow = true
      setTimeout(() => {
        this.isShow = false
      }, 2000)
      if (this.isEmptyOROver() === 1) {
        this.messageContent = '取款操作不能选择0元,系统无法执行'
        this.messageDialog = true
        setTimeout(() => {
          this.messageDialog = false
        }, 3000)
        this.takeAmount = ''
      } else if (this.isEmptyOROver() === 2) {
        this.messageContent = '最小面额是100元人民币,请重新选择金额'
        this.messageDialog = true
        setTimeout(() => {
          this.messageDialog = false
        }, 3000)
        this.takeAmount = ''
      } else if (this.isEmptyOROver() === 3) {
        this.messageContent = '单次取款限额1000元'
        this.messageDialog = true
        setTimeout(() => {
          this.messageDialog = false
        }, 3000)
        this.takeAmount = ''
      } else {
        // 去取钱
        await this.takeRMB()
      }
    },
    async beginCountMoney () {
      return new Promise((resolve, reject) => {
        this.countMoneyDialog = true
        this.countMoneyTime = this.takeAmount / 1000 * 1000
        setTimeout(() => {
          this.countMoneyDialog = false
          resolve() // 表示异步操作完成
        }, this.countMoneyTime)
      })
    },
    async takeRMB () {
      const cardInfo = JSON.parse(sessionStorage.getItem('cardInfo'))
      const cardId = cardInfo.cardId
      const url = '/card/user-takermb?cardid=' + cardId + '&takeAmount=' + this.takeAmount + '&atmId=' + this.$store.state.atmId
      request.post(url).then(async res => {
        if (res.code === '1') {
          // 出错了 转到桌面,退卡
          this.messageContent = '机器故障请重试或者更换机器,即将退卡...'
          this.messageDialog = true
          setTimeout(() => {
            this.messageDialog = false
            this.navigateToDeckTop()
          }, 5000)
          this.takeAmount = ''
        } else if (res.code === '2') {
          // 银行卡余量不足
          this.messageContent = '银行卡余额不足,请减少金额'
          this.messageDialog = true
          setTimeout(() => {
            this.messageDialog = false
          }, 5000)
          this.takeAmount = ''
        } else if (res.code === '3') {
          this.messageContent = '本机器机箱余额不足,请减少金额或者更换机器'
          this.messageDialog = true
          setTimeout(() => {
            this.messageDialog = false
          }, 5000)
          this.takeAmount = ''
        } else {
          await this.beginCountMoney()
          setTimeout(() => {
            this.messageDialog = true
            this.messageContent = '钱款准备完毕,请把钱币收好,以免丢失'
          }, 1000)
          setTimeout(() => {
            this.messageDialog = false
            this.navigateToUserTakeSuccess(this.takeAmount, res.data.recordId)
          }, 5000)
        }
      }).catch(error => {
        this.isShow = false
        this.showErrorMessage('网络错误\n请稍后重试或者更换机器' + error.message)
      })
    },
    isEmptyOROver () {
      if (this.takeAmount === '0' && this.takeAmount === '') {
        // 是0或者空
        return 1
      } else if (Number(this.takeAmount) % 100 !== 0) {
        // 不是100 的整数
        return 2
      } else if (Number(this.takeAmount) > 1000) {
        return 3
      } else {
        return 4
      }
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
    },
    showErrorMessage (msg) {
      this.messageContent = msg
      this.messageDialog = true
      setTimeout(() => {
        this.messageDialog = false
      }, 3000)
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
    justify-content: left;
    align-items: center; /* 垂直居中 */
}

.input-name1 {
    /* label： */
    position: absolute;
    width: 600px;
    left: 440px;
    top: 180px;
    bottom: 801px;
    color: rgb(8, 20, 21);
    font-family:Cambria, Cochin, Georgia, Times, 'Times New Roman', serif;
    font-size: 45px;
    font-weight: 400;
    line-height: 86px;
    letter-spacing: 0px;
    text-align: left;
    text-shadow: 2px 2px 4px rgba(0, 49, 182, 0.5);
}
.input-text1 {
    /* 密码框 */
    position: absolute;
    width: 465px;
    height: 60px;
    left: 450px;
    top: 300px;
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
