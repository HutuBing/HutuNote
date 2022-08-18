package com.hutu.hutunote.common;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 接口返回数据格式
 * @author liaojb
 * @date  2022年8月18日
 */
@Data
@ApiModel(value="分页查询返回对象", description="分页查询返回对象")
public class PageResult<T> extends Result<List<T>> implements Serializable {

    private PageInfo page;

    public static<T> PageResult<T> OK(List<T> data){
        PageResult<T> r = new PageResult<T>();
        r.setSuccess(true);
        r.setCode(CodeConstant.SUCCESS);
        r.setData(data);
        r.setPage(PageInfo.of(data));
        return r;
    }

}
