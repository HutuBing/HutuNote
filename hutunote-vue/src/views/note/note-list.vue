<template>
    <div>
        <div class="search-note">
            <el-input v-model="keyword"
                      placeholder="搜索笔记"
                      style="width: calc(100% - 40px); padding-right: 10px; border-radius: 10px;"
            />
            <el-button icon="el-icon-search"
                       circle
                       type="primary"
                       size="mini"
                       @click="getTableData">
            </el-button>
        </div>
        <el-table class="bueatyScroll"
                  :data="tableData"
                  :height="tableHeight"
                  highlight-current-row
                  :show-header="false"
                  style="padding-left: 10px;"
                  @row-click="handleRowClick">
            <el-table-column prop="name">
                <template slot-scope="scope">
                    <div style="padding: 5px; border-radius: 15px;">
                        <div style="font-size: 16px;">
                            <i class="el-icon-document" style="color: deepskyblue;"></i>
                            <span style="margin-left: 10px;">{{scope.row.name}}</span>
                        </div>
                        <div style="font-size: 12px; margin-top: 10px; margin-left: 25px;">
                            <span style="color: gray">{{ scope.row.planTime }}</span>
                            <span style="color: gray; margin-left: 20px;">{{ scope.row.planDesc }}</span>
<!--                            <span style="color: gray; margin-left: 20px;">{{ scope.row.fileSize }}</span>-->
                        </div>
                    </div>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
    import {mapState} from 'vuex'
    export default {
        name: "note-list",
        data() {
            return {
                tableHeight: "500px",
                tableData: [],
                keyword: ""
            }
        },
        created(){
            this.initTableHeight()
            this.getTableData()
        },
        methods: {
            initTableHeight(){
                this.tableHeight = this.clientHeight - 50 - (this.isSmallScreen ? 57 : 0) + 'px'
            },
            getTableData() {
                this.loadViewData({
                    modulesName: "note",
                    url: "list",
                    params: {
                        keyword: this.keyword
                    },
                    success: (res) => {
                        this.tableData = res.data || [];
                    },
                })
            },
            handleRowClick(row, column, event) {
                this.$emit("item-click", row)
            }
        },
        computed: {
            ...mapState('note', {
                curNoteId: 'curNoteId'
            }),
            ...mapState('common', {
                clientHeight: 'clientHeight',
                isSmallScreen: 'isSmallScreen'
            })
        },
        watch: {
            curNoteId(oldVal, newVal) {
                this.getTableData()
            }
        }
    }
</script>

<style>
    /*
    *改变浏览器默认的滚动条样式
    */
    .bueatyScroll .el-table__body-wrapper::-webkit-scrollbar-track-piece {
        background-color:#f8f8f8;
    }
    .bueatyScroll .el-table__body-wrapper::-webkit-scrollbar {
        width:6px;
    }
    .bueatyScroll .el-table__body-wrapper::-webkit-scrollbar-thumb {
        background-color:#dddddd;
        border-radius: 100px;
    }
    .bueatyScroll .el-table__body-wrapper::-webkit-scrollbar-thumb:hover {
        background-color:#bbb;
    }
    .search-note {
        padding: 10px 10px 10px 20px;
    }
    .search-note .el-input__inner {
        border-radius: 15px;
    }
</style>