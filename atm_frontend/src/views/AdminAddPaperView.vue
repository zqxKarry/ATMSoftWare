<template>
    <div class="container">
      <div class="numberboard">
       <KeyPad></KeyPad>
      </div>
      <div class="desktopBack">
      <atmheader></atmheader>
      <div  style="width: 486px;height:200px;margin-left: 159px;margin-top: 80px;">
        <label class="input-title" style="font-size: 40px;" v-show="isShow">机箱内剩余 <span class = "special0"> {{ this.paperCount }}</span> 张打印纸</label><br>
        <label class="input-title" style="font-size: 40px;" v-show="isShow">还可添加 <span class ="special0"> {{ this.restCount }} </span>张打印纸</label><br>
        <label class="input-title" style="font-size: 40px;" v-show="notShow">机器出现故障或者网络不稳定,请稍后再试</label><br>
      </div>
      <div v-show ="isShowLoading" style="position: relative; left:0px; top:100px; z-index: 9999;">
        <i class="el-icon-loading" style="font-size: 100px;"></i>
      </div>
        <input class="input-text" v-model="addCount" placeholder="输入要加的数量" type="number" readonly>
        <div class="passBack">
          <AdminKeyPad @key-click="handleKeyClick"></AdminKeyPad>
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
import atmheader from '../components/atmHeader.vue'
import KeyPad from '../components/KeyPad.vue'
import AdminKeyPad from '@/components/AdminKeyPad.vue'
import '@/assets/CSS/messageDialog.css'

export default {
  components: {
    atmheader,
    KeyPad,
    AdminKeyPad
  },
  data () {
    return {
      messageDialog: false,
      isShowLoading: false,
      addCount: '',
      message: '',
      isShow: true,
      notShow: false,
      paperCount: 0,
      restCount: 0
    }
  },
  created () {
    this.checkPaperCount()
  },
  methods: {
    navigateToDeskTop () {
      // 执行页面跳转逻辑
      this.$router.push('/')
    },
    navigateToOperation () {
      this.$router.push('/adminoperation')
    },
    checkPaperCount () {
      const url = '/atm/check-pcount?atmId=' + this.$store.state.atmId
      request.get(url).then(res => {
        if (res.code === '0') {
          this.isShow = true
          this.notShow = false
          this.paperCount = res.data.atmPaperCount
          this.restCount = 1000 - this.paperCount
        } else {
          this.isShow = false
          this.notShow = true
          setTimeout(() => {
            this.navigateToOperation()
          }, 3000)
        }
      }).catch(error => {
        this.isShow = false
        // this.addCount = ''
        this.showErrorMessage('网络错误\n请稍后重试或者更换机器' + error.message)
      })
    },
    handleKeyClick (key) {
      if (key === '退格') {
        this.addCount = this.addCount.slice(0, -1)
      } else if (key === '确认') {
        if (this.addCount === '' || Number(this.addCount) === 0) {
          this.message = '添加数量不能为0'
          this.messageDialog = true
          setTimeout(() => {
            this.messageDialog = false
          }, 3000)
        } else {
          this.isShowLoading = true
          setTimeout(() => {
            this.addPaperCount(this.addCount, this.$store.state.atmId)
          }, 2000)
        }
      } else if (key === '重置') {
        this.addCount = ''
      } else if (key === '#' || key === '%' || key === 'D') {
        this.addCount += ''
      } else {
        this.addCount += key
      }
    },
    addPaperCount (addCount, atmId) {
      const url = '/atm/admin-addpaper?addCount=' + addCount + '&' + 'atmId=' + atmId
      request.post(url).then(res => {
        this.isShowLoading = false
        if (res.code === '0') {
          this.message = '添加成功，您辛苦了！'
          this.messageDialog = true
          this.addCount = ''
          this.paperCount = res.data.atmPaperCount
          this.restCount = 1000 - this.paperCount
          setTimeout(() => {
            this.messageDialog = false
            this.navigateToDeskTop()
          }, 3000)
        } else if (res.code === '1') {
          this.message = res.msg
          this.messageDialog = true
          this.isDisButt = true
          setTimeout(() => {
            this.messageDialog = false
            this.navigateToDeskTop()
          }, 3000)
        } else {
          this.message = res.msg
          this.messageDialog = true
          setTimeout(() => {
            this.messageDialog = false
          }, 3000)
        }
      }).catch(error => {
        this.isShow = false
        this.addCount = ''
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

.input-title {
    position: relative;
    color: rgb(72, 161, 87);
    font-family:Cambria, Cochin, Georgia, Times, 'Times New Roman', serif;
    /* font-size: 72px; */
    font-weight: 200;
    line-height: 60px;
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

.special0 {
  color: rgb(234, 19, 19);
  font-family: 黑体;
  font-size: 40px;
  font-weight: 400;
  line-height: 70px;
  letter-spacing: 0px;
  text-align: left;
}
</style>
