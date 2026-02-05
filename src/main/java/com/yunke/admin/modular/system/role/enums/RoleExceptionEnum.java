package com.yunke.admin.modular.system.role.enums;

import com.yunke.admin.common.constant.SysExpCodePrefixConstant;
import com.yunke.admin.common.enums.AbstractExceptionEnum;
import com.yunke.admin.framework.core.annotion.ExpEnumCode;
import com.yunke.admin.framework.core.util.ExpEnumCodeUtil;

@ExpEnumCode(SysExpCodePrefixConstant.ROLE_EXCEPTION_CODE)
public enum RoleExceptionEnum implements AbstractExceptionEnum {

    /**
     * 角色名称重复
     */
    ROLE_NAME_REPEAT(101,"角色名称重复"),

    /**
     * 角色编码重复
     */
    ROLE_CODE_REPEAT(102, "角色编码重复"),

    /**
     * 角色已被使用
     */
    ROLE_HAS_USED(103, "角色已被用户使用");

    private final Integer code;

    private final String message;

    RoleExceptionEnum(Integer code, String message) {
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
