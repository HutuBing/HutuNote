<template>
    <div>
        <el-table class="bueatyScroll" :data="tableData" :height="tableHeight" style="padding-left: 10px;">
            <el-table-column prop="name">
                <template slot-scope="scope">
                    <div style="padding: 5px; border-radius: 15px;">
                        <div style="font-size: 16px;">
                            <i class="el-icon-document" style="color: deepskyblue;"></i>
                            <span style="margin-left: 10px;">{{scope.row.name}}</span>
                        </div>
                        <div style="font-size: 12px; margin-top: 10px; margin-left: 25px;">
                            <span style="color: gray">{{ scope.row.createDate }}</span>
                            <span style="color: gray; margin-left: 20px;">{{ scope.row.fileSize }}</span>
                        </div>
                    </div>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
    export default {
        name: "note-list",
        data() {
            return {
                tableHeight: "800px",
                tableData: [
                    {name: "动态代理.md", createDate: "2022-08-01", fileSize: "10kb"},
                    {name: "测试文件.txt", createDate: "2022-08-01", fileSize: "10kb"},
                    {name: "测试文档.docx", createDate: "2022-08-01", fileSize: "10kb"},
                    {name: "动态代理.md", createDate: "2022-08-01", fileSize: "10kb"},
                    {name: "测试文件.txt", createDate: "2022-08-01", fileSize: "10kb"},
                    {name: "测试文档.docx", createDate: "2022-08-01", fileSize: "10kb"},
                    {name: "动态代理.md", createDate: "2022-08-01", fileSize: "10kb"},
                    {name: "测试文件.txt", createDate: "2022-08-01", fileSize: "10kb"},
                    {name: "测试文档.docx", createDate: "2022-08-01", fileSize: "10kb"},
                    {name: "动态代理.md", createDate: "2022-08-01", fileSize: "10kb"},
                    {name: "测试文件.txt", createDate: "2022-08-01", fileSize: "10kb"},
                    {name: "测试文档.docx", createDate: "2022-08-01", fileSize: "10kb"},
                ]
            }
        },
        methods: {
            fetchTaskList() {
                this.loadViewData({
                    modulesName: "businessDispatch",
                    url: "taskConfigsList",
                    params: {
                        // typeId: this.searchForm.typeId,
                    },
                    success: (res) => {
                        this.taskList = res.data || [];
                    },
                });
            },
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
</style>