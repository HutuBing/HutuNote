/**
 * http配置
 */
import axios from 'axios'
import router from '@/router'
import storage from '@/utils/storage'
import { Message } from 'element-ui'
// axios 配置
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8'
axios.defaults.headers.post['Accept'] = 'application/json;charset=UTF-8'
// axios.defaults.transformRequest = [function (data) {
//   let ret = ''
//   for (let it in data) {
//     ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
//   }
//   return ret
// }]
// http request 拦截器
axios.interceptors.request.use(
  config => {
    const token = storage.getSession('token')
    if (token) {
      config.headers.common['Authorization'] = token
    }
    return config
  },
  err => {
    console.error(err)
    return Promise.reject(err)
  }
)

// http response 拦截器
axios.interceptors.response.use(
  response => {
    const token = response.headers['authorization']
    if (token) {
      storage.setSession('token', token)
    }
    // 导出excel
    if (response.config.responseType === 'blob') {
      const content = response.data
      if (content) {
        const elink = document.createElement('a') // 创建a标签
        // 获取文件名,根据后端返回不同获取的字段不同
        elink.download = decodeURI(escape(response.headers['content-disposition'].split('=')[1]))
        elink.style.display = 'none'
        const blob = new Blob([content], { type: 'application/vnd.ms-excel;charset=UTF-8' })
        elink.href = URL.createObjectURL(blob)
        document.body.appendChild(elink)
        elink.click() // 触发点击a标签事件
        document.body.removeChild(elink)
      } else {
        Message({
          message: '未知错误，请重试！',
          type: 'error'
        })
      }
    }
    return response
  },
  error => {
    if (error.response) {
      switch (error.response.status) {
        case 401:
          // 401 清除token信息并跳转到登录页面
          // loginOutData() // 退出登陆
          storage.clearAllSession()
          // 只有在当前路由不是登录页面才跳转
          router.currentRoute.path !== 'login' &&
            router.replace({
              path: 'login'
            })
      }
    }
    if (error.message === 'Network Error') {
      return Promise.reject({
        code: -10,
        message: '网络异常，请检查网络连接'
      })
    }
    return Promise.reject(error)
  }
)

export default axios
