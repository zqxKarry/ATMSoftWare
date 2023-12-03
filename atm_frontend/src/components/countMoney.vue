<template>
  <div class="money-animation">
    <div class="counting-machine">
      <div class="conveyor-belt">
        <div class="notes-container">
          <div v-for="(note, index) in notes" :key="index" class="note" :style="getNoteStyle(index)">
            <img :src="getNoteImage()" alt="Note">
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style>
.money-animation {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  perspective: 1000px;
}

.counting-machine {
  width: 500px;
  height: 300px;
  background-color: #f8f8f8;
  border: 1px solid #ccc;
  border-radius: 5px;
  overflow: hidden;
  position: relative;
}

.conveyor-belt {
  width: 100%;
  height: 100%;
  position: relative;
  overflow: hidden;
}

/* @keyframes conveyorBeltMove {
  0% {
    transform: translateY(100%);
  }
  100% {
    transform: translateY(-100%);
  }
} */

@keyframes noteFall {
  0% {
    transform: translateY(0%) rotateX(0deg);
  }
  50% {
    transform: translateY(50%) rotateX(-90deg);
  }
  100% {
    transform: translateY(100%) rotateX(-180deg);
  }
}

.notes-container {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column-reverse; /* 修改为从下到上的方向排列 */
  align-items: center;
  position: absolute;
  bottom: 0;
}

.note {
  /* 修改动画持续时间为1秒 */
  animation: noteFall 0.1s infinite ease-in-out;
  /* animation: conveyorBeltMove 0.1s infinite linear; */

}

.note img {
  width: 100%;
  height: 100%;
}

</style>

<script>
export default {
  data () {
    return {
      notes: []
    }
  },
  mounted () {
    this.generateNotes()
  },
  methods: {
    generateNotes () {
      const numberOfNotes = 1 // 需要的钞票数量
      this.notes = Array.from({ length: numberOfNotes })
    },
    getNoteImage () {
      return require('@/assets/rmb.png')
    },
    getNoteStyle (index) {
      return {
        animationDelay: `${index * 0.1}s`
      }
    }
  }
}
</script>
