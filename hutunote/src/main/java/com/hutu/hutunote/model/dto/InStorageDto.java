package com.hutu.hutunote.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InStorageDto {

    private String index;           // 序号
    private String orderUser;       // 订单客户
    private String orderNo;         // 订单编号
    private String prodName;        // 产品名称
    private String carNo;           // 车牌号
    private String sendNum;         // 发货量（吨）
    private String count;           // 件数
    private String sendTime;        // 发货日期
    private String outStorageNo;    // 出库编号
    private String receiveDate;     // 到达日期
    private String desc;            // 备注

    public TargetDto buildTargetDto(InStorageDto item) {
        TargetDto target = new TargetDto();
        target.setInStorageWeight(this.sendNum);
        target.setContractNo(this.orderNo);
        target.setProdName(this.prodName);
        target.setOrderDate(this.receiveDate);
        return target;
    }
}
