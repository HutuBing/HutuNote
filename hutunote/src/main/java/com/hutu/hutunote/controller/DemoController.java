package com.hutu.hutunote.controller;

import com.hutu.hutunote.common.Result;
import com.hutu.hutunote.model.entity.Demo;
import com.hutu.hutunote.service.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private IDemoService demoService;

    @RequestMapping("test")
    public Result<List<Demo>> test(){
        return Result.success(demoService.list());
    }
}
