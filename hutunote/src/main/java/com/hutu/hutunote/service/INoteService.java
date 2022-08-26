package com.hutu.hutunote.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hutu.hutunote.model.entity.Note;
import com.hutu.hutunote.model.params.QueryNoteParams;
import com.hutu.hutunote.model.params.SaveNoteParams;
import com.hutu.hutunote.model.params.UpdateNoteParams;
import com.hutu.hutunote.model.vo.NoteInfoVO;

import java.util.List;

public interface INoteService extends IService<Note> {

    Note getById(String id);

    Boolean removeById(String id);

    String save(SaveNoteParams params);

    Boolean updateById(String id, UpdateNoteParams params);

    List<NoteInfoVO> listNoteInfo(QueryNoteParams params);

}
