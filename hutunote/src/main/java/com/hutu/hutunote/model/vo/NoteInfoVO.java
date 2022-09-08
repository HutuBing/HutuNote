package com.hutu.hutunote.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

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
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDateTime createDate;
    @ApiModelProperty("计划时间")
    @JsonFormat(pattern="yyyy/MM/dd HH:mm")
    private LocalDateTime planTime;
    @ApiModelProperty("计划说明")
    private String planDesc;

}
