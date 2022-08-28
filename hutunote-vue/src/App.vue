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
<style>
</style>
