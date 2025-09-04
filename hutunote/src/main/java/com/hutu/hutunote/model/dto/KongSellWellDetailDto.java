package com.hutu.hutunote.model.dto;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KongSellWellDetailDto {

    private String author;   // 分类名
    private String bookName; // 分类id
    private String contentIntroduction; // 分类id
    private String imgUrl; // 分类id
    private String isbn; // 分类id
    private ItemUrls itemUrls;
    private String newMinPrice;
    private String oldMinPrice;
    private String press;
    private BigDecimal price;
    private String pubDate;

    public String getLink() {
        String result = itemUrls.getPcUrl();
        if (StrUtil.isBlank(result)) {
            result = itemUrls.getMiniUrl();
        }
        if (StrUtil.isBlank(result)) {
            result = itemUrls.getMUrl();
        }
        if (StrUtil.isBlank(result)) {
            result = itemUrls.getAppUrl();
        }
        return result;
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class ItemUrls {
    private String appUrl;
    private String mUrl;
    private String miniUrl;
    private String pcUrl;
}