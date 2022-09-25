<!-- 侧边导航 -->
<template>
    <div class="left-menu auto-dark">
        <ul class="menu-list">
            <li class="item" style="padding: 30px 0px;">
                <el-avatar :size="48" src="https://t7.baidu.com/it/u=848096684,3883475370&fm=193&f=GIF"></el-avatar>
            </li>
            <li class="item">
                <el-tooltip content="新建笔记" placement="right" effect="light">
                    <el-button circle size="medium" type="primary" icon="el-icon-plus" @click="showAddDialog = true"/>
                </el-tooltip>
            </li>
            <li class="item">
                <el-tooltip content="首页" placement="right" effect="light">
                    <el-button circle size="medium" icon="el-icon-s-home" @click="$router.push({path: 'home'})" />
                </el-tooltip>
            </li>
            <li class="item">
                <el-tooltip content="计划" placement="right" effect="light">
                    <el-button circle size="medium" icon="el-icon-alarm-clock" @click="$router.push({path: 'note-editor', query: {noteId: '11'}})"/>
                </el-tooltip>
            </li>
            <li class="item">
                <el-tooltip content="设置" placement="right" effect="light">
                    <el-button circle size="medium" icon="el-icon-setting"/>
                </el-tooltip>
            </li>
        </ul>

        <el-dialog
                title="新建笔记"
                :append-to-body="true"
                :visible.sync="showAddDialog"
                width="400px"
                custom-class="nor-dialog1">
            <div class="dialog-inner">
                <el-form ref="form" :model="form" :rules="rules" class="p-l-10 p-r-10" label-width="50px">
                    <el-form-item label="名称" prop="name">
                        <el-input v-model="form.name"
                                  placeholder="请输入笔记名称/标题"
                                  :clearable="true"/>
                    </el-form-item>
                </el-form>
                <div class="dialog-bottom-btn" style="border-top: 1px solid #e4e7ee">
                    <el-button type="primary" @click="handleSave">保存</el-button>
                    <el-button type="plain" @click="showAddDialog = false">取消</el-button>
                </div>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import {mixinComm} from '@/utils/mixin'
    import {mapMutations} from 'vuex'

    export default {
        components: {},
        mixins: [mixinComm],
        data() {
            return {
                curMenuId: '1',
                form: {
                    name: '',
                    noteType: 'md',
                    cataId: ''
                },
                showAddDialog: false,
                rules: {
                    name: {required: true, message: '请输入', trigger: 'blur'},
                },
            }
        },
        computed: {},
        watch: {},
        created() {

        },
        mounted() {
        },
        methods: {
            ...mapMutations('note', {
                setCurNoteId: 'setCurNoteId'
            }),
            handleSave() {
                this.$refs["form"].validate((valid) => {
                    if (valid) {
                        this.loadViewData({
                            modulesName: "note",
                            url: "save",
                            params: this.form,
                            success: (res) => {
                                this.showAddDialog = false
                                this.setCurNoteId(res.data)
                            },
                        })
                    }
                })
            }
        }
    }
</script>

