package com.hutu.hutunote.controller;

import cn.hutool.json.JSONObject;
import com.hutu.hutunote.common.Result;
import com.hutu.hutunote.service.IFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "文件相关接口")
@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private IFileService fileService;

    @ApiOperation(value = "上传文件", httpMethod = "POST")
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file){
        return Result.OK(fileService.upload(file));
    }

    @ApiOperation(value = "获取文件", httpMethod = "GET")
    @GetMapping("/{fileName}")
    public void getFile(@PathVariable String fileName){
        fileService.getFile(fileName);
    }

    @ApiOperation(value = "上传文件", httpMethod = "POST")
    @PostMapping("/uploadHt")
    public Result uploadHt(MultipartFile file){
        fileService.uploadHt(file);
        return Result.OK();
    }

    @ApiOperation(value = "上传文件", httpMethod = "POST")
    @PostMapping("/uploadImage")
    public Result uploadImage(MultipartFile file){
        fileService.uploadImage(file);
        return Result.OK();
    }

    @ApiOperation(value = "上传文件", httpMethod = "POST")
    @PostMapping("/uploadTemplate")
    public Result uploadTemplate(MultipartFile file){
        fileService.uploadTemplate(file);
        return Result.OK();
    }

    @ApiOperation(value = "上传文件", httpMethod = "POST")
    @PostMapping("/listHt")
    public Result listHt(){
        return Result.OK(fileService.listHt());
    }

    @ApiOperation(value = "更新配置", httpMethod = "POST")
    @PostMapping("/updateConfig")
    public Result updateConfig(@RequestBody JSONObject params){
        fileService.updateConfig(params);
        return Result.OK();
    }

    @ApiOperation(value = "更新配置", httpMethod = "POST")
    @PostMapping("/getConfig")
    public Result getConfig(){
        return Result.OK(fileService.getConfig());
    }

}
