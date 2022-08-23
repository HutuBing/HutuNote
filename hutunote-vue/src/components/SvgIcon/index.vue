<!--
    图标iconfont
    @params: iconName 图标名字
-->
<template>
  <svg class="svg-icon" aria-hidden="true" v-on="$listeners">
    <use :xlink:href="svgName" />
  </svg>
</template>

<script>
export default {
  name: 'SvgIcon',
  props: {
    iconName: {
      type: String,
      required: true
    }
  },
  computed: {
    svgName() {
      return `#${this.iconName}`
    }
  }
}
</script>

<style scoped>
.svg-icon {
  width: 1em;
  height: 1em;
  vertical-align: -0.15em;
  fill: currentColor;
  overflow: hidden;
}
</style>
