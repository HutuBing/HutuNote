package com.hutu.hutunote.model.dto;

import cn.hutool.core.collection.CollUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KongSearchResultItemDto {

    private String author;   // 分类名
    private String catId; // 分类id
    private String imgBigUrl; // 分类id
    private Integer imgCount; // 分类id
    private String imgUrl; // 分类id
    private String isbn;
    private String itemId;
    private Link link;
    private String press;
    private BigDecimal price;
    private String pubDateText;
    private String qualityText;
    private String shopAreaText;
    private String shopAvgShippingTime;
    private Link shopLink;
    private String showTimeText;
    private String title;
    private Postage postage; // 邮费

    public BigDecimal getTotalPrice() {
        if (postage != null && CollUtil.isNotEmpty(postage.getShippingList())) {
            return price.add(postage.getShippingList().get(0).getShippingFee());
        }
        return price;
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Link {
    private String app;
    private String h5;
    private String mp;
    private String pc;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Postage {
    private boolean sellerPayFreight;
    private List<Shipping> shippingList; // 运输方式列表
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Shipping {
    private BigDecimal shippingFee; // 运输费用
    private String shippingFeeText; // 运输费用
    private String shippingId;      // 运输方式id
    private String shippingName;    // 运输方式
}

