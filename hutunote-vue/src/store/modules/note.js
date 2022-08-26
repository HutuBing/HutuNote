/*
	笔记相关请求
*/
const note = {
    namespaced: true,
    state: {
        curNoteId: ''  //当前要展示的笔记id
    },
    getters: {
    },
    mutations: {
        setCurNoteId: (state, val) => {
            state.curNoteId = val
        }
    },
    actions: {

    }
}
export default note
