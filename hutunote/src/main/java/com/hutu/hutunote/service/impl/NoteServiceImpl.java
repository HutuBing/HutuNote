package com.hutu.hutunote.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.util.StringUtil;
import com.hutu.hutunote.mapper.NoteMapper;
import com.hutu.hutunote.model.entity.Note;
import com.hutu.hutunote.model.params.SaveNoteParams;
import com.hutu.hutunote.model.params.UpdateNoteParams;
import com.hutu.hutunote.service.INoteService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note> implements INoteService {

    @Override
    public Note getById(String id) {
        Note note = baseMapper.selectById(id);
        //todo 类型不同从不同地方获取文件内容，需要和文件服务器交互
        return note;
    }

    @Override
    public Boolean removeById(String id) {
        //todo 删除文件服务器上的文件
        int count = baseMapper.deleteById(id);
        return count >= 1;
    }

    @Override
    public Boolean save(SaveNoteParams params) {
        Note note = new Note();
        BeanUtils.copyProperties(params, note);
        //todo 不同类型做不同操作，需要和文件服务器交互
        return this.save(note);
    }

    @Override
    public Boolean updateById(String id, UpdateNoteParams params) {
        Note note = new Note();
        note.setId(id);
        note.setName(StringUtil.isEmpty(params.getName()) ? null : params.getName());
        note.setCataId(StringUtil.isEmpty(params.getCataId()) ? null : params.getCataId());
        note.setFileText(StringUtil.isEmpty(params.getFileText()) ? null : params.getFileText());
        //todo 不同类型做不同操作，需要和文件服务器交互
        return this.updateById(note);
    }
}
