package com.hutu.hutunote.model.entity;


import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("tb_offline_info")
public class OfflineInfo {

    @TableId(type = IdType.AUTO)
    private Long id;
    @ExcelProperty("ISBN")
    private String isbn;

}
