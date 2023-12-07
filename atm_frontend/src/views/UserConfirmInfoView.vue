
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
          <el-button v-show="isShowPrint" class="butt" style="top: 850px;margin-left: 0px;" @click="getRecordInfo">
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
import pdfMake from 'pdfmake/build/pdfmake'
import pdfFonts from 'pdfmake/build/vfs_fonts'
export default {
  components: {
    atmheader
  },
  mounted () {
    pdfMake.vfs = pdfFonts.pdfMake.vfs
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
    },
    generateAndPrintPDF () {
      const transactionMethodFormatted = this.formatTransactionMethod(this.transactionDetails.opType)
      const transactionTimeFormatted = this.formatTransactionTime(this.transactionDetails.time)
      const transactionCardtd1Formatted = this.formatTransactionCardid1(this.transactionDetails.cardId1)
      const docDefinition = {
        content: [
          { text: '    吉大软工T01银行ATM 凭条', style: 'header' },
          '================================================',
          { text: '交易时间 Date and Time: ' + transactionTimeFormatted, style: 'normal' },
          { text: '本人账户 Account No.: ' + this.cardId, style: 'normal' },
          { text: '终端编号 Terminal: ' + this.$store.state.atmId, style: 'normal' },
          { text: '交易类型 Trans.Type: ' + transactionMethodFormatted, style: 'normal' },
          { text: '交易金额 Amount: ' + this.transactionDetails.amount.toFixed(2), style: 'normal' },
          { text: '账户余额 Balance: ' + this.transactionDetails.balance.toFixed(2), style: 'normal' },
          { text: '交易账户 Transfer to: ' + transactionCardtd1Formatted, style: 'normal' },
          { text: '交易结果 Result:  成功/OK', style: 'normal' },
          { text: '交易序号 Serial No. : ' + this.transactionDetails.recordId, style: 'normal' },
          '================================================',
          '感谢您选择我们的 ATM 服务。',
          '如有任何疑问，请致电客服热线:0000-0101。'
        ],
        styles: {
          header: {
            fontSize: 24,
            bold: true,
            // alignment: 'center',
            margin: [0, 0, 0, 10]
          },
          normal: {
            fontSize: 12,
            bold: true,
            lineHeight: 0.5,
            // alignment: 'center',
            margin: [0, 0, 0, 20]
          }
        },
        defaultStyle: {
          font: '方正姚体'
        }
      }
      pdfMake.fonts = {
        Roboto: {
          normal: 'Roboto-Regular.ttf',
          bold: 'Roboto-Medium.ttf',
          italics: 'Roboto-Italic.ttf',
          bolditalics: 'Roboto-Italic.ttf'
        },
        方正姚体: {
          normal: 'FZYTK.TTF',
          bold: 'FZYTK.TTF',
          italics: 'FZYTK.TTF',
          bolditalics: 'FZYTK.TTF'
        }
      }
      pdfMake.createPdf(docDefinition).download('voucher.pdf')
      this.messageContent = '完成打印,请收好凭条'
      setTimeout(() => {
        this.messageDialog = true
      }, 2000)
      setTimeout(() => {
        this.messageDialog = false
        this.navigateToUserOperation()
      }, 3000)
    },
    formatTransactionMethod (opType) {
      // 根据实际需求进行交易类型的替换
      // 将英文的交易类型替换为对应的汉字
      if (opType === 'store') {
        return '存款'
      } else if (opType === 'take') {
        return '取款'
      } else if (opType === 'transadd') {
        return '转入'
      } else if (opType === 'transreduce') {
        return '转出'
      } else {
        return ''
      }
    },
    formatTransactionTime (transactionTime) {
      // 根据实际需求进行时间格式的处理
      const date = new Date(transactionTime)
      const year = date.getFullYear()
      const month = String(date.getMonth() + 1).padStart(2, '0')
      const day = String(date.getDate()).padStart(2, '0')
      const hour = String(date.getHours()).padStart(2, '0')
      const minute = String(date.getMinutes()).padStart(2, '0')
      const second = String(date.getSeconds()).padStart(2, '0')
      return `${year}-${month}-${day} ${hour}:${minute}:${second}`
    },
    formatTransactionCardid1 (cardId1) {
      if (cardId1.length < 16) {
        return ''
      } else {
        return cardId1
      }
    },
    getRecordInfo () {
      this.isShow = true
      const url = '/card/get-onerecord?recordid=' + this.record.recordId
      request.get(url).then(res => {
        if (res.code === '1') {
          this.isShow = false
          this.messageContent = res.msg
          setTimeout(() => {
            this.messageDialog = true
          }, 2000)
          setTimeout(() => {
            this.messageDialog = false
          }, 2000)
        } else {
          this.transactionDetails = res.data
          this.generateAndPrintPDF()
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
