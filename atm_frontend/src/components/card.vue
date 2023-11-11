<template>
  <div class="card0">
  <div style="width: 100%;height: 100%;" :key="card" @click="handleCardClick(card)">
    <div class="card-1-title">
      <div class="imagecard">
        <div class="image"></div>
      </div>
      <div class="titleCard">
        <div style="height:10px"></div>
        <label class="chincard">吉大软工T01银行</label>
        <div style="height: 2px;"></div>
        <label class="englcard">T01 BANK OF JLU'SOFTWARE ENGINEERING</label>
      </div>
    </div>
    <div class="card-2">
      <div class="magnetic">
        <div class="magnetic-image"></div>
      </div>
    </div>
    <div class="card-3">
      <div class="triangel"><i class="el-icon-caret-left"></i></div>
      <div>
        <div style="height:4px"></div>
        <span class="card-number">000012&nbsp;&nbsp;&nbsp;5678900000000</span>
      </div>
    </div>
    <div style="height: 5px;"></div>
    <div class="card-4-date">
      <div style="margin-left: 100px;font-size: 10px;">VALID<br>THRU</div>
      <div class="card-number" style="font-size: 20px">11/2019</div>
      <div class="parallelogram">
        <div class="parallelogram-content">
          <img src="../assets/union.png"  alt="UnionPay">
        </div>
      </div>
    </div>
  </div>
</div>
</template>

<script>
export default {
  data () {
    return {
    }
  },
  methods: {
    handleCardClick (card) {
      this.$emit('key-click', card) // 将点击事件传递给父组件
    }
  }
}

</script>

<style>
.card0 {
  /*银行卡*/
  /* position: absolute; */
  width: 570px;
  height: 360px;
  background-image: url("../assets/DingXin.png");
  border-radius: 20px;
}

.card-1-title {
    display:flex;
    height:86px;
    border-bottom: 2px solid #afb69a;
}

.imagecard {
  margin-left: 20px;
  width: 60px;
  height: 60px;
  top: 13px;
  border: 1px solid black;
  border-radius: 50%;
  overflow: hidden;
  position: relative;
  z-index: 1;
}
.imagecard::before {
  content: "";
  display: block;
  padding-bottom: 100%;
}
.image {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image: url("../assets/logo.png");
  background-size: cover;
  background-position: center;
}
.titleCard {
  top: 20px;
  margin-left: 0;
}
.chincard {
  left: 45px;
  top: 10px;
  width: 50px;
  height: 30px;
  font-size: 40px;
  color:  rgb(95, 195, 33);
  text-shadow: 2px 2px 4px rgba(121, 218, 145, 0.5);
  font-family:仿宋;
}
.englcard {
  left: 45px;
  width: 50px;
  height: 20px;
  font-size: 15px;
  font-family:Cambria, Cochin, Georgia, Times, 'Times New Roman', serif;
  text-shadow: 2px 2px 4px rgba(2, 26, 8, 0.5);
  color: rgb(111, 222, 27)
}

.card-2 {
  height: 80px;
  display: flex;
}
.triangel {
  font-size: 50px;
  width: 70px;
  height: 50px;
  left: 5px;
  top:100px
}

.magnetic {
  margin-left: 45px;
  width: 80px;
  height: 80px;
  top: 40px;
  border: 1px solid rgb(196, 196, 20);
  border-radius: 22%;
  position: relative;
  margin-right: 0;
}

.magnetic-image {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  border-radius: 22%;
  background-image: url("../assets/magnet.png");
  background-size: cover;
  background-position: center;
}

.card-3 {
  display: flex;
  margin-top:40px;
}

.card-number {
  font-family: Arial Black;
  font-size: 32px;
  font-weight: bold;
  font-stretch:extra-expanded;
  color: rgb(215, 236, 241);
  text-shadow: 2px 2px 0 silver;
  letter-spacing: 2px;
  margin-left: 0;
  top:20px;
}

.card-4-date {
  display: flex;
  top: 10px;
  margin-left: 50px;
}

.parallelogram {
  width: 140px;
  height: 95px;
  background-color: #f3efef;
  transform: skew(-15deg);
  transform-origin: top left;
  border-radius: 10%;
  overflow: hidden;
  position: relative;
  margin-left: 150px;
  margin-bottom: 100px;
}

.parallelogram-content {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  border: 1px solid #f40909;
  border-radius: 10%;
  width: 90%;
  height: 90%;
  overflow: hidden;
}

.parallelogram-content img {
  position: absolute;
  top: 50%;
  left: 50%;
  width: 100%;
  height: 100%;
  transform: translate(-50%, -50%);
  object-fit: cover
}
</style>
