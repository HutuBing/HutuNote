<!-- 去掉侧边 -->
<template>
  <div class="doc">
    <v-hearder />
    <router-view v-if="isRouterAlive" />
  </div>
</template>
<script>
import vHearder from './components/Header/index'
import { mapState } from 'vuex'
export default {
  components: {
    vHearder
  },
  data() {
    return {}
  },
  computed: {
      ...mapState('common', {
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
