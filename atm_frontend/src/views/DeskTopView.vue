<template>
  <div class="container">
    <div class="desktopBack">
      <atmheader></atmheader>
      <el-button class="MangerEnterButt" style="top:850px" @click="navigateToAdminCheckId">
        <label class="fontStyle"><i class="el-icon-back"></i>管理员</label>
      </el-button>
      <div class="card-slot0">
        <el-input v-model="cardNumber" style="width:80%;color:bisque" placeholder="请输入16位银行卡号"></el-input>
      </div>
      <div class="cardArea" @click="insertCard">
        <card @key-click="handleCardClick"></card>
      </div>
      <el-dialog
        title="正在读卡请稍后..."
        :visible="readingCardDialog"
        :custom-class="['reading-card-dialog']"
        width="50%"
        :before-close="handleClose">
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
      </el-dialog>
    </div>
  </div>
</template>
<script>
import card from '../components/card.vue'
import atmheader from '../components/atmHeader.vue'
import readcard from '../components/ReadCard.vue'
export default {
  components: {
    card,
    atmheader,
    readcard
  },
  data () {
    return {
      readingCardDialog: false
    }
  },
  methods: {
    navigateToAdminCheckId () {
      // 执行页面跳转逻辑
      this.$router.push('/admincheckid')
    },
    insertCard () {
      /* 点击银行卡进行读卡 */
      this.readingCardDialog = true
      this.startInsertionAnimation()
      setTimeout(() => {
        this.readingCardDialog = false
        this.$nextTick(() => {
          this.$refs.insertcardref.Rreturn()
        })
      }, 5000)
    },
    startInsertionAnimation () {
      // this.$refs.insertcardref.startInsertionAnimation()
      this.$nextTick(() => {
        this.$refs.insertcardref.startInsertionAnimation()
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

.welcome {
  /* 欢迎使用吉大T1银行！ */
  position: absolute;
  width: 938px;
  height: 160px;
  left: 25px;
  right: 477px;
  top: 119px;
  bottom: 745px;
  color: rgb(72, 161, 87);
  font-family: 黑体;
  font-size: 72px;
  font-weight: 400;
  line-height: 86px;
  letter-spacing: 0px;
  text-align: left;
  font-family:Cambria, Cochin, Georgia, Times, 'Times New Roman', serif;
  text-shadow: 2px 2px 4px rgba(2, 26, 8, 0.5);
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

.reading-card-diolog {
  border: 2px solid red;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
  width: 50%;
  height: 30px;
  z-index: 9999;
}
</style>
