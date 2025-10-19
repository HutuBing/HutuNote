package com.hutu.hutunote.model.vo;


import com.alibaba.excel.annotation.ExcelProperty;
import com.hutu.hutunote.model.entity.BookInfo;
import com.hutu.hutunote.model.entity.OfflineInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadOfflineBookInfo {

    @ExcelProperty("商品ID(可选)")
    private String bookId;
    @ExcelProperty("商品编码(可选)")
    private String bookCode;
    @ExcelProperty("ISBN(可选)")
    private String isbn;

    public UploadOfflineBookInfo(OfflineInfo offlineInfo) {
        this.isbn = offlineInfo.getIsbn();
    }
}
