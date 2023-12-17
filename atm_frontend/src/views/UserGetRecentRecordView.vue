
<template>
    <div class="container">
      <div class="numberboard">
        <KeyPad></KeyPad>
      </div>
      <div class="desktopBack">
      <atmheader></atmheader>
      <div class="countdown-container">
        <div class="countdown">
         <div class="countdown-timer">倒计时:{{ this.countdownTime }} s</div>
        </div>
      </div>
        <div>
          <el-button class="butt" style="top: 850px; margin-left: 0px" @click="navigateToUserOperation">
            <label class="fontStyle"><i class="el-icon-back"></i>返回</label>
          </el-button>
          <el-button class="butt" style="top: 850px; margin-left:1130px;" @click="navigateToDeskTopAndReturnCard">
            <label class="fontStyle">退卡<i class="el-icon-right"></i></label>
          </el-button>
        </div>
        <div class="transaction-table">
         <el-table :data="processedTransactions" >
            <el-table-column prop="transactionTimeFormatted" label="时间"></el-table-column>
            <el-table-column prop="recordId" label="流水号"></el-table-column>
            <el-table-column prop="amount" label="交易金额"></el-table-column>
            <el-table-column prop="transactionMethodFormatted" label="交易方式"></el-table-column>
            <el-table-column prop="balance" label="余额"></el-table-column>
            <el-table-column prop="transactionCardtd1Formatted" label="交易对方账户"></el-table-column>
          </el-table>
        </div>
        <div>
         <p style="font-size:20px;top: 20px;color: rgb(252, 240, 255);">{{this.messageTip}}</p>
        </div>
      </div>
      <div v-if="messageDialog" class = "dialog-overlay">
        <div  class="custom-dialog" :class="{'dialog-left': dialogLeft}" style="height: 400px;">
          <!-- 对话框内容 -->
          <span class="dialog-title">重要提示</span>
          <div class="dialog-content">{{ this.messageContent }}
          </div>
        </div>
      </div>
    </div>
</template>
<script>
import atmheader from '../components/atmHeader.vue'
import request from '@/utils/request'
import KeyPad from '@/components/KeyPad.vue'
import '@/assets/CSS/messageDialog.css'
import '@/assets/CSS/timeCounter.css'

export default {
  components: {
    atmheader,
    KeyPad
  },
  data () {
    return {
      countdownTime: 60,
      timer: null,
      records: [],
      messageTip: '注：显示近十条交易记录，如需查看更多，请去柜台或者手机银行查看',
      messageDialog: false,
      messageContent: ''
    }
  },
  mounted () {
    // ===========事件监听=========//
    this.startTimer()
    window.addEventListener('click', this.resetTimer)
    window.addEventListener('keydown', this.resetTimer)
    // ============================//
    this.getTransactions()
  },
  computed: {
    processedTransactions () {
      // 在这里对this.records进行处理，并返回处理后的结果
      // 例如，可以对金额进行格式化，对时间进行转换等
      return this.records.map(record => {
        const transactionMethodFormatted = this.formatTransactionMethod(record.opType)
        const transactionTimeFormatted = this.formatTransactionTime(record.time)
        const transactionCardtd1Formatted = this.formatTransactionCardid1(record.cardId1)
        return {
          ...record,
          transactionMethodFormatted,
          transactionTimeFormatted,
          transactionCardtd1Formatted
        }
      })
    }
  },
  methods: {
    navigateToUserOperation () {
      this.$router.push('/useroperation')
    },
    navigateToDeskTopAndReturnCard () {
      // 执行页面跳转逻辑
      // 执行退卡动画
      this.$router.push({
        name: 'desktop',
        params: {
          triggerMethod: true // 条件信息，设置为 true 表示满足条件}
        }
      })
    },
    navigateToDeskTopAndEatCard () {
      if (this.$route.name !== 'desktop') {
        this.$router.push({
          name: 'desktop',
          params: {
            eatCard: true
          }
        })
      }
    },
    getTransactions () {
      const cardInfo = JSON.parse(sessionStorage.getItem('cardInfo'))
      const cardId = cardInfo.cardId
      const url = '/card/get-recentrecord?cardid=' + cardId
      request.get(url).then(res => {
        if (res.code === '0') {
          this.records = res.data
        } else {
          this.messageTip = '系统错误，请退出重试或者到柜台办理相关业务'
        }
      }).catch(error => {
        this.showErrorMessage('网络错误\n请稍后重试或者更换机器' + error.message)
      })
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
      const cardInfo = JSON.parse(sessionStorage.getItem('cardInfo'))
      const cardId = cardInfo.cardId
      if (cardId1.length < 16) {
        if (cardId1.length === 8) {
          return cardId
        } else {
          return ''
        }
      } else {
        return cardId1
      }
    },
    startTimer () {
      this.timer = setInterval(() => {
        if (this.countdownTime > 0) {
          this.countdownTime--
          if (this.countdownTime === 10) {
            this.messageContent = '倒计时结束之前还未操作将会被吞卡,请执行您的操作'
            this.messageDialog = true
            this.oneORtwo = false
            setTimeout(() => {
              this.messageDialog = false
              this.cardPass = ''
            }, 3000)
          }
        } else {
          clearInterval(this.timer)
          this.navigateToDeskTopAndEatCard()
        }
      }, 1000)
    },
    resetTimer () {
      this.countdownTime = 60
    },
    showErrorMessage (msg) {
      this.messageContent = msg
      this.messageDialog = true
      setTimeout(() => {
        this.messageDialog = false
      }, 3000)
    }
  },
  beforeDestroy () {
    // 在组件销毁前移除事件监听器以及计时
    clearInterval(this.timer)
    window.removeEventListener('click', this.resetTimer)
    window.removeEventListener('keydown', this.resetTimer)
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
    justify-content: left; /* 水平居左 */
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

.transaction-table {
  margin-top: 50px;
  margin-left: 100px;
  /* margin: 20px auto; */
  width: 1200px;
}

.numberboard {
    /* 输入盘背景 */
    position: absolute;
    width: 628px;
    height: 780px;
    left: 1600px;
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

.countdown-container {
  width: 200px;
  position: relative;
  top: 10px; /* 距离顶部的距离 */
  left: 1200px; /* 距离右侧的距离 */
  z-index: 9999; /* 设置层级，确保在其他元素之上 */
}

.countdown {
  text-align: center;
  font-size: 24px;
  font-weight: bold;
  background-color: #f0f0f0;
  padding: 10px;
  border-radius: 4px;
}

.countdown-timer {
  margin-top: 10px;
}
</style>
