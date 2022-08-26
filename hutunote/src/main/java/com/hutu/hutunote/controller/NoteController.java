package com.hutu.hutunote.controller;

import com.hutu.hutunote.common.Result;
import com.hutu.hutunote.model.entity.Note;
import com.hutu.hutunote.model.params.QueryNoteParams;
import com.hutu.hutunote.model.params.SaveNoteParams;
import com.hutu.hutunote.model.params.UpdateNoteParams;
import com.hutu.hutunote.model.vo.NoteInfoVO;
import com.hutu.hutunote.service.INoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "笔记接口")
@RestController
@RequestMapping("/note")
public class NoteController extends BaseController{

    @Autowired
    private INoteService noteService;

    @ApiOperation(value = "查询", httpMethod = "GET")
    @GetMapping("/list")
    public Result<List<NoteInfoVO>> list(QueryNoteParams params){
        List<NoteInfoVO> list = noteService.listNoteInfo(params);
        return Result.OK(list);
    }

    @ApiOperation(value = "根据id查", httpMethod = "GET")
    @GetMapping("/{id}")
    public Result<Note> getById(@PathVariable String id){
        return Result.OK(noteService.getById(id));
    }

    @ApiOperation(value = "根据id删除", httpMethod = "DELETE")
    @DeleteMapping("/{id}")
    public Result<Boolean> removeById(@PathVariable String id){
        return Result.OK(noteService.removeById(id));
    }

    @ApiOperation(value = "新增", httpMethod = "POST")
    @PostMapping
    public Result<String> save(@RequestBody SaveNoteParams params){
        return Result.OK(noteService.save(params));
    }

    @ApiOperation(value = "修改", httpMethod = "PUT")
    @PutMapping("/{id}")
    public Result<Boolean> update(@PathVariable String id, @RequestBody UpdateNoteParams params){
        return Result.OK(noteService.updateById(id, params));
    }
    
}
