package com.hutu.hutunote.model.entity;


import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("tb_book_info")
public class BookInfo {

    @TableId(type = IdType.AUTO)
    private Long id;
    @ExcelProperty("ISBN")
    private String isbn;
    @ExcelProperty("售价")
    private BigDecimal price;
    @ExcelProperty("库存")
    private Long inventory;
    private LocalDateTime updateTime;
    private LocalDateTime refreshTime;

}
