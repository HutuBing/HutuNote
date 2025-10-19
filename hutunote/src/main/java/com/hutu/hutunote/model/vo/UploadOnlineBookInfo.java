package com.hutu.hutunote.model.vo;


import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hutu.hutunote.model.entity.BookInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadOnlineBookInfo {

    @ExcelProperty("ISBN")
    private String isbn;
    @ExcelProperty("售价")
    private BigDecimal price;
    @ExcelProperty("库存")
    private Long inventory;
    @ExcelProperty("商品编码(选填)")
    private String bookCode;
    @ExcelProperty("书名(选填)")
    private String bookName;
    @ExcelProperty("商品ID(选填)")
    private String bookId;
    @ExcelProperty("diy详情(不要填)")
    private String diy;
    @ExcelProperty("定价核实(选填)")
    private String check;
    @ExcelProperty("主图地址(选填)")
    private String imgUrl;

    public UploadOnlineBookInfo(BookInfo bookInfo) {
        this.isbn = bookInfo.getIsbn();
        this.price = bookInfo.getPrice();
        this.inventory = bookInfo.getInventory();
    }
}
