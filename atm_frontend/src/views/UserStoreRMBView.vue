<template>
    <div class="container">
      <div class="desktopBack">
      <atmheader></atmheader>
        <div>
          <el-button class="butt" style="top: 850px;" @click="navigateToUserOperation">
            <label class="fontStyle"><i class="el-icon-back"></i>返回上级</label>
          </el-button>
        </div>
        <div class="board1">
          <div class="board2">
            <div class="board3"></div>
            <img src="../assets/rmb.png" alt="rmb" class="overlay-image">
          </div>
          <div style="display:flex;margin-top: 250px;">
            <div class="input-container">
             <div><label style = "font-size: 40px;color:rgb(196, 213, 255);">真钞</label><input v-model = "reallyNum" type="number" class="number-input" placeholder="0"></div>
             <div><label style = "font-size: 40px;color:rgb(196, 213, 255);">假钞</label><input v-model = "fakeNum" type="number" class="number-input" placeholder="0"></div>
           </div>
           <div style="display: flex;justify-content: center;align-items: center;">
            <el-button style="font-size: large;width: 140px;height: 60px;font-size: 40px;" @click="finished">放入</el-button>
           </div>
          </div>
        </div>
      </div>
      <el-dialog
        title="正在验钞请等待..."
        :visible="countMoneyDialog"
        width="50%">
        <span>
          <div style="height: 400px;">
            <i class="el-icon-loading" style="font-size: 50px;"></i>
            <!-- 对话框内容 -->
            <div style="height:30px"></div>
            <div style="display:flex">
              <div style="width:35%"></div>
              <countmoney ref="countmoneyref"></countmoney>
              <div style="width:30%"></div>
            </div>
          </div>
        </span>
      </el-dialog>
      <div>
        <el-dialog :visible="countFinishDialog" title="点钞结束已经存入" :append-to-body="true" class="custom-dialog">
          <!-- 对话框内容 -->
          <span class="money-case">
            共<span class="special"> {{ this.sumNum }}  </span>张,<span class="special"> {{ this.reallyNum }} </span>张识别成功<br>
            <span v-if = "isAtmBoxFull"> 机箱余量不足,请更换机器或到柜台办理</span>
            共存入<span class="special"> {{ this.storeNum }}  </span>张100元人民币<br>
            剩余<span class="special"> {{ this.restNum }}  </span>张100元人民币可识别但未存入<br>
            <span  v-show = "haveFakeMoney"><span class="special"> {{ this.fakeNum }} </span>张不可识别,请取出</span><br>
          </span><br>
        </el-dialog>
      </div>
      <div>
        <el-dialog :visible="messageDialog" title="重要提示" :append-to-body="true" class="custom-dialog">
        <!-- 对话框内容 -->
        <span class="dialog-content">{{ this.messageContent }}<br>
        </span><br>
        <!-- 机会提示区域 -->
        <div v-if="oneORtwo" style="font-size: 30px;text-align: center;width:100%;box-sizing: border-box;">你还有{{ this.num }}次机会</div>
      </el-dialog>
      </div>
    </div>
