<!--
 * @Description: 底部
 * @Autor: liaojb
 * @Date: 2021-11-26 09:02:55
 * @LastEditors: liaojb
 * @LastEditTime: 2021-11-26 09:26:53
-->
<template>
  <div class="footer">
    <el-menu
            mode="horizontal"
            default-active="home"
            @select="handleSelect"
            @open="handleOpen"
            @close="handleClose"
            active-text-color="#ffd04b"
            :router="true">
      <el-row>
        <el-col :span="6">
          <el-menu-item index="home">
            <i class="el-icon-s-home"></i>
          </el-menu-item>
        </el-col>
        <el-col :span="6">
          <el-menu-item index="note-editor?noteId=11">
            <i class="el-icon-menu"></i>
          </el-menu-item>
        </el-col>
        <el-col :span="6">
          <el-menu-item index="schedule">
            <i class="el-icon-alarm-clock"></i>
          </el-menu-item>
        </el-col>
        <el-col :span="6">
          <el-menu-item index="setting">
            <i class="el-icon-setting"></i>
          </el-menu-item>
        </el-col>
      </el-row>
    </el-menu>
  </div>
</template>
<script>
export default {
  components: {},
  data() {
    return {
      
    }
  },
  computed: {

  },
  mounted() {

  },
  methods: {
      handleSelect(key, keyPath){
          console.log(key, keyPath)
      },
      handleOpen(key, keyPath){
          console.log(key, keyPath)
      },
      handleClose(key, keyPath) {
          console.log(key, keyPath)
      }
  }
}
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
.footer {
  height: 56px;
  line-height: 56px;
  text-align: center;
  font-size: 12px;
  color: #666666;
  background-color: #E9ECF3;
}
</style>
