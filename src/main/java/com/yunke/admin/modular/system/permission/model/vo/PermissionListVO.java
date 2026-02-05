package com.yunke.admin.modular.system.permission.model.vo;

import com.yunke.admin.common.base.BaseVO;
import com.yunke.admin.common.enums.CommonStatusEnum;
import com.yunke.admin.framework.core.annotion.EnumDictField;
import com.yunke.admin.modular.system.permission.enums.PermissionOpenTypeEnum;
import com.yunke.admin.modular.system.permission.enums.PermissionTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class PermissionListVO extends BaseVO {

    /**
     * 权限id
     */
    private String id;

    /**
     * 父级id
     */
    private String parentId;


    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 权限编码
     */
    private String permissionCode;

    /**
     * 权限类型（字典：0目录 1菜单 2按钮）
     */
    @EnumDictField(PermissionTypeEnum.class)
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
    private Integer sort;

    /**
     * 是否可见（字典：1显示 0隐藏）
     */
    @EnumDictField(CommonStatusEnum.class)
    private String visible;

    /**
     * 状态（字典：1正常 0停用）
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 子菜单
     */
    private List<PermissionListVO> children;

    /**
     * 打开方式（字典：1内链 2外链）
     */
    @EnumDictField(PermissionOpenTypeEnum.class)
    private String openType;

    private String isExternal;

    /**
     * 页面标题
     */
    private String pageTitle;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
