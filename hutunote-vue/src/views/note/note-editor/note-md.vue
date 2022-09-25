<template>
    <mavon-editor ref="editor"
                  class="auto-dark"
                  v-model="fileText"
                  :subfield="editable"
                  :editable="true"
                  :defaultOpen="'preview'"
                  :toolbarsFlag="editable"
                  previewBackground="#2D2D2D"
                  toolbarsBackground="#2D2D2D"
                  :style="{height: editorHeight}"
                  @imgAdd="handleImgAdd" />
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
            },
            handleImgAdd(pos, file) {
                this.uploadFile({
                    file: file,
                    success: (res) => {
                        this.$refs['editor'].$img2Url(pos, res.data || '')
                    },
                })
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