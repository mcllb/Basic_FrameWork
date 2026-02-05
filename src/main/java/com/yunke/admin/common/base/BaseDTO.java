package com.yunke.admin.common.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseDTO implements Serializable {
    private static final long serialVersionUID = 2221984677849711204L;

    /**
     * 分页查询时的页面
     */
    private Integer pageNo = 1;

    /**
     * 分页查询时的每页数量
     */
    private Integer pageSize = 10;

    /**
     * 封装mybatis-plus分页查询page参数
     */
    public Page page(){
        Page page = new Page(pageNo,pageSize);
        return page;
    }

}
