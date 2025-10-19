package com.hutu.hutunote.model.dto;

import cn.hutool.core.collection.CollUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BookFeeDto {

    private String itemId;
    private String userId;
    private boolean isSeller;
    private List<FeeDto> fee;

    public BigDecimal getTotalFee() {
        BigDecimal result = BigDecimal.ZERO;
        if (CollUtil.isEmpty(fee)) {
            return result;
        }
        for (FeeDto feeDto : fee) {
            result = result.add(feeDto.getTotalFee());
        }
        return result;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class FeeDto {
        private String shippingId;
        private String shippingName;
        private BigDecimal totalFee;
        private BigDecimal filterTotalFee;
        private BigDecimal condit;
    }

}
