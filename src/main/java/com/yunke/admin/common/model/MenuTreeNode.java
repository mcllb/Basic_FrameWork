package com.yunke.admin.common.model;

import lombok.Data;

import java.util.List;

@Data
public class MenuTreeNode {

    /**
     * 菜单id
     */
    private String id;

    /**
     * 菜单名称
     */
    private String permissionName;

    /**
     * 权限类型（字典：0目录 1菜单 2按钮）
     */
    private String permissionType;
    /**
     * 权限编码
     */
    private String permissionCode;

    /**
     * 父节点id
     */
    private String parentId;

    /**
     * 是否禁用
     */
    private boolean disabled = false;

    /**
     * 图标
     */
    private String icon;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 是否可见（字典：1显示 0隐藏）
     */
    private String visible;

    /**
     * 打开方式（字典：1内链 2外链）
     */
    private String openType;

    private String isExternal;

    /**
     * 页面标题
     */
    private String pageTitle;

    /**
     * 子节点
     */
    private List<MenuTreeNode> children;

}
