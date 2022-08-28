/*
	存储常用请求
*/
const common = {
  namespaced: true,
  state: {
    isRouterAlive: true, // 是否重载该路由
    clientWidth: 0,  //客户端宽度
    clientHeight: 0,  //客户端高度
    isSmallScreen: false,  //是否小屏
  },
  getters: {
  },
  mutations: {
    setClientWidth: (state, val) => {
      state.clientWidth = val
    },
    setClientHeight: (state, val) => {
      state.clientHeight = val
    },
    setIsSmallScreen: (state, val) => {
      state.isSmallScreen = val
    },
    setIsRouterAlive: (state, val) => {
      state.isRouterAlive = val
    }
  },
  actions: {

  }
}
export default common
