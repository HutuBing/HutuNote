<template>
  <div class="breadcrumb-box clearfix">
    <div class="location fl"><svg-icon style="position: relative;top: 2px; color: #AFB9CC;" icon-name="icon-weizhi" />当前位置：</div>
    <el-breadcrumb class="app-breadcrumb fl" separator=">" >
      <transition-group name="breadcrumb">
        <el-breadcrumb-item v-for="(item,index) in levelList" :key="item.path">
          <span v-if="item.redirect === 'noredirect' || index == levelList.length - 1" class="no-redirect">{{ item.meta.title }}</span>
          <a v-else @click.prevent="handleLink(item)">{{ item.meta.title }}</a>
        </el-breadcrumb-item>
      </transition-group>
    </el-breadcrumb>
    <slot></slot>
  </div>
</template>

<script>
import pathToRegexp from 'path-to-regexp'

export default {
  data() {
    return {
      levelList: null
    }
  },
  watch: {
    $route() {
      this.getBreadcrumb()
    }
  },
  created() {
    this.getBreadcrumb()
  },
  methods: {
    getBreadcrumb() {
      const matched = this.$route.matched.filter(item => item.name)
      this.levelList = matched.filter(item => item.meta && item.meta.title && item.meta.breadcrumb !== false)
    },
    pathCompile(path) {
      // To solve this problem https://github.com/PanJiaChen/vue-element-admin/issues/561
      const { params } = this.$route
      const toPath = pathToRegexp.compile(path)
      return toPath(params)
    },
    handleLink(item) {
      const { redirect, path } = item
      if (redirect) {
        this.$router.push(redirect)
        return
      }
      this.$router.push(this.pathCompile(path))
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
  .app-breadcrumb.el-breadcrumb {
    display: inline-block;
    font-size: 12px;
    line-height: 40px;
    .el-breadcrumb__inner a {
      font-weight: normal;
      color: #303057;
    }
    .no-redirect {
      color: #009EFF;
      cursor: text;
    }
  }
  .location {
    font-size: 12px;
    line-height: 40px;
    color: #303057;
  }
</style>
