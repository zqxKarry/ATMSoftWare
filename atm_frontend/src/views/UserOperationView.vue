
<template>
    <div class="container">
      <div class="desktopBack">
      <atmheader></atmheader>
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
          <el-button class="butt" style="top: 850px; margin-left:1130px;" @click="navigateToDeskTop">
            <label class="fontStyle">退卡<i class="el-icon-right"></i></label>
          </el-button>
          <el-button class="butt" style="top: 650px; margin-left:1130px;" @click="navigateToChangePassword">
            <label class="fontStyle">修改密码<i class="el-icon-right"></i></label>
          </el-button>
          <el-button class="butt" style="top: 450px; margin-left:1130px;" @click="navigateToGetAllRecord">
            <label class="fontStyle">查看流水<i class="el-icon-right"></i></label>
          </el-button>
        </div>
        <div class="someMessage1">
          <div>
            <label style="color: rgba(237, 250, 1, 0.858);font-family: 等线;font-size: 32px;font-weight: 300;line-height: 86px;letter-spacing: 0px;text-align: left;">亲爱的用户:</label>
           <label style="color: rgba(250, 196, 1, 0.858);font-family: 等线;font-size: 28px;font-weight: 300;line-height: 86px;letter-spacing: 0px;text-align: left;">欢迎您！请您选择即将进行的操作！</label><br>
          </div>
        </div>
      </div>
    </div>
</template>
<script>
import atmheader from '../components/atmHeader.vue'
export default {
  components: {
    atmheader
  },
  data () {
    return {
      // messageDialog: false
    }
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
    navigateToGetAllRecord () {
      this.$router.push('/usergetallrecord')
    },
    navigateToChangePassword () {
      this.$router.push('/userchangepassword')
    },
    navigateToDeskTop () {
      // 执行页面跳转逻辑
      // 需要调用退卡动画
      this.$router.push('/')
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
  width: 800px;
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
</style>
