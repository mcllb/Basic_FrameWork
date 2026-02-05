package com.yunke.admin.common.model;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class PageWrapper<T> implements Serializable {

    private static final long serialVersionUID = -7529416451046323466L;

    /**
     * 返回记录
     */
    private List<T> rows;

    /**
     * 总页数
     */
    private Long totalCount;

    /**
     * 当前页码
     */
    private Long pageNo;

    /**
     * 每页数量
     */
    private Long pageSize;

    /**
     * 扩展数据
     */
    private Map<String,Object> extend = new HashMap<>();

    public PageWrapper() {
    }

    public PageWrapper(IPage<T> page) {
        this.rows = page.getRecords();
        this.totalCount = page.getTotal();
        this.pageSize = page.getSize();
        this.pageNo = page.getCurrent();
    }

    public PageWrapper(List<T> data, Long totalCount, Long pageSize, Long pageNo) {
        this.rows = data;
        this.totalCount = totalCount;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public PageWrapper(List<T> data, Long totalCount, Long pageSize, Long pageNo,Map<String,Object> extend) {
        this.rows = data;
        this.totalCount = totalCount;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.extend = extend;
    }

}
