package com.hutu.hutunote.model.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("tb_book_sale_info")
public class BookSaleInfo {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String isbn;
    private BigDecimal price;
    private Long inventory;
    private String storeCode; // 在售商家编码
    private LocalDateTime updateTime;
    private LocalDateTime refreshTime;
    private String itemId;

}
