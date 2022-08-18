package com.hutu.hutunote.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class PageParams {

    @ApiModelProperty("每页记录数，默认10")
    private int pageSize = 10;
    @ApiModelProperty("页数，默认1")
    private int pageNo = 1;

}
