package com.hutu.hutunote.model.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("tb_config_info")
public class ConfigInfo {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String code;
    private String value;
    private LocalDateTime updateTime;

}
