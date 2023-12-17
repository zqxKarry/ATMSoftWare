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
        <label class="input-name1" style="left:600px;top:300px">{{ this.inputName }}</label>
        <input class="input-text1" style="left:500px;top:400px" v-model="cardPass" type="password" readonly>
        <div>
          <el-button class="butt" style="top: 850px" @click="navigateToUserOperation">
            <label class="fontStyle"><i class="el-icon-back"></i>取消修改</label>
          </el-button>
        </div>
      </div>
      <div v-if="messageDialog" class = "dialog-overlay">
        <div class="custom-dialog" :class="{'dialog-left': dialogLeft}" style="height: 400px;">
          <!-- 对话框内容 -->
          <span class="dialog-title">重要提示</span>
          <div class="dialog-content">{{ this.messageContent }}<br>
            <span v-if="oneORtwo" class="dialog-hint">超过3次输入错误密码本账户将锁定！</span>
          </div>
          <!-- 机会提示区域 -->
          <div v-if="oneORtwo" class="dialog-chance">
              你还有{{ this.num }}次机会
          </div>
        </div>
      </div>
    </div>
</template>
<script>
import request from '@/utils/request'
import Keypad from '../components/KeyPad.vue'
import atmheader from '../components/atmHeader.vue'
import '@/assets/CSS/messageDialog.css'
import '@/assets/CSS/timeCounter.css'

export default {
  components: {
    Keypad,
    atmheader
  },
  data () {
    return {
      countdownTime: 60,
      timer: null,
      inputName: '请输入原始密码',
      counter: 0, // 计数器，实现值的转换传递，比如0表示输入的是初始密码，1表示输入第一次新密码，2表示输入第二次新密码
      cardPass: '',
      oldPass: '',
      newPass1: '',
      newPass2: '',
      messageDialog: false,
      messageContent: '',
      num: 0,
      isDisButt: false,
      oneORtwo: false,
      isShow: false
    }
  },
  mounted () {
    // ===========事件监听=========//
    this.startTimer()
    window.addEventListener('click', this.resetTimer)
    window.addEventListener('keydown', this.resetTimer)
    // ============================//
    this.whenRefresh()
  },
  methods: {
    whenRefresh () {
      this.inputName = '请输入原密码'
      this.counter = 0
      this.num = 0
      this.messageDialog = false
      this.messageContent = '' // 提示内容
      this.oneORtwo = true
      this.isShow = false
      this.cardPass = ''
    },
    navigateToUserOperation () {
      // 执行页面跳转逻辑
      this.$router.push('/useroperation')
    },
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
    navigateToCheckPass (cardId) {
      // 执行页面跳转逻辑
      this.$router.push({
        name: 'usercheckpass',
        params: {
          cardId: cardId
        }
      })
    },
    handleKeyClick (key) {
      if (key === '退格') {
        this.cardPass = this.cardPass.slice(0, -1)
      } else if (key === '确认') {
        if (this.cardPass === '' || this.cardPass.length !== 6) {
          this.oneORtwo = false
          this.messageContent = '请输入六位数字密码'
          this.messageDialog = true
          setTimeout(() => {
            this.messageDialog = false
          }, 3000)
        } else {
          this.isShow = true
          setTimeout(() => {
            if (this.counter % 3 === 0) {
              this.oldPass = this.cardPass
              this.checkCardPass(this.oldPass)
            } else if (this.counter % 3 === 1) {
              this.newPass1 = this.cardPass
              this.counter = (this.counter + 1) % 3
              this.inputName = '请确认新密码'
              this.cardPass = ''
              this.isShow = false
            } else {
              this.newPass2 = this.cardPass
              if (this.comparePass()) {
                setTimeout(() => {
                  this.changeCardPass(this.newPass1)
                }, 2000)
              } else {
                this.isShow = false
                this.messageContent = '两次密码不一致,请重新输入'
                this.oneORtwo = false
                this.messageDialog = true
                setTimeout(() => {
                  this.messageDialog = false
                  this.cardPass = ''
                }, 3000)
              }
            }
          }, 2000)
          // 执行确认操作
        }
      } else {
        this.cardPass += key
      }
    },
    closeDialog () {
      this.messageDialog = false
      this.cardPass = ''
    },
    checkCardPass (cardPass) {
      const cardInfo = JSON.parse(sessionStorage.getItem('cardInfo'))
      const cardId = cardInfo.cardId
      const url = '/card/check-pass?cardid=' + cardId + '&cardpassword=' + cardPass
      request.get(url).then(res => {
        this.isShow = false
        if (res.code === '0') {
          this.inputName = '请输入新密码'
          this.cardPass = ''
          this.counter = (this.counter + 1) % 3
        } else if (res.code === '1') {
          this.oneORtwo = false
          this.messageContent = res.msg
          this.messageDialog = true
          setTimeout(() => {
            this.messageDialog = false
            this.cardPass = ''
            // （如果系统错误）退到操作页面
            this.navigateToUserOperation()
          }, 2000)
        } else {
          this.num = res.data.num
          if (this.num <= 0) {
            this.messageContent = '账号已经锁定,请到柜台解锁后重试！'
            this.messageDialog = true
            this.oneORtwo = false
            setTimeout(() => {
              this.messageDialog = false
              this.cardPass = ''
              this.navigateToDeskTopAndReturnCard()
            }, 3000)
          } else {
            this.oneORtwo = true
            this.messageContent = '验证密码错误，请重试！'
            this.messageDialog = true
            setTimeout(() => {
              this.messageDialog = false
              this.cardPass = ''
            }, 3000)
          }
        }
      }).catch(error => {
        this.isShow = false
        this.cardPass = ''
        this.showErrorMessage('网络错误\n请稍后重试或者更换机器' + error.message)
      })
    },
    comparePass () {
      // 比较两个密码是否相同
      if (this.newPass1 === this.newPass2) {
        return true
      } else {
        return false
      }
    },
    changeCardPass (newPass) {
      const cardInfo = JSON.parse(sessionStorage.getItem('cardInfo'))
      const cardId = cardInfo.cardId
      const url = '/card/change-password?cardid=' + cardId + '&' + 'newpassword=' + newPass
      request.post(url).then(res => {
        this.isShow = false
        if (res.code === '0') {
          this.oneORtwo = false
          this.messageContent = '修改成功,请重新登录！'
          this.messageDialog = true
          setTimeout(() => {
            this.messageDialog = false
            this.cardPass = ''
            this.navigateToCheckPass(cardId)
          }, 3000)
        } else {
          // 修改失败,系统错误
          this.oneORtwo = false
          this.message = res.msg
          this.messageDialog = true
          setTimeout(() => {
            this.navigateToDeskTopAndReturnCard()
          }, 3000)
        }
      }).catch(error => {
        this.isShow = false
        this.cardPass = ''
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

.numberboard {
    /* 输入盘背景 */
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
.dialog-left {
  left: 20px; /* 设置对话框的左侧位置 */
}
</style>
