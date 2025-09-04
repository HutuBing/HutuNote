/* eslint-disable no-undef */
let base_url = ''

// console.log(process.env.VUE_APP_ENV)
if (process.env.VUE_APP_ENV === 'development') {
  // 开发环境
  base_url = './hutunote'
  // base_url = 'http://localhost:8088/hutunote'
} else if (process.env.VUE_APP_ENV === 'test') {
  // 测试环境
  base_url = api_url.test
} else if (process.env.VUE_APP_ENV === 'stage') {
  // 预发布环境
  base_url = api_url.stage
} else {
  // 生产环境
  base_url = api_url.product
}

export { base_url }
