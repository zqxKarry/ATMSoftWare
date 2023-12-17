<template>
  <div class="container">
    <div class="numberboard">
       <Keypad></Keypad>
    </div>
    <div class="desktopBack">
      <atmheader></atmheader>
      <el-button class="MangerEnterButt" style="top:850px" @click="navigateToAdminCheckId">
        <label class="fontStyle"><i class="el-icon-back"></i>管理员</label>
      </el-button>
      <div class="card-slot0">
        <el-input v-model="cardid" style="width:80%;color:bisque" placeholder="请输入16位银行卡号"></el-input>
      </div>
      <div class="cardArea" @click="insertCard">
        <card @key-click="handleCardClick"></card>
      </div>
      <div v-if="readingCardDialog" class = "dialog-overlay">
      <div class="custom-dialog" :class="{'dialog-left': dialogLeft}" style="height: 500px;">
        <span class="dialog-title">正在读卡....</span>
        <span>
          <div style="height: 400px;">
            <i class="el-icon-loading" style="font-size: 50px;"></i>
            <div style="height:30px"></div>
            <div style="display:flex">
              <div style="width:35%"></div>
              <readcard ref="insertcardref"></readcard>
              <div style="width:30%"></div>
            </div>
          </div>
        </span>
      </div>
      </div>
      <div v-if="returnCardDialog" class = "dialog-overlay">
      <div class="custom-dialog" :class="{'dialog-left': dialogLeft}" style="height: 500px;">
        <span class="dialog-title">正在退卡....</span>
        <span>
          <span style="font-size:20px">{{ this.message }} 请收好</span>
          <div style="height: 400px;">
            <i class="el-icon-loading" style="font-size: 50px;"></i>
            <!-- 对话框内容 -->
            <div style="height:30px"></div>
            <div style="display:flex">
              <div style="width:35%"></div>
              <returncard ref="returncardref"></returncard>
              <div style="width:30%"></div>
            </div>
          </div>
        </span>
      </div>
      </div>
    </div>
    <div v-if="messageDialog" class = "dialog-overlay">
        <div class="custom-dialog" :class="{'dialog-left': dialogLeft}">
          <!-- 对话框内容 -->
          <span class="dialog-title">重要提示</span>
          <div class="dialog-content">{{ this.messageContent }}</div>
        </div>
      </div>
  </div>
</template>
<script>
import card from '../components/card.vue'
import atmheader from '../components/atmHeader.vue'
import readcard from '../components/ReadCard.vue'
import returncard from '../components/ReturnCard.vue'
import Keypad from '../components/KeyPad.vue'
import request from '@/utils/request'
import '@/assets/CSS/messageDialog.css'

export default {
  components: {
    card,
    atmheader,
    readcard,
    returncard,
    Keypad
  },
  data () {
    return {
      returnCardDialog: false,
      readingCardDialog: false,
      cardid: '',
      messageContent: '',
      messageDialog: false
    }
  },
  created () {
    const triggerMethod = this.$route.params.triggerMethod
    const eatCard = this.$route.params.eatCard
    if (triggerMethod) {
      this.returnCard() // 触发 returnCard 方法
    } else if (eatCard) {
      this.messageContent = '卡已被吞,请联系管理员取出'
      this.messageDialog = true
      setTimeout(() => {
        this.messageDialog = false
      }, 2000)
    }
  },
  methods: {
    navigateToAdminCheckId () {
      // 执行页面跳转逻辑
      this.$router.push('/admincheckid')
    },
    insertCard () {
      /* 判断卡号是否合法 */
      if (this.validateCardNumber(this.cardid) === true) {
        /* 点击银行卡进行读卡 */
        this.readingCardDialog = true
        this.startInsertionAnimation()
        setTimeout(() => {
          this.checkCardId(this.cardid)
        }, 4000)
      } else {
        this.messageContent = '银行卡号非法,请输入十六位数字银行卡号'
        this.messageDialog = true
        setTimeout(() => {
          this.cardid = ''
          this.messageDialog = false
        }, 2000)
      }
    },
    startInsertionAnimation () {
      this.$nextTick(() => {
        this.$refs.insertcardref.startInsertionAnimation()
      })
    },
    returnCard () {
      this.returnCardDialog = true
      this.startReturnAnimation()
      setTimeout(() => {
        this.returnCardDialog = false
        this.$nextTick(() => {
          // 退卡动画结束
          this.$refs.returncardref.Rreturn()
        })
      }, 5000)
    },
    startReturnAnimation () {
      this.$nextTick(() => {
        this.$refs.returncardref.startReturnAnimation()
      })
    },
    checkCardId (cardId) {
      this.readingCardDialog = false
      this.$nextTick(() => {
        this.$refs.insertcardref.Rreturn()
      })
      const url = '/card/check-id?cardid=' + cardId
      request.get(url).then(res => {
        if (res.code === '0') {
          this.navigateToUserCheckPass(cardId)
        } else {
          this.message = res.msg
          this.returnCard()
        }
      }).catch(error => {
        this.cardId = ''
        this.showErrorMessage(error.message)
      })
    },
    navigateToUserCheckPass (cardId) {
      // 执行页面跳转逻辑
      this.$router.push({
        name: 'usercheckpass',
        params: {
          cardId: cardId
        }
      })
    },
    validateCardNumber () {
      const regex = /^\d{16}$/
      const isValid = regex.test(this.cardid)
      if (!isValid) {
        return false
      } else {
        return true
      }
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
  justify-content: left; /* 水平居左 */
  align-items: center; /* 垂直居中 */
}
.MangerEnterButt {
  /* 管理员入口按钮 */
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

.card-slot0 {
  /* 入卡口、入钞口 */
  position: absolute;
  width: 522px;
  height: 98px;
  left: 850px;
  right: 26px;
  top: 210px;
  bottom: 727px;
  background: rgb(72, 81, 113);
  border-radius: 20px;
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
}

.cardArea {
  /* 银行卡位置 */
  position: absolute;
  width: 570px;
  height: 360px;
  top: 350px;
  bottom: 101px;
  left: 1290px;
  right: 100px;
  border-radius: 20%;
  /* 旋转 */
  transform: rotate(90deg);
  transform-origin: left top;
}

</style>
