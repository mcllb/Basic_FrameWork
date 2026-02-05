package com.yunke.admin.modular.system.region.model.vo;

import com.yunke.admin.common.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


/**
 * @className RegionVO
 * @description: 行政区划代码表_详情返回数据
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RegionVO extends BaseVO {

    /**
     * 行政区划代码
     */
    private String id;
    /**
     * 行政区划名称
     */
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

    /**
     * 是否有子节点
     */
    private boolean hasChild;

    /**
     * 子节点集合
     */
    private List<RegionVO> children;

}