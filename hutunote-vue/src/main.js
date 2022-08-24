import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import axios from './utils/http'
Vue.prototype.$http = axios

import api from './api/index'
Vue.prototype.api = api

import ELEMENT from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
Vue.use(ELEMENT)

import '@/global' // 注册全局组件
// svg组件
import SvgIcon from './components/SvgIcon'
import '@/icons/iconfont.js'
Vue.component(SvgIcon.name, SvgIcon)

import lodash from 'lodash'
Vue.prototype.$lodash = lodash

// 全局mixin
import { mixinComm } from '@/utils/mixin.js'
Vue.mixin(mixinComm)

import utils from '@/utils/utils.js'
Vue.prototype.$utils = utils

// 引入mockjs
// require('./mock/index.js')

// filters
import './filters'

Vue.config.productionTip = false

import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
Vue.use(mavonEditor)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
