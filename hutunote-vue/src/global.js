/*
 * @Description: 注册全局组件
 * @Autor: liaojb
 * @Date: 2021-11-25 10:56:56
 * @LastEditors: liaojb
 * @LastEditTime: 2021-11-29 10:48:07
 */
import Vue from 'vue'
import Breadcrumb from '@/components/Breadcrumb'
import AppUpload from '@/components/AppUpload'

// 分页组件
Vue.component('Breadcrumb', Breadcrumb)
Vue.component('AppUpload', AppUpload)

