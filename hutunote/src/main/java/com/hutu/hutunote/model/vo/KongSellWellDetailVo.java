package com.hutu.hutunote.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KongSellWellDetailVo {

    private String author;   // 作者
    private String bookName; // 书名
    private String contentIntroduction; // 简介
    private String imgUrl; // 图片url
    private String isbn; // isbn
    private String newMinPrice;
    private String oldMinPrice;
    private String press;
    private BigDecimal price;
    private String pubDate;
    private BigDecimal salePrice; // 售价
    private String link1;
    private String link2;
    private String link3;
    private String link4;
    private String link5;
    private String imgUrl1;
    private String imgUrl2;
    private String imgUrl3;
    private String imgUrl4;
    private String imgUrl5;
    private BigDecimal minPrice;
    private BigDecimal top5MinPrice;
    private String catName;

}
