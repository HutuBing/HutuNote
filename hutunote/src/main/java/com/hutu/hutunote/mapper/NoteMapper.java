package com.hutu.hutunote.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hutu.hutunote.model.entity.Note;
import com.hutu.hutunote.model.params.QueryNoteParams;
import com.hutu.hutunote.model.vo.NoteInfoVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteMapper extends BaseMapper<Note> {

    public List<NoteInfoVO> listNoteInfo(QueryNoteParams params);

}
