package com.yunke.admin.common.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @className BaseQueryParam
 * @description: 查询参数封装基类
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BaseQueryParam extends BaseParam {

    private static final long serialVersionUID = 8751863540242840769L;
    /**
     * 搜索关键字
     */
    private String keyword;

    /**
     * 排序规则
     */
    private List<Sort> sorts = new ArrayList<>();

    @Data
    public static class Sort {

        /**
         * 排序字段
         */
        private String field;

        /**
         * 是否正序排序
         */
        private boolean asc;

    }

}
