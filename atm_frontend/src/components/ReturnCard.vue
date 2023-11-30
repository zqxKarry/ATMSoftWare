<template>
  <div>
    <div>
    <div style="position: relative; height:10px ;background-color: black;">
    </div>
    <div class="card-slot">
      <transition name="card-return" @before-enter="beforeEnter" @enter="enter">
        <div v-if="isCardReturned" class="card">
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
      isCardReturned: false
    }
  },
  methods: {
    startReturnAnimation () {
      this.isCardReturned = true
    },
    beforeEnter (el) {
      el.style.transform = 'rotate(90deg) translateY(0) translateX(-100%)'
    },
    enter (el, done) {
      setTimeout(() => {
        el.style.transform = 'rotate(90deg) translateY(0) translateX(5%)'
        // done()
      }, 1000)
    },
    Rreturn () {
      this.isCardReturned = false
    }
  }
}
</script>

<style>
.card-slot {
  width: 380px;
  height: 600px;
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

.card-return-enter-active,
.card-return-leave-active {
  transition: all 4s ease;
}

.card-return-enter {
  opacity: 100;
}

.card-return-leave-to {
  opacity: 0;
}
</style>
