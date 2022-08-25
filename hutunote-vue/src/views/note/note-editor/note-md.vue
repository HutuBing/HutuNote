<template>
    <mavon-editor v-model="fileText"
                  :subfield="false"
                  :editable="true"
                  :defaultOpen="'preview'"
                  :style="{height: editorHeight}" />
</template>

<script>
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
            }
        },
        created() {
            this.initEditorHeight()
            this.fileText = this.value
        },
        methods: {
            initEditorHeight() {
                this.editorHeight = document.documentElement.clientHeight - this.headHeight + "px"
            }
        },
        watch: {
            fileText(oldVal, newVal){
                this.$emit('input', newVal)
            }
        }
    }
</script>

<style scoped>

</style>