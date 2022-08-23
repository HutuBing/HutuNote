<!--
 * @Description: 上传组件
 * @Autor: liaojb
 * @Date: 2021-11-29 10:43:41
 * @LastEditors: liaojb
 * @LastEditTime: 2022-08-11 14:22:23
-->
<template>
  <div class="upload-btn">
    <template v-if="type == 'default'">
      <div class="flex">
        <el-upload
        ref="upload"
        :action="action"
        :data="data"
        :headers="headers"
        :multiple="multiple"
        :show-file-list="false"
        :accept="$attrs.accept"
        :name="'file'"
        :auto-upload="true"
        :before-upload="beforeUpload"
        :on-success="successCallback"
        :on-remove="removeCallback"
        :on-error="errorCallback">
          <slot></slot>
        </el-upload>
      </div>
    </template>
  </div>
</template>

<script>
// 根据文件获取文件类型.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png
function getFileType(fileName) {
  const exec = /\.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png$/.exec(fileName)
  if (exec) {
    return `image/${fileName.substring(exec.index + 1)}`
  } else if (fileName.endsWith('.pdf')) {
    return 'application/pdf'
  }
  return ''
}

import { base_url } from '../../utils/config'
export default {
  data() {
    return {
      // action: `${ base_url }/Index/upload_image`,
      headers: {
        Accept: 'application/json, text/javascript, */*; q=0.01',
        'X-Requested-With': 'XMLHttpRequest'
      },
      fileList: [], // 用来记录当前的文件列表
    }
  },
  computed: {
    
  },
  props: {
    action: {
      type: String,
      default: ''
    },
    data: {
      type: Object,
      default: () => {
        return {
          id: ''
        }
      }
    },
    // default是默认显示的形式,目前只有一个形式，没有图片预览之类的
    type: {
      type: String,
      default: 'default'
    },
    multiple: {
      type: Boolean,
      default: false,
    },
    showSuccessTips: {
      type: Boolean,
      default: true
    }
  },
  watch: {
    
  },
  methods: {
    successCallback(response, file, fileList) {
      // if (!this.multiple && fileList.length >= 2) {
      //   fileList.splice(0, 1)
      //   this.fileList.splice(0, 1)
      // }
      // this.fileList.push(response.path)
      if (this.showSuccessTips) {
        this.$message.success('上传成功')
      }
      this.$emit('successCallback', response)
    },
    errorCallback(err, file, fileList) {
      console.log(err)
      this.$message.error('上传失败')
    },
    removeCallback(file, fileList) {
      // 服务器文件和本地上传的文件的处理方式不一样
      const path = file.isServerFile ? file.name : file.response.path
      this.fileList.splice(this.fileList.indexOf(path), 1)
    },
    beforeUpload(file) {
      this.$emit('beforeUpload', file)
    },
  },
}
</script>

<style lang="scss" scoped>
.flex {
  display:-webkit-box;
  display:-webkit-flex;
  display:-ms-flexbox;
  display:flex;
}
.upload-btn {
  display: inline-block;
}
</style>
