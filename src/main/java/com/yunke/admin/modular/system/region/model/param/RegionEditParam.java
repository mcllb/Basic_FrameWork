package com.yunke.admin.modular.system.region.model.param;

import com.yunke.admin.common.base.BaseEditParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * @className RegionEditParam
 * @description: 行政区划代码表_编辑请求参数
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RegionEditParam extends BaseEditParam {

    /**
     * 行政区划代码
     */
    @NotEmpty(message = "主键不能为空，请检查参数id")
    private String id;
    /**
     * 行政区划名称
     */
    @NotEmpty(message = "行政区划名称不能为空，请检查参数regionName")
    private String regionName;
    /**
     * 小组号
     */
    private String regionCode;
    /**
     * 简称
     */
    private String alias;
    /**
     * 父代码
     */
    private String parentId;
    /**
     * 行政级别
     */
    private String level;
    /**
     * 邮政编码
     */
    private String postcode;
    /**
     * 排序号
     */
    private Integer sort;
    /**
     * 是否启用（1是0否）
     */
    private String enable;
}