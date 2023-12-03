
<template>
    <div class="container">
      <div class="desktopBack">
      <atmheader></atmheader>
        <div>
          <el-button class="butt" style="top: 250px;" @click="navigateToCheckBalance">
            <label class="fontStyle"><i class="el-icon-back"></i>查看余额</label>
          </el-button>
          <el-button class="butt" style="top: 450px; margin-left: 0px" @click="navigateToAddRMB">
            <label class="fontStyle"><i class="el-icon-back"></i>放钞</label>
          </el-button>
          <el-button class="butt" style="top: 650px; margin-left: 0px" @click="navigateToTakeRMB">
            <label class="fontStyle"><i class="el-icon-back"></i>取钞</label>
          </el-button>
          <el-button class="butt" style="top: 850px; margin-left: 0px" @click="navigateToAddPaper">
            <label class="fontStyle"><i class="el-icon-back"></i>放打印纸</label>
          </el-button>
          <el-button class="butt" style="top: 850px; margin-left:1130px;" @click="navigateToDeskTop">
            <label class="fontStyle">回到桌面<i class="el-icon-right"></i></label>
          </el-button>
        </div>
        <div class="someMessage">
          <div>
            <label style="color: rgba(237, 250, 1, 0.858);font-family: 等线;font-size: 28px;font-weight: 300;line-height: 86px;letter-spacing: 0px;text-align: left;">亲爱的管理员:</label>
           <label style="color: rgba(250, 196, 1, 0.858);font-family: 等线;font-size: 22px;font-weight: 300;line-height: 86px;letter-spacing: 0px;text-align: left;">欢迎您！请您选择即将进行的操作！</label><br>
          </div>
          <label style="color: rgba(237, 250, 1, 0.999);width:90%;display:block;font-family: 等线;font-size: 23px;font-weight:50;text-align: left;">以确保ATM机的顺利运行和用户的满意体验,请您注意以下几点:<br>1.定期检查和维护ATM机。请将ATM机视为自己的孩子,保持它的良好状态,定期清洁和维护,以确保它在用户面前展现最佳状态。

            <br>2.密码和安全性。请牢记您的管理员密码,并确保它足够强大,不易被猜测。只有您知晓密码信息,并确保将其保密。同时,确保ATM机周围环境的安全,让用户感到安心。

            <br>3.更新软件和安全补丁:ATM机的软件和安全补丁需要定期更新。

            <br>4.提供用户支持和故障排除：作为ATM机管理员，您是用户的重要支持者。请耐心回答用户的问题，解决故障，并确保他们得到及时的帮助和支持。您的关心和努力将为用户带来无比的便利和信任。

            <br>5.定期报告和监控ATM机：定期生成报告，监控ATM机的使用情况和性能。这样可以及时发现任何异常和问题，并采取适当的措施。您的用心和细致，将为用户提供一个安全、可靠的ATM机环境。

            <br>6.持续培训和更新知识：随着科技的不断进步，保持学习和更新知识是不可或缺的。通过不断的培训和学习，您将更好地掌握最新的安全标准和最佳实践，为用户提供更优质的服务。
          </label>
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
    navigateToDeskTop () {
      // 执行页面跳转逻辑
      this.$router.push('/')
    },
    navigateToCheckBalance () {
      this.$router.push('/admincheckbalance')
    },
    navigateToAddRMB () {
      this.$router.push('/adminaddrmb')
    },
    navigateToTakeRMB () {
      this.$router.push('/admintakermb')
    },
    navigateToAddPaper () {
      this.$router.push('/adminaddpaper')
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

.someMessage {
  position: absolute;
  width: 800px;
  height: 550px;
  left: 500px;
  top: 250px;
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
