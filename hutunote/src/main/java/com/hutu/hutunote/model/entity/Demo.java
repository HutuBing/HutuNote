package com.hutu.hutunote.model.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("TB_DEMO")
public class Demo {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private String name;

}
