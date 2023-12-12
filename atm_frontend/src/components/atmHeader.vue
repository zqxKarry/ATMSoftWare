<template>
  <div style="display:flex;height:180px;border-bottom: 5px dashed #afb69a;">
    <div class="imageB">
      <div class="image"></div>
    </div>
    <div class="title">
      <div style="height:30px"></div>
      <label class="chin">吉大软工T01银行</label>
      <div style="height:20px"></div>
      <label class="engl">T01 BANK OF JLU'SOFTWARE ENGINEERING</label>
    </div>
    <div style="display: flex 1px;width: 20%;"></div>
    <div class="clock">
      <div style="display: flex;justify-content: center;align-items: center;"><i class="el-icon-timer"></i><p style="height: 40px;width:100%;  font-size: 28px;"> {{ this.currentTimeData }}</p></div>
      <div style="display: flex;justify-content: center;align-items: center; font-size: 25px;height: 70px;"><div style="bottom: 2px;"><i class="el-icon-phone"></i> 24小时客服热线:0000-0101</div></div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      currentTimeData: ''
    }
  },
  mounted () {
    this.updateTime()
    setInterval(this.updateTime, 1000)
  },
  methods: {
    updateTime () {
      // const now = new Date()
      const now = new Date()
      const year = now.getFullYear()
      const month = now.getMonth() + 1
      const date = now.getDate()
      const dayOfWeek = now.getDay() // 获取星期几的数字
      const daysOfWeek = ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六']
      const hours = String(now.getHours()).padStart(2, '0')
      const minutes = String(now.getMinutes()).padStart(2, '0')
      const seconds = String(now.getSeconds()).padStart(2, '0')
      const dayOfWeekString = daysOfWeek[dayOfWeek] // 根据数字获取对应的星期几字符串
      const currentTime = `${year}-${month}-${date} ${hours}:${minutes}:${seconds} ${dayOfWeekString}`
      // console.log(currentTime);
      this.currentTimeData = currentTime
    }
  },
  computed: {
    currentTime () {
      return this.currentTimeData
    }
  }
}
</script>

<style>
.imageB {
  margin-left: 20px;
  top: 20px;
  width: 150px;
  height: 150px;
  border: 2px solid black;
  border-radius: 50%;
  overflow: hidden;
  position: relative;
  z-index: 1;
}
.imageB::before {
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
.title {
  top: 20px;
}
.chin {
  left: 175px;
  top: 40px;
  width: 500px;
  height: 80px;
  font-size: 70px;
  color:  rgb(95, 195, 33);
  text-shadow: 2px 2px 4px rgba(121, 218, 145, 0.5);
  font-family:仿宋;
}
.engl {
  left: 175px;
  top: 110px;
  width: 500px;
  height: 80px;
  font-size: 30px;
  font-family:Cambria, Cochin, Georgia, Times, 'Times New Roman', serif;
  text-shadow: 2px 2px 4px rgba(2, 26, 8, 0.5);
  color: rgb(111, 222, 27)
}
.clock {
  font-size: 20px;
  text-align: center;
  font-size: 2rem;
  font-weight: bold;
  margin-right: 10px;
  /* width: 35%; */
}
</style>
