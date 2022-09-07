<template>
    <div v-loading="loading">
        <el-row :style="{'line-height': headHeight+'px'}">
            <div style="margin-left: 10px; font-size: 16px; width: calc(100% - 150px); float: left;">
            <el-input v-if="editable" v-model="note.name" style="width: 150px; border-radius: 5px;"/>
            <span v-else>{{note.name}}</span>
            </div>
            <el-button v-if="editable"
                       type="plain"
                       round
                       size="small"
                       @click="handleSave">保存</el-button>
            <el-button v-else
                       type="plain"
                       round
                       size="small"
                       @click="editable = !editable">编辑</el-button>

            <el-button type="danger"
                       round
                       size="small"
                       @click="handleDelete">删除</el-button>

        </el-row>
        <div v-if="showEditor">
            <note-md v-model="note.fileText" :head-height="headHeight" :editable="editable"/>
        </div>
    </div>
</template>

<script>
    import noteMd from './note-md'

    export default {
        name: "note-editor",
        components: {
            noteMd
        },
        props: {
            value: {
                type: String
            }
        },
        data() {
            return {
                note: {
                    name: "",
                    fileText: ''
                },
                headHeight: 65,
                editable: false,
                showEditor: false,
                loading: false
            }
        },
        mounted(){
            if(this.$route.query && this.$route.query.noteId){
                this.note.id = this.$route.query.noteId
            }
            this.getFileText()
        },
        watch: {
            value(oldVal, newVal){
                if(this.editable){
                    this.confirmSave()
                }else {
                    this.getFileText()
                }
            }
        },
        methods: {
            getFileText() {
                this.showEditor = false
                this.loadViewData({
                    modulesName: "note",
                    url: "getById",
                    params: this.value || this.note.id,
                    loading: 'loading',
                    success: (res) => {
                        this.note.id = res.data.id || this.value || this.note.id
                        this.note.name = res.data.name || ''
                        this.note.fileText = res.data.fileText || ''
                        this.showEditor = true
                    },
                })
            },
            handleSave() {
                this.loadViewData({
                    modulesName: "note",
                    url: "updateById",
                    params: {
                        name: this.note.name,
                        fileText: this.note.fileText,
                        id: this.note.id
                    },
                    success: (res) => {
                        this.editable = false
                        this.$emit("save-success", this.note)
                    },
                })
            },
            confirmSave() {
                this.$confirm("是否保存修改?", "提示", {
                    confirmButtonText: "是",
                    cancelButtonText: "否",
                    type: "warning",
                }).then(() => {
                    this.handleSave()
                }).catch(() => {})
            },
            handleDelete() {
                this.$confirm("是否确认删除?", "提示", {
                    confirmButtonText: "是",
                    cancelButtonText: "否",
                    type: "warning",
                }).then(() => {
                    this.doDelete()
                }).catch(() => {})
            },
            doDelete() {
                this.loadViewData({
                    modulesName: "note",
                    url: "deleteById",
                    params: {
                        id: this.note.id
                    },
                    success: (res) => {
                        this.editable = false
                        this.$emit("delete-success", this.note)
                    },
                })
            }
        }
    }
</script>

<style scoped>

</style>