package com.hutu.hutunote.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;

@Data
public class BaseEntity {

    @TableField(value = "create_oper", fill = FieldFill.INSERT)
    private String createOper;
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private Date createDate;
    @TableField(value = "update_oper", fill = FieldFill.UPDATE)
    private String updateOper;
    @TableField(value = "update_date", fill = FieldFill.UPDATE)
    private Date updateDate;
    @TableField(value = "state", fill = FieldFill.INSERT)
    @TableLogic(value = "E", delval = "D")
    private String state;

}
