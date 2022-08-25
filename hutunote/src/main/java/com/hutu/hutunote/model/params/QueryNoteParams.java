package com.hutu.hutunote.model.params;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("搜索笔记")
public class QueryNoteParams {

    private String keyword;

}
