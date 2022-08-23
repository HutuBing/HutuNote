/*
	存储常用请求
*/
const commonHttp = {
  namespaced: true,
  state: {
    isRouterAlive: true, // 是否重载该路由
    shrinkSidebar: false, // 收缩菜单 true收缩，false展开
    curMenu: [] // 当前菜单，包含其父级,最后一级即为当前级别
  },
  getters: {
  },
  mutations: {
    setShrinkSidebar: (state, val) => {
      state.shrinkSidebar = val
    },
    setIsRouterAlive: (state, val) => {
      state.isRouterAlive = val
    },
    setCurMenu: (state, val) => {
      state.curMenu = val
    }
  },
  actions: {

  }
}
export default commonHttp
