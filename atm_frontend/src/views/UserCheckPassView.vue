
<template>
    <div class="container">
      <div class="desktopBack">
      <atmheader></atmheader>
      <div v-show ="isShow" style="position: relative; left:0px; top:300px; z-index: 9999;">
        <i class="el-icon-loading" style="font-size: 100px;"></i>
      </div>
        <label class="input-name">请输入密码</label>
        <input class="input-text" v-model="cardPass" type="password" readonly>
        <div>
          <el-button class="butt" style="top: 850px" @click="navigateToAdminOperation">
            <label class="fontStyle"><i class="el-icon-back"></i>退出</label>
          </el-button>
        </div>
        <div class="passBack">
          <Keypad @key-click="handleKeyClick"></Keypad>
        </div>
      </div>
      <div>
        <el-dialog :visible="messageDialog" title="重要提示" :append-to-body="true" class="custom-dialog">
        <!-- 对话框内容 -->
        <span class="dialog-content">{{ this.messageContent }}<br>
          <span class="dialog-content" v-if = "oneORtwo">超过3次输入错误密码本账户将锁定！</span>
        </span><br>
        <!-- 机会提示区域 -->
        <div v-if="oneORtwo" style="font-size: 30px;text-align: center;width:100%;box-sizing: border-box;">你还有{{ this.num }}次机会</div>
        <!-- 对话框底部按钮 -->
        <span slot="footer" class="dialog-footer">
          <el-button :disabled = "isDisButt" @click="closeDialog" style="width: 20%;height: 60px;font-size: 40px;font-family: 楷体;">确 认</el-button>
        </span>
      </el-dialog>
      </div>
    </div>
</template>
<script>
import request from '@/utils/request'
import Keypad from '../components/KeyPad.vue'
import atmheader from '../components/atmHeader.vue'

export default {
  components: {
    Keypad,
    atmheader
  },
  data () {
    return {
      cardPass: '',
      messageDialog: false,
      messageContent: '',
      num: 0,
      isDisButt: false,
      oneORtwo: false,
      isShow: false
    }
  },
  mounted () {
    this.whenRefresh()
  },
  methods: {
    whenRefresh () {
      this.num = 0
      this.messageDialog = false
      this.messageContent = '' // 提示内容
      this.oneORtwo = true
      this.isShow = false
      this.isDisButt = false
      this.cardPass = ''
    },
    navigateToUserOperation () {
      // 执行页面跳转逻辑
      this.$router.push('/useroperation')
    },
    navigateToDeskTop () {
      // 执行页面跳转逻辑
      this.$router.push('/')
    },
    handleKeyClick (key) {
      if (key === '退格') {
        this.cardPass = this.cardPass.slice(0, -1)
      } else if (key === '确认') {
        this.isShow = true
        setTimeout(() => {
          this.checkCardPass(this.cardPass)
        }, 2000)
        // 执行确认操作
      } else {
        this.cardPass += key
      }
    },
    closeDialog () {
      this.messageDialog = false
      this.cardPass = ''
    },
    checkCardPass (cardPass) {
      const cardId = this.$route.params.cardId
      const url = '/card/check-pass?cardid=' + cardId + '&cardpassword=' + cardPass
      request.get(url).then(res => {
        this.isShow = false
        if (res.code === '0') {
          sessionStorage.setItem('cardInfo', JSON.stringify(res.data))
          this.navigateToUserOperation()
        } else if (res.code === '1') {
          this.oneORtwo = false
          this.messageContent = res.msg
          this.messageDialog = true
          setTimeout(() => {
            this.navigateToDeskTop()
            // （如果系统错误）加退卡操作
          }, 2000)
        } else {
          this.num = res.data.num
          if (this.num <= 0) {
            this.messageContent = '账号已经锁定,请到柜台解锁后重试！'
            this.messageDialog = true
            this.isDisButt = true
            this.oneORtwo = false
            setTimeout(() => {
              this.navigateToDeskTop()
            }, 3000)
          } else {
            this.isDisButt = false
            this.oneORtwo = true
            this.messageContent = '银行卡对应密码错误，请重试！'
            this.messageDialog = true
          }
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
</style>
