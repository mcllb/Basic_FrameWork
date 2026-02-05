package com.yunke.admin.modular.system.region.model.param;

import com.yunke.admin.common.base.BasePageQueryParam;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @className RegionPageQueryParam
 * @description: 行政区划代码表_分页查询请求参数
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RegionPageQueryParam extends BasePageQueryParam {

    /**
     * 父代码
     */
    private String parentId;

    /**
     * 行政级别
     */
    private String level;
    
}
