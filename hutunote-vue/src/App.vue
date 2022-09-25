<template>
    <div id="app">
        <router-view/>
    </div>
</template>

<script>
    import { mapMutations } from 'vuex'

    export default {
        name: 'App',
        data() {
            return {}
        },
        created() {
            this.initState()

            window.onresize = () => {
                this.initState()
            };

        },
        mounted() {
            window.document.documentElement.setAttribute( "data-theme", 'dark');
        },
        updated() {
        },
        methods: {
            ...mapMutations('common', {
                setClientWidth: 'setClientWidth',
                setClientHeight: 'setClientHeight',
                setIsSmallScreen: 'setIsSmallScreen',
            }),

            initState(){
                this.setClientWidth(document.documentElement.clientWidth)
                this.setClientHeight(document.documentElement.clientHeight)
                this.setIsSmallScreen(document.documentElement.clientWidth < 992)
            }
        }
    }
</script>

<style lang="scss">
    @import '@/assets/scss/common/common';

    #app {
        //在此使用了背景颜色变量
        @include background_color("background_color");
        //再次使用了文字颜色变量
        @include font_color("text-color");
    }

    .auto-dark {
        //在此使用了背景颜色变量
        @include background_color("background_color");
        //再次使用了文字颜色变量
        @include font_color("text-color");
    }
</style>