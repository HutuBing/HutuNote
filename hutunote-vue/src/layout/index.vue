<template>
  <div class="doc new-doc auto-dark">
    <v-sidebar class="hidden-sm-and-down"/>
    <div class="wrap" :class="isSmallScreen ? 'small-screen':'left'">
      <router-view v-if="isRouterAlive" />
    </div>
    <v-footer v-if="isSmallScreen"/>
  </div>
</template>
<script>
import vHearder from './components/Header/index'
import vSidebar from './components/Sidebar/index'
import vFooter from './components/Footer/index'
import { mapState } from 'vuex'
export default {
  components: {
      vHearder,
      vSidebar,
      vFooter
  },
  data() {
      return {

      }
  },
  computed: {
      ...mapState('common', {
          isRouterAlive: 'isRouterAlive',
          isSmallScreen: 'isSmallScreen'
      })
  },
  watch: {},
  created() {},
  methods: {}
}
</script>
<style lang="scss">
@import "~@/assets/style/index.scss";
</style>
