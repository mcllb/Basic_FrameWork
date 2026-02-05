package com.yunke.admin.modular.system.permission.enums;

import com.yunke.admin.common.constant.SysExpCodePrefixConstant;
import com.yunke.admin.common.enums.AbstractExceptionEnum;
import com.yunke.admin.framework.core.annotion.ExpEnumCode;
import com.yunke.admin.framework.core.util.ExpEnumCodeUtil;

@ExpEnumCode(SysExpCodePrefixConstant.PERMISSION_EXCEPTION_CODE)
public enum PermissionExceptionEnum implements AbstractExceptionEnum {

    /**
     * 权限编码为空
     */
    PERMISSION_CODE_EMPTY(101, "权限编码不能为空"),

    /**
     * 权限编码重复
     */
    PERMISSION_CODE_REPEAT(102, "权限编码不能重复"),

    /**
     * 权限名称重复
     */
    PERMISSION_NAME_REPEAT(103, "菜单名称不能重复"),

    /**
     * 路由地址为空
     */
    PERMISSION_PATH_EMPTY(104, "路由地址不能为空"),

    /**
     * 组件路径为空
     */
    PERMISSION_COMPONENT_EMPTY(105, "组件路径不能为空"),

    /**
     * 父节点不存在
     */
    PARENT_NOT_EXIST(106, "父节点不存在"),

    /**
     * 父节点不存在
     */
    PARENT_STATUS_EXCEPTION(107, "父节点状态异常"),

    /**
     * 父节点为当前节点
     */
    PARENT_CAN_NOT_EQ_ID(108, "父级菜单不能为当前节点"),

    /**
     * 菜单图标不能为空
     */
    ICON_CAN_NOT_EMPTY(109, "菜单图标不能为空"),

    /**
     * 权限已被角色使用
     */
    USED_BY_ROLE(110, "权限已被角色使用"),

    /**
     * 菜单打开方式不能为空
     */
    OPEN_TYPE_CAN_NOT_EMPTY(111, "菜单打开方式不能为空"),

    /**
     * 菜单是否可见不能为空
     */
    VISIBLE_CAN_NOT_EMPTY(112, "菜单是否可见不能为空"),

    ;

    private final Integer code;

    private final String message;

    PermissionExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return ExpEnumCodeUtil.getExpEnumCode(this.getClass(),code);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
