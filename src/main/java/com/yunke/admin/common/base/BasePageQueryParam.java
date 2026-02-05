package com.yunke.admin.common.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @className BasePageQueryParam
 * @description: 分页查询参数封装基类
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BasePageQueryParam extends BaseQueryParam {

    private static final long serialVersionUID = -2693410001630962037L;
    /**
     * 分页查询时的页面
     */
    private Long pageNo = 1L;

    /**
     * 分页查询时的每页数量
     */
    private Long pageSize = 10L;

    /**
     * 封装mybatis-plus分页查询page参数
     */
    public Page page(){
        Page page = new Page(pageNo,pageSize);
        return page;
    }

    public void setPageNo(Long pageNo) {
        this.pageNo = pageNo == null || pageNo < 1L ? 1L : pageNo;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize == null || pageSize < 1L ? 10L : pageSize;
    }

    public Long getPageNo() {
        return pageNo == null || pageNo < 1L  ? 1L : pageNo;
    }

    public Long getPageSize() {
        return pageSize == null || pageSize < 1L ? 10L : pageSize;
    }
}
