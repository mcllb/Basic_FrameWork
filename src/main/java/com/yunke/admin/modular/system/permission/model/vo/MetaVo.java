package com.yunke.admin.modular.system.permission.model.vo;

import lombok.Data;

/**
 * @className MetaVo
 * @description: 路由显示信息
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
public class MetaVo
{
    /**
     * 设置该路由在侧边栏和面包屑中展示的名字
     */
    private String title;

    /**
     * 设置该路由的图标，对应路径src/assets/icons/svg
     */
    private String icon;


    /**
     * 页面标题
     */
    private String pageTitle;
    /**
     * 权限id
     */
    private String permissionId;
    /**
     * 父级id
     */
    private String permissionParentId;
    /**
     * 权限名称
     */
    private String permissionName;
    /**
     * 权限编码
     */
    private String permissionCode;

}
