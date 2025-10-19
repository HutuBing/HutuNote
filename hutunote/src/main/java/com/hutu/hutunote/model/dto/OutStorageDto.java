package com.hutu.hutunote.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OutStorageDto {

    private String index;           // 序号
    private String planDate;        // 计划日期
    private String prodName;        // 产品名称
    private String carNo;           // 车号
    private String weight;          // 磅计重量
    private String armWeight;       // 理论重量
    private String count;           // 出库件数
    private String address;         // 地址
    private String phone;           // 收货电话
    private String outStorageNo;    // 出库单号
    private String userFormNo;      // 客户单号
    private String desc;            // 备注

}
