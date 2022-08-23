<!-- 新建模型 -->
<template>
  <div v-loading="loading2" class="modal-create">
    <el-form
      ref="form"
      :rules="rules1"
      :model="form"
      label-width="130px"
      class="nor-form1"
    >
      <el-form-item label="模型名称" prop="modelName">
        <el-input v-model="form.modelName" placeholder="请输入模型名称" />
      </el-form-item>
      <el-form-item v-if="!defaultModelType" label="模型类型:" prop="modelType">
        <el-select v-model="form.modelType" clearable placeholder="请选择模型类型" style="width: 300px">
          <el-option
            v-for="item in modelTypeList"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item class="tree" label="模型存放位置:" prop="nodeId">
        <div v-loading="loading1" class="nor-tree2-wrap default-scroll">
          <el-tree
            ref="tree"
            class="filter-tree"
            node-key="nodeId"
            default-expand-all
            :indent="10"
            :data="treeData"
            :props="defaultProps"
            :expand-on-click-node="false"
            :highlight-current="true"
            @node-click="handleNodeClick"
          >
            <span slot-scope="{ node, data }" class="custom-tree-node" :class="'level' + node.level">
              <span class="icon default"><svg-icon :icon-name="calComponentIcon(node, data).icon" /></span>
              <span class="label">{{ node.label }}</span>
            </span>
          </el-tree>
        </div>
      </el-form-item>
      <el-form-item label="模型缩略图:" prop="modelPic">
        <div v-show="form.modelPic" class="upload-img">
          <img :src="form.modelPic" alt="">
        </div>
        <input id="selectFile" type="file" hidden @change="fileChangeFn">
        <button type="button" class="nor-btn3 btn-blue" style="min-width: auto; margin: 2px 5px;" @click="handleUpload">上传图片</button>
        <span class="font12 redtxt">(图片大小不能大于100k)</span>
      </el-form-item>
      <el-form-item label="模型简介:" prop="modelDetail">
        <el-input v-model="form.modelDetail" type="textarea" rows="5" placeholder="适用场景/问题、模型思路、评估效果" />
      </el-form-item>
    </el-form>
    <div class="dialog-bottom-btn" style="padding-top: 0;">
      <button type="button" class="nor-btn1 btn-blue" @click="handleConfirm">确定</button>
    </div>
  </div>
