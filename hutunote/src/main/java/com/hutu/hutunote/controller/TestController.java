package com.hutu.hutunote.controller;

import com.hutu.hutunote.common.Result;
import com.hutu.hutunote.execute.KongfuziExecute;
import com.hutu.hutunote.model.vo.KongIsbnInfoVo;
import com.hutu.hutunote.model.vo.KongSellWellDetailVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "DEMO接口")
@RestController
@RequestMapping("/test")
public class TestController extends BaseController {

    @Autowired
    private KongfuziExecute kongfuziExecute;

    @GetMapping("/test")
    public Result<List<KongSellWellDetailVo>> test() {
        return Result.OK(kongfuziExecute.getSellWellDetailList());
    }

    @GetMapping("/test2")
    public Result<List<KongIsbnInfoVo>> test2() {
        return Result.OK(kongfuziExecute.getIsbnInfoList());
    }

}
