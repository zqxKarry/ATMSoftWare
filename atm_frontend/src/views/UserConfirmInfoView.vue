
<template>
    <div class="container">
      <div class="desktopBack">
      <atmheader></atmheader>
      <div v-show ="isShow" style="position: relative; left:0px; top:300px; z-index: 9999;">
        <i class="el-icon-loading" style="font-size: 100px;"></i>
      </div>
        <div>
          <el-button v-show="isShowNotPrint" class="butt" style="top: 850px;margin-left: 0px;" @click="confirmTransfer">
            <label class="fontStyle"><i class="el-icon-back"></i>确认</label>
          </el-button>
          <el-button  v-show="isShowNotPrint" class="butt" style="top: 850px; margin-left: 1130px" @click="navigateToUserOperation">
            <label class="fontStyle">取消业务<i class="el-icon-right"></i></label>
          </el-button>
          <el-button v-show="isShowPrint" class="butt" style="top: 850px;margin-left: 0px;" @click="printVoucher">
            <label class="fontStyle"><i class="el-icon-back"></i>打印凭条</label>
          </el-button>
          <el-button  v-show="isShowPrint" class="butt" style="top: 850px; margin-left: 1130px" @click="navigateToUserOperation">
            <label class="fontStyle">返回<i class="el-icon-right"></i></label>
          </el-button>
        </div>
        <div v-show="isShowNotPrint" class="transfer-info">
          <h2 style="background-color: blueviolet;font-size: 40px;font-weight: 10;">转账信息</h2>
            <p style="font-size: 30px;">转达账户：{{ this.cardid2 }}</p>
            <p style="font-size: 30px;">预计转账金额：{{ this.amount }}</p>
        </div>
        <div v-show="isShowPrint" class="transfer-info">
          <el-result icon="success" title="成功提示"></el-result>
          <h2 style="background-color: blueviolet;font-size: 40px;font-weight: 10;">交易成功</h2>
          <div>
            <p style="font-size: 30px;">流水号：{{ this.record.recordId }}</p>
            <p style="font-size: 30px;">转达账户：{{ this.record.cardId1 }}</p>
            <p style="font-size: 30px;">转账金额：{{ this.record.amount }}</p>
            <p style="font-size: 30px;">交易时间：{{ this.record.time}}</p>
          </div>
        </div>
        <div>
        <el-dialog :visible="messageDialog" title="重要提示" :append-to-body="true" class="custom-dialog">
          <!-- 对话框内容 -->
          <span class="dialog-content">{{ this.messageContent }}</span>
        </el-dialog>
        </div>
      </div>
    </div>
</template>
<script>
import atmheader from '../components/atmHeader.vue'
import request from '@/utils/request'
export default {
  components: {
    atmheader
  },
  mounted () {
    this.cardid2 = this.$route.params.cardid2
    this.amount = this.$route.params.amount
  },
  data () {
    return {
      cardid: '',
      cardid2: '',
      amount: 0,
      isShow: false,
      isShowNotPrint: true,
      isShowPrint: false,
      record: {},
      messageDialog: false,
      messageContent: ''
    }
  },
  methods: {
    navigateToUserOperation () {
      this.$router.push('/useroperation')
    },
    navigateToDeskTop () {
      // 执行页面跳转逻辑
      // 需要调用退卡动画
      this.$router.push('/')
    },
    confirmTransfer () {
      const cardInfo = JSON.parse(sessionStorage.getItem('cardInfo'))
      this.cardid1 = cardInfo.cardId
      const url = '/card/transfer?cardid1=' + this.cardid1 + '&amount=' + this.amount + '&cardid2=' + this.cardid2 + '&atmId=' + this.$store.state.atmId
      request.post(url).then(res => {
        if (res.code === '0') {
          this.record = res.data
          this.isShowNotPrint = false
          this.isShowPrint = true
        } else if (res.code === '1') {
          this.messageContent = '系统故障'
          setTimeout(() => {
            this.messageDialog = false
            this.navigateToDeskTop()
          }, 3000)
        } else {
          this.messageContent = res.msg
          setTimeout(() => {
            this.messageDialog = false
            this.navigateToUserOperation()
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

.transfer-info {
  position: relative;
  width: 650px;
  padding: 20px;
  left: 380px;
  top: 100px;
  background-color: #f2f2f2;
  border: 1px solid #ccc;
  border-radius: 5px;
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
