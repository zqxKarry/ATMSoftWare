<template>
    <div class="container">
      <div class="numberboard">
       <KeyPad ></KeyPad>
      </div>
      <div class="desktopBack">
      <atmheader></atmheader>
        <div>
          <el-button class="butt" style="top: 850px;" @click="navigateToDeskTop">
            <label class="fontStyle"><i class="el-icon-back"></i>回到桌面</label>
          </el-button>
          <el-button class="butt" style="top: 850px; margin-left:1130px;" @click="navigateToOperation">
            <label class="fontStyle">返回上级<i class="el-icon-right"></i></label>
          </el-button>
        </div>
      </div>
      <div class="balanceBox">
        <label class="balaFontStyle" style="color: #000;font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;line-height: 100px;" v-show="isShow">机箱内详情</label><br>
        <label class="balaFontStyle" v-show="isShow">机箱内剩余 <span class="special"> {{ this.rmbCount }} </span>张百元钞票<br>共计<span class="special"> {{ this.balance }} </span>元</label><br>
        <label class="balaFontStyle" v-show="isShow">机箱最大容量<span class="special"> 1000 </span>张百元钞票<br>还可放入<span class="special"> {{ this.restCount }} </span>张</label>
        <label class="balaFontStyle" v-show="notShow">机器出现故障或者网络不稳定,请稍后再试</label>
      </div>
      <div v-if="messageDialog" class = "dialog-overlay">
        <div class="custom-dialog" :class="{'dialog-left': dialogLeft}">
          <!-- 对话框内容 -->
          <span class="dialog-title">重要提示</span>
          <div class="dialog-content">{{ this.message }}</div>
        </div>
      </div>
    </div>
</template>
<script>
import request from '@/utils/request'
import KeyPad from '@/components/KeyPad.vue'
import atmheader from '../components/atmHeader.vue'
import '@/assets/CSS/messageDialog.css'

export default {
  components: {
    atmheader,
    KeyPad
  },
  data () {
    return {
      messageDialog: false,
      messageContent: '',
      balance: 0,
      isShow: true,
      notShow: false,
      rmbCount: 0,
      restCount: 0
    }
  },
  created () {
    this.checkBalance()
  },
  methods: {
    navigateToDeskTop () {
      // 执行页面跳转逻辑
      this.$router.push('/')
    },
    navigateToOperation () {
      this.$router.push('/adminoperation')
    },
    checkBalance () {
      const url = '/atm/check-bala?atmId=' + this.$store.state.atmId
      request.get(url).then(res => {
        if (res.code === '0') {
          this.isShow = true
          this.notShow = false
          this.balance = res.data.atmBalance
          this.rmbCount = this.balance / 100
          this.restCount = 1000 - this.rmbCount
        } else {
          this.isShow = false
          this.notShow = true
          setTimeout(() => {
            this.navigateToOperation()
          }, 3000)
        }
      }).catch(error => {
        this.isShowLoading = false
        this.adminPass = ''
        this.oneORtwo = false
        this.showErrorMessage('网络错误\n请稍后重试或者更换机器' + error.message)
      })
    },
    showErrorMessage (msg) {
      this.messageContent = msg
      this.messageDialog = true
      setTimeout(() => {
        this.messageDialog = false
      }, 3000)
    }
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
  margin-right: 138px;
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
