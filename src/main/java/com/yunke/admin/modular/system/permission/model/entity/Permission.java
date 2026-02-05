package com.yunke.admin.modular.system.permission.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yunke.admin.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @className Permission
 * @description: 系统权限表_实体
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_permission")
public class Permission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 父级id
     */
    @TableField("parent_id")
    private String parentId;

    /**
     * 父级ids
     */
    @TableField("parent_ids")
    private String parentIds;

    /**
     * 权限名称
     */
    @TableField("permission_name")
    private String permissionName;

    /**
     * 权限编码
     */
    @TableField("permission_code")
    private String permissionCode;

    /**
     * 权限类型（字典：0目录 1菜单 2按钮）
     */
    @TableField("permission_type")
    private String permissionType;

    /**
     * 图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 路由地址
     */
    @TableField("path")
    private String path;

    /**
     * 组件路径
     */
    @TableField("component")
    private String component;

    /**
     * 显示排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 是否可见（字典：1显示 0隐藏）
     */
    @TableField("visible")
    private String visible;

    /**
     * 状态（字典：1正常 0停用）
     */
    @TableField("status")
    private String status;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 打开方式（字典：1Frame 2新窗口）
     */
    @TableField("open_type")
    private String openType;

    /**
     * 是否为外链（字典: 1是 0否）
     */
    @TableField("is_external")
    private String isExternal;

    /**
     * 页面标题
     */
    @TableField("page_title")
    private String pageTitle;


}
