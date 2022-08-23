<template>
  <div class="doc new-doc">
    <v-sidebar />
    <div class="wrap">
      <router-view v-if="isRouterAlive" />
    </div>
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
    return {}
  },
  computed: {
    ...mapState('commonHttp', {
      shrinkSidebar: 'shrinkSidebar',
      isRouterAlive: 'isRouterAlive'
    })
  },
  watch: {},
  created() {

  },
  methods: {}
}
</script>
<style lang="scss">
@import "~@/assets/style/index.scss";
</style>
