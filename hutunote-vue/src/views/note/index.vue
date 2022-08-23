<template>
    <div style="background: white; height: 100%;">
        <el-row style="height: 100%; padding: 0px; margin: 0px;">
            <el-col :span="4" style="border-right: 1px solid #d3d3d3; height: 100%;">
                <note-list @item-click="handleItemClick"/>
            </el-col>
            <el-col :span="20">

            </el-col>
        </el-row>
    </div>
</template>

<script>
    import noteList from './note-list'
    export default {
        name: "note",
        components: {
            noteList
        },
        methods: {
            handleItemClick(item) {
                console.log(item)
            }
        }
    }
</script>

<style scoped>

</style>