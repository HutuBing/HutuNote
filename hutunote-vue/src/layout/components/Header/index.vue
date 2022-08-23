<template>
  <div class="header-box">
    <div class="header">
      <img src="~@/assets/images/common/logo.png" alt="" class="logo" />
      <ul class="list">
        <li class="menu">
          <el-popover
            placement="bottom"
            width="150"
            trigger="click"
            popper-class="header-down-menu"
          >
            <ul class="down-menu">
              <li class="item">
                <svg-icon icon-name="icon-gerenziliao" />个人资料
              </li>
              <li class="item">
                <svg-icon icon-name="icon-tuichudenglu" />退出登录
              </li>
            </ul>
            <span slot="reference">
              <img
                src="~@/assets/images/common/icon-user.png"
                alt=""
                class="icon-user"
              />超级管理员
            </span>
          </el-popover>
        </li>
        <!-- <li class="edit" @click="$router.push({ path: '/home' })">
          <svg-icon icon-name="icon-xiugai" />
          <span>修改密码</span>
        </li> -->
      </ul>
    </div>
    <div class="header-nav">
      <ul class="nav-ul">
        <li
          :class="[currentNav == item.nav ? 'active' : '']"
          v-for="item in navList"
          :key="item.name"
        >
          <el-dropdown placement="top">
            <!-- trigger="click"  -->
            <span
              class="nav-menu el-dropdown-link"
              v-if="!item.children || item.children.length < 1"
            >
              <router-link :to="{ name: item.name }">
                {{ item.title }}
              </router-link>
            </span>
            <span class="nav-menu el-dropdown-link" v-else>
              {{ item.title }}
            </span>
            <!-- 解决隐藏el-dropdown-menu报错 -->
            <el-dropdown-menu style="opacity: 0">
              <el-dropdown-item></el-dropdown-item>
            </el-dropdown-menu>
            <el-dropdown-menu
              class="nav-menu"
              slot="dropdown"
              v-if="item.children && item.children.length > 0"
            >
              <el-dropdown-item
                v-for="citem in item.children"
                :key="citem.name"
                :class="[currentName == citem.name ? 'active' : '']"
              >
                <router-link :to="{ name: citem.name }">{{
                  citem.title
                }}</router-link>
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </li>
      </ul>
    </div>
  </div>
</template>
<script>
export default {
  components: {},
  data() {
    return {
      navList: [],
      msgList: []
    }
  },
  computed: {
    currentNav() {
      return `${this.$route.meta.nav}`;
    },
    currentName() {
      return `${this.$route.name}`;
    },
  },
  mounted() {},
  methods: {},
};
</script>
