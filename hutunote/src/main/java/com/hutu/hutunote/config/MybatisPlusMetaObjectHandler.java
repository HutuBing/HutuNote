package com.hutu.hutunote.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createDate", new Date(), metaObject);
        this.setFieldValByName("state", "E", metaObject);
        //todo 上下文获取用户code
        this.setFieldValByName("createOper", "test", metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateDate", new Date(), metaObject);
        //todo 上下文获取用户code
        this.setFieldValByName("updateOper", "test", metaObject);
    }

}
