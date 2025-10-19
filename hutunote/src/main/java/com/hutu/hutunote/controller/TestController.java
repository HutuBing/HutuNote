package com.hutu.hutunote.controller;

import com.hutu.hutunote.common.Result;
import com.hutu.hutunote.execute.BookExecute;
import com.hutu.hutunote.execute.KongfuziExecute;
import com.hutu.hutunote.model.vo.KongIsbnInfoVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(tags = "DEMO接口")
@RestController
@RequestMapping("/test")
public class TestController extends BaseController {

    @Autowired
    private KongfuziExecute kongfuziExecute;
    @Autowired
    private BookExecute bookExecute;

    @GetMapping("/test")
    public Result<List<KongIsbnInfoVo>> test() {
        return Result.OK(kongfuziExecute.getSellWellDetailList());
    }

    @PostMapping("/uploadBook")
    public void uploadBook(MultipartFile file) {
        bookExecute.uploadBook(file);
    }

    @GetMapping("/downloadBook/{inventory}")
    public void downloadBook(@PathVariable("inventory") Long inventory, HttpServletResponse response) throws Exception{
        bookExecute.downloadBook(inventory, response);
    }

    @GetMapping("/refreshOnlineBook")
    @PostConstruct
    public void refreshOnlineBook() {
        bookExecute.refreshOnlineBook();
    }

    @GetMapping("/refreshOfflineBook")
//    @PostConstruct
    public void refreshOfflineBook() {
        bookExecute.refreshOfflineBook();
    }

    @GetMapping("/getNewBook")
//    @PostConstruct
    public void getNewBook() {
        bookExecute.getNewBook();
    }

    @GetMapping("/autoUploadOnline")
    @PostConstruct
    public void autoUploadOnline() {
        bookExecute.autoUploadOnline();
    }

    @GetMapping("/autoUploadOffline")
    @PostConstruct
    public void autoUploadOffline() {
        bookExecute.autoUploadOffline();
    }

}
