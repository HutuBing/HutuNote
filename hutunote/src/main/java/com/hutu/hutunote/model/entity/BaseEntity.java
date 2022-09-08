package com.hutu.hutunote.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class BaseEntity {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    @TableField(value = "create_oper", fill = FieldFill.INSERT)
    private String createOper;
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private LocalDateTime createDate;
    @TableField(value = "update_oper", fill = FieldFill.UPDATE)
    private String updateOper;
    @TableField(value = "update_date", fill = FieldFill.UPDATE)
    private LocalDateTime updateDate;
    @TableField(value = "state", fill = FieldFill.INSERT)
    @TableLogic(value = "E", delval = "D")
    private String state;

}
