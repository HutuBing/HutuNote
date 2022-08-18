package com.hutu.hutunote.controller;

import com.github.pagehelper.PageHelper;
import com.hutu.hutunote.common.PageParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected void startPage(PageParams params){
        PageHelper.startPage(params.getPageNo(), params.getPageSize());
    }

}
