package com.yunke.admin.modular.system.region.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yunke.admin.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.lang.String;
import java.lang.Integer;
import java.util.List;

/**
 * @className Region
 * @description: 行政区划代码表_实体
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
@TableName("sys_region")
@EqualsAndHashCode(callSuper = true)
public class Region extends BaseEntity {

    /**
     * 行政区划代码
     */
    @TableId(type = IdType.INPUT)
    @TableField(value = "id")
    private String id;
    /**
     * 行政区划名称
     */
    @TableField(value = "region_name")
    private String regionName;
    /**
     * 小组号
     */
    @TableField(value = "region_code")
    private String regionCode;
    /**
     * 简称
     */
    @TableField(value = "alias")
    private String alias;
    /**
     * 父代码
     */
    @TableField(value = "parent_id")
    private String parentId;
    /**
     * 行政级别
     */
    @TableField(value = "level")
    private String level;
    /**
     * 邮政编码
     */
    @TableField(value = "postcode")
    private String postcode;
    /**
     * 排序号
     */
    @TableField(value = "sort")
    private Integer sort;
    /**
     * 是否启用（1是0否）
     */
    @TableField(value = "enable")
    private String enable;

    @TableField(exist = false)
    private List<Region> children;
}
