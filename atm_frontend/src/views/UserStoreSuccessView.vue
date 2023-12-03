<template>
    <div class="container">
      <div class="desktopBack">
      <atmheader></atmheader>
        <div>
          <el-button class="butt" style="top: 850px;" @click="navigateToDeskTop">
            <label class="fontStyle"><i class="el-icon-back"></i>退卡</label>
          </el-button>
          <el-button class="butt" style="top: 650px;margin-left:0px;" @click="navigateToUserOperation">
            <label class="fontStyle"><i class="el-icon-back"></i>返回</label>
          </el-button>
          <el-button class="butt" style="top: 850px; margin-left:1130px;" @click="generatePDF">
            <label class="fontStyle">打印凭条<i class="el-icon-right"></i></label>
          </el-button>
          <el-button class="butt" style="top: 650px; margin-left:1130px;" @click="navigateToStoreRMB">
            <label class="fontStyle">继续放钞<i class="el-icon-right"></i></label>
          </el-button>
        </div>
      </div>
      <div class="messageBox">
        <span class="money-case">
          <span class="special" style="color: blueviolet;">操作结果</span><br>
           <span class="special"> </span><br>
            存入<span class="special"> {{ this.storeNum }}  </span>张100元人民币<br>
            共计<span class="special"> {{ this.price }} </span>元
          </span><br>
      </div>
    </div>
</template>
<script>
// import request from '@/utils/request'
import atmheader from '../components/atmHeader.vue'
// import pdfMake from 'pdfmake/build/pdfmake'
// /* eslint-disable camelcase */
// const vfs_fonts = require('@/assets/fonts')
// /* eslint-disable camelcase */
// pdfMake.vfs = vfs_fonts.pdfMake.vfs

export default {
  components: {
    atmheader
  },
  data () {
    return {
      storeNum: 0, // 操作数量
      price: 0,
      recordId: ''
    }
  },
  mounted () {
    this.writePage()
  },
  methods: {
    navigateToDeskTop () {
      // 执行退卡动画
      this.$router.push('/')
    },
    navigateToStoreRMB () {
      this.$router.push('/userstorermb')
    },
    navigateToUserOperation () {
      this.$router.push('/useroperation')
    },
    writePage () {
      this.storeNum = this.$route.params.storeNum
      this.recordId = this.$route.params.recordid
      this.price = this.storeNum * 100.00
    },
    printVoucher () {

    },
    // generatePDF () {
    //   const documentDefinition = {
    //     content: [
    //       {
    //         text: '你好，世界！',
    //         font: 'simsunb',
    //         fontSize: 12
    //       }
    //     ],
    //     defaultStyle: {
    //       font: '方正姚体',
    //       header: {
    //         fontSize: 22,
    //         bold: true,
    //         margin: [5, 50]
    //       },
    //       splitInfo: {
    //         fontSize: 22,
    //         color: 'blue'
    //       }
    //     }
    //   }
    //   pdfMake.fonts = {
    //     Roboto: {
    //       normal: 'Roboto-Regular.ttf',
    //       bold: 'Roboto-Medium.ttf',
    //       italics: 'Roboto-Italic.ttf',
    //       bolditalics: 'Roboto-Italic.ttf'
    //     },
    //     方正姚体: {
    //       normal: 'SIMLI.TTF',
    //       bold: 'SIMLI.TTF',
    //       italics: 'SIMLI.TTF',
    //       bolditalics: 'SIMLI.TTF'
    //     }
    //   }
    //   const pdfDocGenerator = pdfMake.createPdf(documentDefinition)
    //   // 下载PDF
    //   pdfDocGenerator.download('ATM_voucher.pdf')
    // },
    getRecordInfo () {

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
.messageBox {
  position: absolute;
  width: 700px;
  height: 455px;
  margin-left: 100px;
  margin-right: 138px;
  margin-top: 50px;
  background: rgb(172, 140, 140);
  border-radius: 20px;
}
.balaFontStyle {
  color: rgb(99, 11, 11);
  font-family: 黑体;
  font-size: 60px;
  font-weight: 400;
  line-height: 75px;
  letter-spacing: 0px;
  text-align: left;
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
