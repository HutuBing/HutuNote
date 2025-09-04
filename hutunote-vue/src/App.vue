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
            setInterval(() => {
                let date = new Date()
                if (date.getHours() >= 7 && date.getHours() <= 21) {
                    window.document.documentElement.setAttribute( "data-theme", 'light')
                    this.setIsDark(false)
                }else {
                    window.document.documentElement.setAttribute( "data-theme", 'dark')
                    this.setIsDark(true)
                }
            },100000)
        },
        updated() {
        },
        methods: {
            ...mapMutations('common', {
                setClientWidth: 'setClientWidth',
                setClientHeight: 'setClientHeight',
                setIsSmallScreen: 'setIsSmallScreen',
                setIsDark: 'setIsDark',
            }),

            initState(){
                this.setClientWidth(document.documentElement.clientWidth)
                this.setClientHeight(document.documentElement.clientHeight)
                this.setIsSmallScreen(document.documentElement.clientWidth < 992)
                let date = new Date()
                if (date.getHours() >= 7 && date.getHours() <= 21) {
                    window.document.documentElement.setAttribute( "data-theme", 'light')
                    this.setIsDark(false)
                }else {
                    window.document.documentElement.setAttribute( "data-theme", 'dark')
                    this.setIsDark(true)
                }
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