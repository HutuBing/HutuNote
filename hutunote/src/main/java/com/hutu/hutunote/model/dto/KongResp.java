package com.hutu.hutunote.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KongResp<T> {

    private boolean status; // 状态
    private T result; // 响应结果
    private String errMessage; // 错误信息
    private int errCode; // 业务错误码

}
