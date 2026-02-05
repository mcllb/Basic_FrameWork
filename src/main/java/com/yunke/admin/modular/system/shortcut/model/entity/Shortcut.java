package com.yunke.admin.modular.system.shortcut.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yunke.admin.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.lang.String;
import java.lang.Integer;

/**
 * @className Shortcut
 * @description: 快捷方式
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
@TableName("sys_shortcut")
@EqualsAndHashCode(callSuper = true)
public class Shortcut extends BaseEntity {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @TableField(value = "id")
    private String id;
    /**
     * 类型
     */
    @TableField(value = "type")
    private String type;
    /**
     * 名称
     */
    @TableField(value = "name")
    private String name;
    /**
     * 路径
     */
    @TableField(value = "path")
    private String path;
    /**
     * 图标
     */
    @TableField(value = "icon")
    private String icon;
    /**
     * 关联菜单id
     */
    @TableField(value = "permission_id")
    private String permissionId;
    /**
     * 背景颜色
     */
    @TableField(value = "bg_color")
    private String bgColor;
    /**
     * 显示排序
     */
    @TableField(value = "sort")
    private Integer sort;
    /**
     * 状态（字典： 1启用 0停用）
     */
    @TableField(value = "enable")
    private String enable;
    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;
}
