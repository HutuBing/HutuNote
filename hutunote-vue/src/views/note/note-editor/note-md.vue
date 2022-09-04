<template>
    <mavon-editor v-model="fileText"
                  :subfield="editable"
                  :editable="true"
                  :defaultOpen="'preview'"
                  :toolbarsFlag="editable"
                  :style="{height: editorHeight}" />
</template>

<script>
    import { mapState } from 'vuex'
    export default {
        name: "note-md",
        data() {
            return {
                editorHeight: "500px",
                fileText: ""
            }
        },
        props: {
            headHeight: {
                type: Number
            },
            value: {
                type: String
            },
            editable: {
                type: Boolean,
                default: false
            }
        },
        created() {
            this.initEditorHeight()
            this.fileText = this.value
        },
        methods: {
            initEditorHeight() {
                this.editorHeight = this.clientHeight - this.headHeight - (this.isSmallScreen ? 57 : 0) + "px"
            }
        },
        watch: {
            fileText(oldVal, newVal){
                this.$emit('input', this.fileText)
            }
        },
        computed: {
            ...mapState('common', {
                clientHeight: 'clientHeight',
                isSmallScreen: 'isSmallScreen'
            })
        }
    }
</script>

<style scoped>

</style>