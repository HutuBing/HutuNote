<template>
    <div style="height: 100%;" class="auto-dark">
        <el-row style="height: 100%; padding: 0px; margin: 0px;">
            <el-col :xs="24"
                    :sm="24"
                    :md="6"
                    :lg="5"
                    :xl="4"
                    style="border-right: 1px solid #d3d3d3; height: 100%;">
                <note-list ref="noteList"
                           @item-click="handleItemClick" />
            </el-col>
            <el-col v-if="!isSmallScreen"
                    :xs="1"
                    :sm="1"
                    :md="18"
                    :lg="19"
                    :xl="20"
                    style="height: 100%;">
                <note-editor v-if="curNoteId"
                             v-model="curNoteId"
                             @refresh="refreshNoteList"
                             @save-success="handleSaveSuccess"
                             @delete-success="handleDeleteSuccess" />
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import { mapState } from 'vuex'
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
                if(document.documentElement.clientWidth < 992){
                    this.$router.push({
                        path: 'note-editor',
                        query: {
                            noteId: item.id
                        }
                    })
                }else {
                    this.curNoteId = item.id
                }
            },
            refreshNoteList() {
                this.$refs["noteList"].getTableData()
            },
            handleSaveSuccess(item) {
                this.$refs["noteList"].getTableData()
            },
            handleDeleteSuccess() {
                this.$refs["noteList"].getTableData()
                this.curNoteId = ""
            }
        },
        computed: {
            ...mapState('common', {
                isSmallScreen: 'isSmallScreen'
            })
        }
    }
</script>

