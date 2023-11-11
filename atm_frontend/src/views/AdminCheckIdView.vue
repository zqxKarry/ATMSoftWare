
<template>
    <div class="container">
      <div class="desktopBack">
      <atmheader></atmheader>
      <div v-show ="isShow" style="position: relative; left:0px; top:300px; z-index: 9999;">
        <i class="el-icon-loading" style="font-size: 100px;"></i>
      </div>
        <label class="input-name">请输入员工号</label>
        <input class="input-text" v-model="adminId" type="text" readonly>
        <div>
          <el-button class="butt" style="top: 850px" @click="navigateToDeskTop">
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
          <span class="dialog-content" v-if="oneORtwo">超过3次输入错误密码将自动报警</span>
          <span class="dialog-content" v-else>请重新尝试！如不行请联系检修员</span>
        </span><br>
        <div v-if="oneORtwo" style="font-size: 30px;text-align: center;width:100%;box-sizing: border-box;">你还有{{ this.num }}次机会</div>
        <!-- 对话框底部按钮 -->
        <span slot="footer" class="dialog-footer">
          <el-button @click="closeDialog" style="width: 20%;height: 60px;font-size: 40px;font-family: 楷体;">确 认</el-button>
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
      adminId: '',
      messageDialog: false,
      num: 3,
      messageContent: '', // 提示内容
      oneORtwo: true,
      isShow: false
    }
  },
  mounted () {
    this.whenRefresh()
  },
  methods: {
    whenRefresh () {
      this.num = 3
      this.messageDialog = false
      this.messageContent = '' // 提示内容
      this.oneORtwo = true
      this.isShow = false
      this.adminId = ''
    },
    navigateToDeskTop () {
      // 执行页面跳转逻辑
      this.$router.push('/')
    },
    navigateToAdminCheckPass (adminId) {
      // 执行页面跳转逻辑
      this.$router.push({
        name: 'admincheckpass',
        params: {
          adminId: adminId
        }
      })
    },
    handleKeyClick (key) {
      if (key === '退格') {
        this.adminId = this.adminId.slice(0, -1)
      } else if (key === '确认') {
        this.isShow = true
        setTimeout(() => {
          this.checkAdminId(this.adminId, this.$store.state.atmId)
        }, 2000)
      } else {
        this.adminId += key
      }
    },
    closeDialog () {
      this.messageDialog = false
      this.oneORtwo = false
      this.adminId = ''
    },
    checkAdminId (adminId, atmId) {
      if (this.num > 1) {
        const url = '/admin/check-id?adminId=' + adminId + '&' + 'atmId=' + atmId
        request.get(url).then(res => {
          /* 对结果进行判断 */
          this.isShow = false
          console.log(res)
          if (res.code === '0') {
            this.navigateToAdminCheckPass(adminId)
          } else {
            if (res.code === '1') {
              this.oneORtwo = true
              this.messageContent = '员工号错误！请重新输入!\n'
              this.num--
              this.messageDialog = true
            } else if (res.code === '2') {
              this.oneORtwo = false
              this.messageContent = '请求超时，未连接到服务器！\n'
              this.messageDialog = true
            } else {
              this.oneORtwo = false
              this.messageContent = '密码错误3次,已锁定，请前往柜台解锁！\n'
              this.messageDialog = true
            }
          }
        })
      } else {
        this.isShow = false
        this.messageContent = '警报已拉响！！请返回再尝试！\n'
        this.messageDialog = true
        setTimeout(() => {
          this.navigateToDeskTop()
        }, 2000)
      }
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
