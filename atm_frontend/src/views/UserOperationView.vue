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
          <el-button class="butt" style="top: 250px;" @click="navigateToCheckBalance">
            <label class="fontStyle"><i class="el-icon-back"></i>查询余额</label>
          </el-button>
          <el-button class="butt" style="top: 450px; margin-left: 0px" @click="navigateToStoreRMB">
            <label class="fontStyle"><i class="el-icon-back"></i>存款</label>
          </el-button>
          <el-button class="butt" style="top: 650px; margin-left: 0px" @click="navigateToTakeRMB">
            <label class="fontStyle"><i class="el-icon-back"></i>取款</label>
          </el-button>
          <el-button class="butt" style="top: 850px; margin-left: 0px" @click="navigateToTransfer">
            <label class="fontStyle"><i class="el-icon-back"></i>转账业务</label>
          </el-button>
          <el-button class="butt" style="top: 850px; margin-left:1130px;" @click="navigateToDeskTopAndReturnCard">
            <label class="fontStyle">退卡<i class="el-icon-right"></i></label>
          </el-button>
          <el-button class="butt" style="top: 650px; margin-left:1130px;" @click="navigateToChangePassword">
            <label class="fontStyle">修改密码<i class="el-icon-right"></i></label>
          </el-button>
          <el-button class="butt" style="top: 450px; margin-left:1130px;" @click="navigateToGetRecentRecord">
            <label class="fontStyle">查看流水<i class="el-icon-right"></i></label>
          </el-button>
        </div>
        <div class="someMessage1">
          <div>
            <label style="color: rgba(237, 250, 1, 0.858);font-family: 等线;font-size: 32px;font-weight: 300;line-height: 86px;letter-spacing: 0px;text-align: left;">亲爱的用户:</label>
           <label style="color: rgba(250, 196, 1, 0.858);font-family: 等线;font-size: 28px;font-weight: 300;line-height: 86px;letter-spacing: 0px;text-align: left;">欢迎您！请您选择即将进行的操作！</label><br>
          </div>
        </div>
        <div v-if="messageDialog" class = "dialog-overlay">
        <div class="custom-dialog" :class="{'dialog-left': dialogLeft}" style="height: 400px;">
          <!-- 对话框内容 -->
          <span class="dialog-title">重要提示</span>
          <div class="dialog-content">{{ this.messageContent }}
          </div>
        </div>
      </div>
      </div>
    </div>
</template>
<script>
import atmheader from '../components/atmHeader.vue'
import KeyPad from '@/components/KeyPad.vue'
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
      messageContent: ''
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
    navigateToCheckBalance () {
      this.$router.push('/usercheckbalance')
    },
    navigateToStoreRMB () {
      this.$router.push('/userstorermb')
    },
    navigateToTakeRMB () {
      this.$router.push('/usertakermb')
    },
    navigateToTransfer () {
      this.$router.push('/usertransfer')
    },
    navigateToGetRecentRecord () {
      this.$router.push('/usergetrecentrecord')
    },
    navigateToChangePassword () {
      this.$router.push('/userchangepassword')
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
    justify-content: left; /* 水平居左 */
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

.someMessage1 {
  position: absolute;
  width: 700px;
  height: 180px;
  left: 450px;
  top: 220px;
  background: linear-gradient(0.57deg, rgb(195, 170, 170) 1.141%,rgba(255, 255, 255, 0) 98.349%);
  border-radius: 20px;
  border: dotted 5px #19c5cb;
  text-align: left;
  display: flex;
  flex-direction: column;
  justify-content: center; /* 水平居中对齐 */
  align-items: center; /* 垂直居中对齐 */
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
</style>
