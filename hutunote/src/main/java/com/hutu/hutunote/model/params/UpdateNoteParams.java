package com.hutu.hutunote.model.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("修改基本信息入参")
public class UpdateNoteParams {

    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("内容")
    private String fileText;
    @ApiModelProperty("分类id")
    private String cataId;

}
