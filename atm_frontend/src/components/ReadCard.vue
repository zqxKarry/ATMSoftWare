<template>
  <div>
  <div>
  <div style="position: relative; height:10px ;background-color: black;">
  </div>
  <div class="card-slot">
    <transition name="card-insertion" @before-enter="beforeEnter" @enter="enter">
      <div v-if="isCardInserted" class="card">
        <card></card>
      </div>
    </transition>
  </div>
</div>
</div>
</template>

<script>
import card from '../components/card.vue'
export default {
  props: {
    customClass: {
      type: String,
      default: ''
    }
  },
  components: {
    card
  },
  data () {
    return {
      isCardInserted: false
    }
  },
  methods: {
    startInsertionAnimation () {
      this.isCardInserted = true
    },
    beforeEnter (el) {
      el.style.transform = 'rotate(90deg)'
    },
    enter (el, done) {
      setTimeout(() => {
        el.style.transform = 'rotate(90deg) translateY(0) translateX(-100%)'
        // done()
      }, 1000)
    },
    Rreturn () {
      this.isCardInserted = false
    }
  }
}
</script>

<style>
.card-slot {
  /* position: relative; */
  /* top:200px; */
  width: 380px;
  height: 600px;
  /* background-color: #f2f2f2; */
  border-radius: 10px;
  overflow: hidden;
}

.card {
  position: relative;
  width: 570px;
  height: 360px;
  background-color: #00c853;
  border-radius: 20px;
  transform-origin: top right;
  left: -200px;
  top: 550px
}

.card.hide {
  transform: translateX(100%);
}

.card-insertion-enter-active,
.card-insertion-leave-active {
  transition: all 4s ease;
}

.card-insertion-enter {
  opacity: 100;
}

.card-insertion-leave-to {
  opacity: 0;
}
</style>
