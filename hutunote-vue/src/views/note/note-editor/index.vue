<template>
    <div>
        <el-row :style="{'line-height': headHeight+'px'}">
            <span style="margin-left: 10px; font-size: 16px; width: calc(100% - 100px); float: left;">{{note.name}}</span>
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
        </el-row>
        <!--<div v-if="showEditor">-->
            <note-md v-if="showEditor" v-model="note.fileText" :head-height="headHeight" :editable="editable"/>
        <!--</div>-->
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
                showEditor: false
            }
        },
        watch: {
            value(oldVal, newVal){
                this.getFileText()
            }
        },
        methods: {
            getFileText() {
                this.showEditor = false
                this.loadViewData({
                    modulesName: "note",
                    url: "getById",
                    params: this.value,
                    success: (res) => {
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
                        id: this.value
                    },
                    success: (res) => {

                    },
                })
            }
        }
    }
</script>

<style scoped>

</style>