package com.hutu.hutunote.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("笔记列表对象")
public class NoteInfoVO {

    @ApiModelProperty("ID")
    private String id;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("类型")
    private String noteType;
    @ApiModelProperty("目录ID")
    private String cataId;
    @ApiModelProperty("文档大小")
    private String fileSize;
    @ApiModelProperty("创建时间")
    private Date createDate;

}
