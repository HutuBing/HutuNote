package com.hutu.hutunote.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KongPageResp<T> {

    private int current; // 当前页
    private int total; // 总页数
    private List<T> data; // 分页数据

}
