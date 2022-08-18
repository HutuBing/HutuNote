package com.hutu.hutunote.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hutu.hutunote.model.entity.Note;
import com.hutu.hutunote.model.params.SaveNoteParams;
import com.hutu.hutunote.model.params.UpdateNoteParams;

public interface INoteService extends IService<Note> {

    Note getById(String id);

    Boolean removeById(String id);

    Boolean save(SaveNoteParams params);

    Boolean updateById(String id, UpdateNoteParams params);
}