</template>
<script>
export default {
  components: {
  },
  props: {
    jumpType: { // 新建成功后跳转方式，可选：_blank(新窗口)，_self(当前窗口)，none(不打开新页面)
      type: String,
      default: '_blank'
    },
    projectId: { // 项目id
      type: Number,
      default: null
    },
    defaultModelType: { // 默认模型类型，如果传了就隐藏模型类型
      type: Number,
      default: null
    }
  },
  data() {
    return {
      loading1: false,
      loading2: false,
      treeData: [],
      defaultProps: {
        label: 'name',
        value: 'nodeId'
      },
      rules1: {
        modelName: [
          { required: true, message: '请输入模型名称', trigger: 'blur' }
        ],
        nodeId: [
          { required: true, message: '请选择模型存放位置' }
        ],
        modelType: [
          { required: true, message: '请选择模型类型', trigger: 'change' }
        ]
      },
      form: {
        modelName: '',
        nodeId: '',
        modelType: null,
        filename: '',
        file: null,
        modelDetail: '',
        modelPic: ''
      },
      modelTypeList: [
        { value: 1, label: '数据流式模型' },
        { value: 2, label: 'python文件式模型' }
      ]
    }
  },
  computed: {
    calComponentIcon() {
      // 组件树icon和icon className
      return (node, data) => {
        const obj = {
          icon: '', // 不传如果是目录显示目录icon,否则显示指定icon
          className: '' // 目录不返回className，其它根据type返回
        }
        if (!data.icon) {
          if (data.folder) { // 目录
            obj.icon = node.expanded ? 'icon-wenjianjia-dakai' : 'icon-wenjianjia-moren'
          } else {
            obj.icon = data.icon ? data.icon : 'icon-wendangbeifen'
          }
        } else {
          obj.icon = data.icon
        }
        if (data.folder) { // 目录
          obj.className = 'default'
        } else {
          obj.className = 'icon-type' + (data.type || 1)
        }
        return obj
      }
    }
  },
  watch: {
    projectId: {
      handler(newVal, oldVal) {
        if (newVal !== oldVal) {
          this.treeData = []
          this.loadTreeData()
        }
      },
      immediate: false
    }
  },
  created() {
    this.loadTreeData()
  },
  mounted() {},
  methods: {
    loadTreeData() {
      // 资源目录树加载
      this.loadViewData({
        loading: 'loading1',
        modulesName: 'home',
        url: 'rscTreeGetTreeByType',
        params: {
          type: 3, // 2-数据源 3-模型
          name: '',
          status: null,
          projectId: this.projectId
        },
        success: (res) => {
          this.treeData = res.data || []
        }
      })
    },
    handleNodeClick(val) {
      // 树节点点击
      // console.log(val)
      this.form.nodeId = val.nodeId
    },
    handleReset() {
      // 重置表单
      this.form = {
        modelName: '',
        nodeId: '',
        modelType: null,
        filename: '',
        file: null,
        modelDetail: '',
        modelPic: ''
      }
      if (this.defaultModelType) {
        this.form.modelType = this.defaultModelType
      }
      if (this.$refs.tree) {
        this.resetForm('form')
        this.$refs.tree.setCurrentKey(null)
      }
    },
    handleUpload() {
      // 上传
      const btn = document.getElementById('selectFile')
      btn.value = ''
      btn.click()
    },
    fileChangeFn(e) {
      // 选择文件
      const file = e.target.files[0]
      // console.log(file)
      if (file !== undefined) {
        const isRightImg = file.size / 1024 < 100
        let url = ''
        const file_typename = file.name.substring(
          file.name.lastIndexOf('.')
        )
        if (['.png', '.jpg', '.jpeg', '.JPEG'].indexOf(file_typename) !== -1) {
          if (!isRightImg) {
            this.warningMessageFn(
              'warning',
              '图片大小不能大于100k'
            )
          } else {
            const selectFile = document.getElementById('selectFile')
            const agent = navigator.userAgent
            if (agent.indexOf('MSIE') >= 1) {
              url = selectFile.value
            } else if (agent.indexOf('Firefox') > 0) {
              url = window.URL.createObjectURL(selectFile.files.item(0))
            } else if (agent.indexOf('Chrome') > 0) {
              url = window.URL.createObjectURL(selectFile.files.item(0))
            }
            this.form.modelPic = url
            this.form.filename = file.name
            this.form.file = file
          }
        } else {
          this.warningMessageFn(
            'warning',
            '请选择正确的文件类型,目前只支持png,jpg,jpeg,JPEG后缀的文件'
          )
        }
      } else {
        this.warningMessageFn('warning', '请选择正确的文件')
      }
    },
    handleConfirm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          const formData = new FormData()
          formData.append('file', this.form.file)
          formData.append('modelName', this.form.modelName)
          formData.append('modelType', this.form.modelType)
          formData.append('nodeId', this.form.nodeId)
          formData.append('modelDetail', this.form.modelDetail)
          this.loadViewData({
            loading: 'loading2',
            modulesName: 'modalManage',
            url: 'getProjectModelInfo',
            params: formData,
            success: (res) => {
              this.warningMessageFn('success', '新建成功')
              const data = res.data || {}
              const query = {
                modelName: data.modelName || '',
                projectName: data.projectName || '',
                nodeId: data.nodeId,
                modelId: null,
                projectId: data.projectId || null,
                oprType: 'add' // 可选 add,edit,当是add以nodeId去保存，当是edit拿modelId保存
              }
              this.$emit('handleConfirm', query)
              switch (this.jumpType) {
                case '_blank': { // 新页面打开
                  const str = this.$utils.jointUrlQuery(query) // 携带参数
                  const url = window.location.href.split('#')[0].replace('index.html', '') + '#/modal-config?' + str
                  window.open(url, '_blank')
                  break
                }
                case '_self': { // 当前页打开
                  this.$router.push({
                    path: 'modal-config',
                    query: query
                  })
                  break
                }
              }
            }
          })
        } else {
          return false
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.modal-create {
  .nor-form1 {
    margin: 20px 100px 0 50px;
  }
  .tree {
    ::v-deep {
      .el-form-item__content{
        border: 1px solid #D4D7E5;
      }
      .el-form-item {
        width: 100%;
      }
      .el-form-item__content {
      display: block;
    }
    }
  }
  .nor-tree2-wrap {
    height: 350px;
    background-color: #FDFDFD;
    border-radius: 4px;
  }
}
</style>
