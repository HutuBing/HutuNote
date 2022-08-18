package com.hutu.hutunote.common;

import com.github.pagehelper.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Collection;
import java.util.List;

@Data
@ApiModel
public class PageInfo {

    private long pageNum;
    private long pageSize;
    private long totalRecord;
    private long totalPage;

    public static PageInfo of(List list){
        PageInfo pageInfo = new PageInfo();
        if (list instanceof Page) {
            Page page = (Page)list;
            pageInfo.setPageNum(page.getPageNum());
            pageInfo.setPageSize(page.getPageSize());
            pageInfo.setTotalPage(page.getPages());
            pageInfo.setTotalRecord(page.getTotal());

        } else if (list instanceof Collection) {
            pageInfo.setPageNum(1);
            pageInfo.setPageSize(list.size());
            pageInfo.setTotalPage(list.size() > 0 ? 1 : 0);
            pageInfo.setTotalRecord(list.size());
        }
        return pageInfo;
    }
}
