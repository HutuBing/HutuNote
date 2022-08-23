/*
	模型配置
*/
const modalConfig = {
  namespaced: true,
  state: {
    showConfigPanel: true, // 是否显示配置面板
    curTabsValue: '1', // 当前高亮tabs的name
    curTabsIndex: 0, // 当前高亮tabs的index
    configTabsData: [], // 模型配置所有tab节点流程数组
    curNodeSelected: {
      componentAlias: '',
      modelDetail: ''
    } // 当前选择的节点对象
  },
  getters: {
  },
  mutations: {
    setShowConfigPanel: (state, val) => {
      state.showConfigPanel = val
    },
    setCurTabsValue: (state, val) => {
      state.curTabsValue = val
    },
    setCurTabsIndex: (state, val) => {
      state.curTabsIndex = val
    },
    setConfigTabsData: (state, val) => {
      state.configTabsData = val
    },
    setCurNodeSelected: (state, val) => {
      state.curNodeSelected = val
    }
  },
  actions: {

  }
}
export default modalConfig
