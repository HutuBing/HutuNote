package com.hutu.hutunote.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hutu.hutunote.model.entity.Note;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteMapper extends BaseMapper<Note> {
}
