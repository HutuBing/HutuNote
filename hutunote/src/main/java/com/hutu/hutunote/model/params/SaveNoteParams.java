package com.hutu.hutunote.model.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("新增笔记入参")
public class SaveNoteParams {

    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("类型")
    private String noteType;
    @ApiModelProperty("目录ID")
    private String cataId;

}
