package com.hutu.hutunote.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TargetDto {

    private String index;             // 序号
    private String contractNo;        // 合同号
    private String prodName;          // 产品名称
    private String orderDate;         // 订单日期
    private String orderWeight;       // 订单重量
    private String sendWeight;        // 钢厂发货重量
    private String toSendWeight;      // 待交量
    private String beenSendWeight;    // 已出仓
    private String totalWeight;       // 总库存（仓库现货+在途+未发货）
    private String lockDate;          // 成本锁定日期
    private String relateRepo;        // 所属仓库
    private String inStorageWeight;   // 入仓重量
    private String outStorageWeight;  // 出仓重量
    private String remainWeight;      // 库存结余
    private String outStorageDate;    // 出仓日期
    private String outStoragePrice;   // 出仓网价
    private String businessType;      // 业务销售类型
    private String endpointAddress;   // 最终流向地点

}
