package com.yunke.admin.common.enums;

import com.yunke.admin.common.constant.SysExpCodePrefixConstant;
import com.yunke.admin.framework.core.annotion.ExpEnumCode;
import com.yunke.admin.framework.core.util.ExpEnumCodeUtil;

@ExpEnumCode(SysExpCodePrefixConstant.SATOKEN_EXCEPTION_CODE)
public enum SaTokenExceptionEnum implements AbstractExceptionEnum {

    TOKEN_ERROR(101,"token错误"),
    NO_PERMISSON(102,"没有操作权限"),
    NO_ROLE(103,"没有操作角色"),
    ;

    private final Integer code;

    private final String message;

    SaTokenExceptionEnum(Integer code, String message){
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
