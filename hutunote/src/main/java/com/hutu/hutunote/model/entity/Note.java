package com.hutu.hutunote.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("TB_NOTE")
public class Note extends BaseEntity {

    private String name;
    private String noteType;
    private String filePath;
    private String fileText;
    private String cataId;
    private String fileSize;

}
