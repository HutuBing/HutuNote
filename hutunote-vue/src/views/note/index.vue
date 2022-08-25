<template>
    <div style="background: white; height: 100%;">
        <el-row style="height: 100%; padding: 0px; margin: 0px;">
            <el-col :span="4" style="border-right: 1px solid #d3d3d3; height: 100%;">
                <note-list @item-click="handleItemClick"/>
            </el-col>
            <el-col :span="20" style="border-right: 1px solid #d3d3d3; height: 100%;">
                <note-editor v-if="curNoteId" v-model="curNoteId" />
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import noteList from './note-list'
    import noteEditor from './note-editor'
    export default {
        name: "note",
        data() {
            return{
                curNoteId: ''
            }
        },
        components: {
            noteList,
            noteEditor
        },
        methods: {
            handleItemClick(item) {
                this.curNoteId = item.id
            }
        }
    }
</script>

<style scoped>

</style>