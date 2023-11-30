<template>
    <div class="container">
      <div class="desktopBack">
      <atmheader></atmheader>
        <div>
          <el-button class="butt" style="top: 850px;" @click="navigateToDeskTop">
            <label class="fontStyle"><i class="el-icon-back"></i>退卡</label>
          </el-button>
          <el-button class="butt" style="top: 850px; margin-left:1130px;" @click="navigateToOperation">
            <label class="fontStyle">返回上级<i class="el-icon-right"></i></label>
          </el-button>
        </div>
      </div>
      <div class="balanceBox">
        <label class="balaFontStyle" v-show="isShow">您的余额共计:<span class="special"> {{ this.balance }} </span>元</label><br>
        <label class="balaFontStyle" v-show="notShow">机器出现故障或者网络不稳定,请稍后再试</label>
      </div>
    </div>
</template>
<script>
import request from '@/utils/request'
import atmheader from '../components/atmHeader.vue'

export default {
  components: {
    atmheader
  },
  data () {
    return {
      messageDialog: false,
      balance: 0,
      isShow: true,
      notShow: false
    }
  },
  created () {
    this.checkBalance()
  },
  methods: {
    navigateToDeskTop () {
      // 执行页面跳转逻辑
      // 执行退卡动画
      this.$router.push('/')
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
          this.isShow = true
          this.notShow = false
          this.balance = res.data.cardBalance.toFixed(2)
        } else {
          this.isShow = false
          this.notShow = true
          setTimeout(() => {
            this.navigateToOperation()
          }, 3000)
        }
      })
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
    justify-content: center; /* 水平居中 */
    align-items: center; /* 垂直居中 */
}

.passBack {
    /* 输入密码盘背景 */
    position: absolute;
    width: 628px;
    height: 780px;
    left: 759px;
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

.input-name {
    /* 请输入员工号： */
    position: absolute;
    width: 486px;
    height: 87px;
    left: 159px;
    right: 849px;
    top: 300px;
    bottom: 801px;
    color: rgb(72, 161, 87);
    font-family:Cambria, Cochin, Georgia, Times, 'Times New Roman', serif;
    font-size: 72px;
    font-weight: 400;
    line-height: 86px;
    letter-spacing: 0px;
    text-align: left;
    text-shadow: 2px 2px 4px rgba(212, 52, 116, 0.5);
}
.input-text {
    /* 密码框 */
    position: absolute;
    width: 465px;
    height: 97px;
    left: 150px;
    right: 821px;
    top: 450px;
    bottom: 487px;
    background: rgb(195, 170, 170);
    border-radius: 20px;
    text-align: center;
    font-size: 50px;
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
  margin-left: 100px;
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
