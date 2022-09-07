import { mapState, mapMutations } from 'vuex'
export const mixinComm = { // 公共mixin
  data() {
    return {
      existWarning: null
    }
  },
  created() {
  },
  computed: {
    ...mapState('common', {
      curMenu: 'curMenu'
    })
  },
  methods: {
    ...mapMutations('common', {
      setIsRouterAlive: 'setIsRouterAlive'
    }),
    reloadRouter() {
      // 重载路由
      this.setIsRouterAlive(false)
      this.$nextTick(function() {
        this.setIsRouterAlive(true)
      })
    },
    warningMessageFn(type, message) {
      // 公共提示
      if (!this.existWarning) {
        this.existWarning = this.$message({
          type: type,
          message: message,
          showClose: true,
          onClose: instance => {
            this.existWarning = null
          }
        })
      }
    },
    resetForm(form) {
      this.$refs[form] && this.$refs[form].resetFields()
    },
    loadViewData({ params, modulesName, url, loading, success, fail, error }) { // success: 成功回调函数，fail:失败回调，error:error回调,params: 请求参数, url: 请求url，loading: loading变量,modulesName: 请求url所属模块
      // 统一调用接口
      this[loading] = true
      this.api[modulesName][url](params)
        .then(response => {
          const result = response.data.code
          this[loading] = false
          if (result === '200' || result === 200 || result === 0 || result === '0') {
            success && success(response.data)
          } else {
            if (fail) {
              fail(response.data)
            } else {
              this.warningMessageFn('error', response.data.msg || response.data.message)
            }
          }
        })
        .catch(er => {
          this[loading] = false
          console.log(er)
          if (error) {
            error(er)
          } else {
            this.warningMessageFn('error', JSON.stringify(er))
          }
        })
    },
    uploadFile({ file, success, fail, error }) {
      let formData = new FormData()
      formData.append('file', file)
      this.loadViewData({
        params: formData,
        modulesName: 'files',
        url: 'upload',
        success: success,
        fail: fail,
        error: error
      })
    }
  }
}
