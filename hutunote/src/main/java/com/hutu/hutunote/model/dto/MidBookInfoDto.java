package com.hutu.hutunote.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MidBookInfoDto {

    private String isbn;
    private BigDecimal price;
    private BigDecimal expressPrice;
    private BigDecimal bookPrice;
    private String userId;
    private String itemId;
    private boolean postIn24Hours;

}
