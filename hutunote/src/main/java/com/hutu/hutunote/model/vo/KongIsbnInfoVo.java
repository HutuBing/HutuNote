package com.hutu.hutunote.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KongIsbnInfoVo {

    @ExcelProperty("isbn")
    private String isbn;
    @ExcelProperty("孔网前五最高价")
    private BigDecimal top5MaxPrice;

}
