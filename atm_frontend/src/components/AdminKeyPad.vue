<template>
  <div>
    <h2>管理员输入键盘</h2>
    <div class="keypad">
      <div class="row" v-for="row in keypadRows" :key="row">
        <div class="key key-cell" v-for="key in row" :key="key" @click="handleKeyClick(key)">
        {{ key }}
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      keypadRows: [[1, 2, 3, '#'], [4, 5, 6, '%'], [7, 8, 9, 'D'], ['退格', 0, '确认', '重置']]
    }
  },
  methods: {
    handleKeyClick (key) {
      this.$emit('key-click', key) // 将点击事件传递给父组件
    }
  }
}
</script>

<style>
.keypad {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.row {
  display: flex;
  justify-content: center;
}

.key {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 10px;
  border: 3px solid #c0c0c0;
  cursor: pointer;
  margin: 20px;
  transition: box-shadow 0.3s; /* 添加过渡效果 */
}

.key-cell {
  /* 添加您想要的样式 */
  width: 90px;
  height: 110px;
  left: 66px;
  background: rgb(255, 255, 255);
  border-radius: 20px;
  font-size: 45px;
  font-family: 等线;
  /* 其他样式属性 */
}

.key:hover,
.key:focus {
  box-shadow: 0 0 8px rgba(244, 5, 41, 0.889); /* 添加点击时的阴影效果 */
}
</style>
