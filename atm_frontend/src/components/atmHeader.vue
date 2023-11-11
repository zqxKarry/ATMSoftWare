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
  </div>
</template>

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
</style>
