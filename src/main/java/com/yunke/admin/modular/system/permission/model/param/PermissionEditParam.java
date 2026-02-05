package com.yunke.admin.modular.system.permission.model.param;

import com.yunke.admin.common.base.BaseEditParam;
import com.yunke.admin.framework.core.validation.EnumDictValidator;
import com.yunke.admin.modular.system.permission.enums.PermissionOpenTypeEnum;
import com.yunke.admin.modular.system.permission.enums.PermissionTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class PermissionEditParam extends BaseEditParam {

    /**
     * id
     */
    @NotEmpty(message = "id不能为空,请检查参数id")
    private String id;


    /**
     * 上级菜单
     */
    @NotEmpty(message = "上级菜单不能为空，请检查参数parentId")
    private String parentId;


    /**
     * 权限名称
     */
    @NotEmpty(message = "权限名称不能为空，请检查参数permissionName")
    private String permissionName;

    /**
     * 权限编码
     */
    private String permissionCode;

    /**
     * 权限类型（字典：0目录 1菜单 2按钮）
     */
    @NotEmpty(message = "权限类型不能为空，请检查参数permissionType")
    @EnumDictValidator(value = PermissionTypeEnum.class,message = "权限类型不在正确的枚举中，请检查参数permissionType")
    private String permissionType;

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
     * 显示排序
     */
    @NotNull(message = "显示排序不能为空，请检查参数sort")
    private Integer sort;

    /**
     * 是否可见（字典：1显示 0隐藏）
     */
    private String visible;


    /**
     * 备注
     */
    private String remark;

    /**
     * 打开方式（字典: 1内链 2外链）
     */
    @EnumDictValidator(value = PermissionOpenTypeEnum.class,message = "打开方式不在正确的枚举中，请检查参数openType")
    private String openType;

    /**
     * 页面标题
     */
    private String pageTitle;

}
