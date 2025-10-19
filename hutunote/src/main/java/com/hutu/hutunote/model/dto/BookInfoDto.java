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
public class BookInfoDto {

    private String isbn;
    private Long inventory;
    private BigDecimal price;

}
