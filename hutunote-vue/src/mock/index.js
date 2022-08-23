const Mock = require('mockjs')
import mockdata from './data.js'
// 格式： Mock.mock( url, post/get , 返回的数据)；
// 延时400s请求到数据
Mock.setup({
  timeout: 400
})
const base_url = '/api'


// 根据nodeId查询模型
Mock.mock(`${base_url}/model/getByNodeId?nodeId=110`, 'get', function(option) {
  return mockdata.getByNodeIdData
})
//执行
Mock.mock(`${base_url}/modell/execute?modelId=44`, 'get', function(option) {
  return mockdata.modelExecute
})
//获取执行状态
Mock.mock(`${base_url}/modell/exec/status`, 'post', function(option) {
  return mockdata.modelExecuteStatus
})


