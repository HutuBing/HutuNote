package com.hutu.hutunote.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KongCategoryDto {

    private String key;   // 分类名
    private Integer value; // 分类id

}
