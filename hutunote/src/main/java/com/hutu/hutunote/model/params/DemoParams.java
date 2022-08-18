package com.hutu.hutunote.model.params;

import com.hutu.hutunote.common.PageParams;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class DemoParams extends PageParams {

    @ApiModelProperty("id")
    private String id;

}
