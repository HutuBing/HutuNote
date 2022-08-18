package com.hutu.hutunote.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.util.StringUtil;
import com.hutu.hutunote.common.PageResult;
import com.hutu.hutunote.common.Result;
import com.hutu.hutunote.model.entity.Demo;
import com.hutu.hutunote.model.params.DemoParams;
import com.hutu.hutunote.service.IDemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "DEMO接口")
@RestController
@RequestMapping("/demo")
public class DemoController extends BaseController{

    @Autowired
    private IDemoService demoService;

    @ApiOperation(value = "分页查询", httpMethod = "GET")
    @GetMapping("/page")
    public PageResult<Demo> test(DemoParams params){
        startPage(params);
        List<Demo> list = demoService.list(
                new LambdaQueryWrapper<Demo>()
                        .eq(StringUtil.isNotEmpty(params.getId()), Demo::getId, params.getId())
        );

        return PageResult.OK(list);
    }

    @ApiOperation(value = "根据id查", httpMethod = "GET")
    @GetMapping("/{id}")
    public Result<Demo> getById(@PathVariable String id){
        return Result.OK(demoService.getById(id));
    }

    @ApiOperation(value = "根据id删除", httpMethod = "DELETE")
    @DeleteMapping("/{id}")
    public Result<Boolean> removeById(@PathVariable String id){
        return Result.OK(demoService.removeById(id));
    }

    @ApiOperation(value = "新增", httpMethod = "POST")
    @PostMapping
    public Result<Boolean> save(@RequestBody Demo demo){
        return Result.OK(demoService.save(demo));
    }

    @ApiOperation(value = "修改", httpMethod = "PUT")
    @PutMapping("/{id}")
    public Result<Boolean> update(@PathVariable String id, @RequestBody Demo demo){
        demo.setId(id);
        return Result.OK(demoService.updateById(demo));
    }


}
