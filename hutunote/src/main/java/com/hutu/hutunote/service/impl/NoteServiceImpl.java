package com.hutu.hutunote.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.util.StringUtil;
import com.hutu.hutunote.mapper.NoteMapper;
import com.hutu.hutunote.model.entity.Note;
import com.hutu.hutunote.model.params.QueryNoteParams;
import com.hutu.hutunote.model.params.SaveNoteParams;
import com.hutu.hutunote.model.params.UpdateNoteParams;
import com.hutu.hutunote.model.vo.NoteInfoVO;
import com.hutu.hutunote.service.INoteService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public String save(SaveNoteParams params) {
        Note note = new Note();
        BeanUtils.copyProperties(params, note);
        //todo 不同类型做不同操作，需要和文件服务器交互
        this.save(note);
        return note.getId();
    }

    @Override
    public Boolean updateById(String id, UpdateNoteParams params) {
        Note note = new Note();
        note.setId(id);
        note.setName(StringUtil.isEmpty(params.getName()) ? null : params.getName());
        note.setCataId(StringUtil.isEmpty(params.getCataId()) ? null : params.getCataId());
        note.setFileText(StringUtil.isEmpty(params.getFileText()) ? null : params.getFileText());
        note.setFileSize(StringUtil.isEmpty(params.getFileText()) ? "0" : params.getFileText().getBytes().length + "");
        //todo 不同类型做不同操作，需要和文件服务器交互
        return this.updateById(note);
    }

    @Override
    public List<NoteInfoVO> listNoteInfo(QueryNoteParams params) {
        List<NoteInfoVO> list = baseMapper.listNoteInfo(params);
        list.forEach(item -> {
            item.setFileSize(formatSize(item.getFileSize()));
        });
        return list;
    }

    private String formatSize(String size){
        if(StringUtil.isEmpty(size)) return size;
        long l = Long.parseLong(size);
        if(l < 1024) return l + "B";
        if(l < 1024 * 1024) return l/1024 + "KB";
        if(l < 1024 * 1024 * 1024) return l/1024/1024 + "MB";

        return l/1024/1024/1024 + "GB";
    }
}
