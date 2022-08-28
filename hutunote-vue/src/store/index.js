import Vue from 'vue'
import Vuex from 'vuex'
import common from './modules/common'
import note from './modules/note'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {},
  modules: {
    common,
    note
  }
})
