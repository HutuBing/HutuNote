package com.hutu.hutunote.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ConfigEnum {

    ONLINE_UPDATE_TIME("ONLINE_UPDATE_TIME"),
    REFRESH_PRICE_MIN("REFRESH_PRICE_MIN"),
    REFRESH_PRICE_MAX("REFRESH_PRICE_MAX"),
    ON_SALE_COUNT_MIN("ON_SALE_COUNT_MIN"),
    ;

    final String code; // 配置编码

}