</template>
<script>
import atmheader from '../components/atmHeader.vue'
import countmoney from '../components/countMoney.vue'
import request from '@/utils/request'
export default {
  components: {
    atmheader,
    countmoney
  },
  data () {
    return {
      reallyNum: 0, // 真钞数量
      fakeNum: 0, // 假钞数量
      sumNum: 0, // 放入总数量
      restNum: 0, // 箱内剩余的(包括无法识别的以及超过额度剩下的)
      storeNum: 0, // 真正存入的数量
      countMoneyDialog: false,
      countFinishDialog: false,
      messageContent: '',
      messageDialog: false,
      countMoneyTime: 2000,
      isAtmBoxFull: false,
      haveFakeMoney: false
    }
  },
  methods: {
    navigateToDeckTop () {
      this.$router.push('/')
    },
    navigateToUserOperation () {
      this.$router.push('/useroperation')
    },
    navigateToUserStoreSuccess (recordid) {
      this.$router.push({
        name: 'userstoresuccess',
        params: {
          storeNum: this.storeNum,
          recordid: recordid
        }
      })
    },
    async finished () {
      await this.beginCountMoney()
      if (this.isEmptyOROver() === 1) {
        this.messageContent = '您未放入钱币,如要存款,请将钱币放入存款槽'
        this.messageDialog = true
        setTimeout(() => {
          this.messageDialog = false
        }, 2000)
      } else if (this.isEmptyOROver() === 2) {
        this.messageContent = '单次限存100张,请您重新放钞'
        this.messageDialog = true
        setTimeout(() => {
          this.messageDialog = false
        }, 2000)
      } else {
        // 去存钱
        if (this.fakeNum > 0) {
          this.haveFakeMoney = true
        } else {
          this.haveFakeMoney = false
        }
        this.storeRMB()
      }
    },
    beginCountMoney () {
      return new Promise((resolve, reject) => {
        this.countMoneyDialog = true
        this.sumNum = parseInt(this.reallyNum) + parseInt(this.fakeNum)
        this.countMoneyTime = this.sumNum / 10 * 1000
        setTimeout(() => {
          this.countMoneyDialog = false
          resolve() // 表示异步操作完成
        }, this.countMoneyTime)
      })
    },
    storeRMB () {
      const cardInfo = JSON.parse(sessionStorage.getItem('cardInfo'))
      const cardId = cardInfo.cardId
      const url = '/card/user-storermb?cardid=' + cardId + '&reallyNum=' + this.reallyNum + '&atmId=' + this.$store.state.atmId
      request.post(url).then(res => {
        if (res.code === '1') {
          // 出错了 转到桌面,退卡,钱不会吞掉
          this.messageContent = '机器故障请重试或者更换机器'
          this.messageDialog = true
          setTimeout(() => {
            this.messageDialog = false
            this.navigateToDeckTop()
          }, 5000)
        } else if (res.code === '2') {
          // 机箱余量不足 吞入一部分钱或者一分钱不存
          this.isAtmBoxFull = true
          if (res.data === null) {
            // 一分钱没存
            this.storeNum = 0
            this.restNum = this.reallyNum - this.storeNum
            this.countFinishDialog = true
            setTimeout(() => {
              this.countFinishDialog = false
            }, 5000)
          } else {
            this.storeNum = res.data.amount / 100
            this.restNum = this.reallyNum - this.storeNum
            this.countFinishDialog = true
            setTimeout(() => {
              this.countFinishDialog = false
              this.navigateToUserStoreSuccess(res.data.recordId)
            }, 5000)
          }
        } else {
          this.isAtmBoxFull = false
          this.storeNum = res.data.amount / 100
          this.restNum = this.reallyNum - this.storeNum
          this.countFinishDialog = true
          setTimeout(() => {
            this.countFinishDialog = false
            this.navigateToUserStoreSuccess(res.data.recordId)
          }, 5000)
        }
      })
    },
    isEmptyOROver () {
      if (this.reallyNum === 0 && this.fakeNum === 0) {
        return 1
      } else if (this.sumNum > 100) {
        return 2
      } else {
        return 3
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

/* 按钮 */
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

.board1 {
  justify-content: center; /* 水平居中 */
  position: absolute;
  width: 800px;
  height: 680px;
  left: 550px;
  right: 30px;
  top: 208px;
  bottom: 129px;
  border-radius: 20px;
  background: rgb(31, 32, 51);
}

.board2 {
  position: relative;
  width: 620px;
  height: 256px;
  left: 91px;
  right: 124px;
  top: 68px;
  bottom: 363px;
  border-radius: 20px;
  background: rgb(72, 81, 113);
}

.board3 {
  position: relative;
  width: 540px;
  height: 162px;
  left: 38px;
  right: 37px;
  top: 42px;
  bottom: 52px;
  border-radius: 20px;
  background: rgb(8, 10, 34);
}

.overlay-image {
  position: absolute;
  top: 50%;
  left: 47%;
  transform: translate(-50%, -20%) skew(-10deg, 0deg) rotateX(-50deg); /* 向后倾斜 */;
  /* 设置图片的宽度和高度 */
  width: 480px;
  height: 340px;
}

.input-container {
  position: relative;
  display: flex;
  justify-content: center; /* 水平居中 */
  flex-direction: column;
  width: 70%;
}

.number-input {
  position: relative;
  margin-left: 20px;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  margin-bottom: 10px;
  height: 50px;
  width: 20%;
  background-color: cornflowerblue;
  font-size: 40px;
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

.money-case {
  font-size: 60px;
  font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
  color: rgb(32, 106, 224);
  text-align: center;
}

.special {
  color: rgb(234, 19, 19);
  font-family: 黑体;
  font-size: 65px;
  font-weight: 400;
  line-height: 70px;
  letter-spacing: 0px;
  text-align: left;
}
</style>
