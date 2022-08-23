<!-- 侧边导航 -->
<template>
  <div class="left-menu">
    <ul class="menu-list">
      <li class="item" style="padding: 30px 0px;"><el-avatar :size="48" src="https://t7.baidu.com/it/u=848096684,3883475370&fm=193&f=GIF"></el-avatar></li>
      <li class="item"><el-button circle size="medium" type="primary" icon="el-icon-plus" /></li>
      <li class="item"><el-button circle size="medium" icon="el-icon-menu" /></li>
    </ul>
  </div>
</template>
<script>
import { mixinComm } from '@/utils/mixin'
import { mapState, mapMutations } from 'vuex'
export default {
  components: {
  },
  mixins: [mixinComm],
  data() {
    return {
      curMenuId: '1',
      menuList: [
        {
          menuId: '1',
          menuName: '个人工作台',
          path: '/home',
          icon: 'el-icon-add'
        }
      ]
    }
  },
  computed: {
    ...mapState('commonHttp', {
      shrinkSidebar: 'shrinkSidebar'
    })
  },
  watch: {

  },
  created() {

  },
  mounted() {
    this.setShrinkSidebar(true)
  },
  methods: {
    ...mapMutations('commonHttp', {
      setShrinkSidebar: 'setShrinkSidebar',
      setCurMenu: 'setCurMenu'
    }),
    handleToggle() {
      this.setShrinkSidebar(!this.shrinkSidebar)
    },
    handleNodeClick(item) {
      // 菜单节点点击
      this.curMenuId = item.menuId
      if (!item.path) return false
      this.reloadRouter()
      this.$router.push({ path: item.path })
    }
  }
}
</script>

