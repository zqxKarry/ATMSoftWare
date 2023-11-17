<template>
    <div class="container">
      <div class="desktopBack">
      <atmheader></atmheader>
      <div v-show ="isShow" style="position: relative; left:0px; top:300px; z-index: 9999;">
        <i class="el-icon-loading" style="font-size: 100px;"></i>
      </div>
        <label class="input-name" style="font-size: 55px;">请输入添加的数量</label>
        <input class="input-text" v-model="addCount" type="number" readonly>
        <div class="passBack">
          <Keypad @key-click="handleKeyClick"></Keypad>
        </div>
        <div>
          <el-button class="butt" style="top: 850px;" @click="navigateToDeskTop">
            <label class="fontStyle"><i class="el-icon-back"></i>回到桌面</label>
          </el-button>
          <el-button class="butt" style="top: 650px; margin-left:0px;" @click="navigateToOperation">
            <label class="fontStyle"><i class="el-icon-back"></i>返回上级</label>
          </el-button>
        </div>
      </div>
      <div>
      <el-dialog :visible="messageDialog" title="重要提示" :append-to-body="true" class="custom-dialog">
        <!-- 对话框内容 -->
        <span class="dialog-content"> {{ this.message }}</span><br>
        <!-- 对话框底部按钮 -->
        <span slot="footer" class="dialog-footer">
          <el-button :disabled = "isDisButt" @click="messageDialog = false" style="width: 20%;height: 60px;font-size: 40px;font-family: 楷体;">确 认</el-button>
        </span>
      </el-dialog>
      </div>
    </div>
</template>
<script>
import request from '@/utils/request'
import atmheader from '../components/atmHeader.vue'
import Keypad from '../components/KeyPad.vue'
export default {
  components: {
    atmheader,
    Keypad
  },
  data () {
    return {
      messageDialog: false,
      addCount: '',
      message: '',
      isDisButt: false,
      isShow: false
    }
  },
  methods: {
    navigateToDeskTop () {
      // 执行页面跳转逻辑
      this.$router.push('/')
    },
    navigateToOperation () {
      this.$router.push('/adminoperation')
    },
    handleKeyClick (key) {
      if (key === '退格') {
        this.addCount = this.addCount.slice(0, -1)
      } else if (key === '确认') {
        this.isShow = true
        setTimeout(() => {
          this.addRMBCount(this.addCount, this.$store.state.atmId)
        }, 2000)
        // 执行确认操作
      } else {
        this.addCount += key
      }
    },
    addRMBCount (addCount, atmId) {
      const url = '/atm/admin-addrmb?addCount=' + addCount + '&' + 'atmId=' + atmId
      request.post(url).then(res => {
        this.isShow = false
        if (res.code === '0') {
          this.message = '添加成功，您辛苦了！'
          this.messageDialog = true
          this.isDisButt = false
          this.addCount = ''
        } else if (res.code === '1') {
          this.message = res.msg
          this.messageDialog = true
          this.isDisButt = true
          setTimeout(() => {
            this.navigateToDeskTop()
          }, 3000)
        } else {
          this.message = res.msg
          this.messageDialog = true
          this.isDisButt = false
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
