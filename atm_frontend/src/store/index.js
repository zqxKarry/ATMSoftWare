import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    cardInfo: null,
    adminInfo: null,
    atmId: '00000000'
  },
  getters: {
  },
  mutations: {
    setAdminInfo (state, adminInfo) {
      state.adminInfo = adminInfo
    },
    setCardInfo (state, cardInfo) {
      state.cardInfo = cardInfo
    }
  },
  actions: {
  },
  modules: {
  }
})
