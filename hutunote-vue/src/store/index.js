import Vue from 'vue'
import Vuex from 'vuex'
import commonHttp from './modules/common-http'
import modalConfig from './modules/modal-config'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {},
  modules: {
    commonHttp,
    modalConfig
  }
})
