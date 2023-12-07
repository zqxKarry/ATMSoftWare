
<template>
    <div class="container">
      <div class="desktopBack">
      <atmheader></atmheader>
        <div>
          <el-button class="butt" style="top: 850px; margin-left: 0px" @click="navigateToUserOperation">
            <label class="fontStyle"><i class="el-icon-back"></i>返回</label>
          </el-button>
          <el-button class="butt" style="top: 850px; margin-left:1130px;" @click="navigateToDeskTop">
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
         <p style="font-size:20px;top: 20px;color: rgb(252, 240, 255);">注：显示近十条交易记录，如需查看更多，请去柜台或者手机银行查看</p>
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
  data () {
    return {
      records: []
    }
  },
  mounted () {
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
    navigateToDeskTop () {
      // 执行页面跳转逻辑
      // 需要调用退卡动画
      this.$router.push('/')
    },
    getTransactions () {
      const cardInfo = JSON.parse(sessionStorage.getItem('cardInfo'))
      const cardId = cardInfo.cardId
      const url = '/card/get-recentrecord?cardid=' + cardId
      request.get(url).then(res => {
        if (res.code === '0') {
          this.records = res.data
        }
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
      if (cardId1.length < 16) {
        if (cardId1.length === 8) {
          return 'atm:' + cardId1
        } else {
          return ''
        }
      } else {
        return cardId1
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
  margin: 20px auto;
  width: 1200px;
}
</style>
