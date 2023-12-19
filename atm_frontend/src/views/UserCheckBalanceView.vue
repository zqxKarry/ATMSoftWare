<template>
    <div class="container">
    <div class="numberboard">
       <KeyPad></KeyPad>
    </div>
      <div class="desktopBack">
      <atmheader></atmheader>
      <div class="countdown-container">
        <div class="countdown">
         <div class="countdown-timer">倒计时:{{ this.countdownTime }} s</div>
        </div>
      </div>
        <div>
          <el-button class="butt" style="top: 850px;" @click="navigateToDeskTopAndReturnCard">
            <label class="fontStyle"><i class="el-icon-back"></i>退卡</label>
          </el-button>
          <el-button class="butt" style="top: 850px; margin-left:1130px;" @click="navigateToOperation">
            <label class="fontStyle">返回<i class="el-icon-right"></i></label>
          </el-button>
        </div>
      </div>
      <div class="balanceBox">
        <label class="balaFontStyle" v-show="isShow">您的余额共计: <span class="special"> {{ this.balance }} </span>元</label><br>
        <label class="balaFontStyle" v-show="isShow">可取余额共计: <span class="special"> {{ this.takeBalance }} </span>元</label><br>
        <label class="balaFontStyle" v-show="isShow">可转账余额共计:<span class="special"> {{ this.balance }} </span>元</label><br>
        <label class="balaFontStyle" v-show="notShow">机器出现故障或者网络不稳定,请稍后再试</label>
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
import request from '@/utils/request'
import KeyPad from '@/components/KeyPad.vue'
import atmheader from '../components/atmHeader.vue'
import '@/assets/CSS/messageDialog.css'
import '@/assets/CSS/timeCounter.css'

export default {
  components: {
    atmheader,
    KeyPad
  },
  data () {
    return {
      countdownTime: 60,
      timer: null,
      messageDialog: false,
      balance: 0,
      takeBalance: 0,
      isShow: true,
      notShow: false
    }
  },
  created () {
    // ===========事件监听=========//
    this.startTimer()
    window.addEventListener('click', this.resetTimer)
    window.addEventListener('keydown', this.resetTimer)
    // ============================//
    this.checkBalance()
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
    navigateToOperation () {
      this.$router.push('/useroperation')
    },
    checkBalance () {
      const cardInfo = JSON.parse(sessionStorage.getItem('cardInfo'))
      // console.log(cardInfo)
      const cardId = cardInfo.cardId
      const url = '/card/check-bala?cardid=' + cardId
      request.get(url).then(res => {
        if (res.code === '0') {
          this.notShow = false
          this.balance = res.data.cardBalance.toFixed(2)
          this.takeBalance = (this.balance - this.balance % 100).toFixed(2)
        } else {
          this.notShow = true
          setTimeout(() => {
            this.navigateToOperation()
          }, 3000)
        }
      }).catch(error => {
        this.showErrorMessage('网络错误\n请稍后重试或者更换机器' + error.message)
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

/* 两个按钮 */
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
.balanceBox {
  position: absolute;
  width: 937px;
  height: 455px;
  margin-left: 250px;
  /* margin-right: 138px; */
  margin-top: 50px;
  background: rgb(172, 140, 140);
  border-radius: 20px;
}
.balaFontStyle {
  color: rgb(99, 11, 11);
  font-family: 黑体;
  font-size: 60px;
  font-weight: 400;
  line-height: 75px;
  letter-spacing: 0px;
  text-align: left;
}

.special {
  color: rgb(234, 19, 19);
  font-family: 黑体;
  font-size: 65px;
  font-weight: 400;
  line-height: 70px;
  letter-spacing: 0px;
  text-align: left;
}
</style>
