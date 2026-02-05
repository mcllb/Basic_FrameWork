package com.yunke.admin.common.enums;

import com.yunke.admin.common.constant.SysExpCodePrefixConstant;
import com.yunke.admin.framework.core.annotion.ExpEnumCode;
import com.yunke.admin.framework.core.util.ExpEnumCodeUtil;

@ExpEnumCode(SysExpCodePrefixConstant.OPENAPI_EXCEPTION_CODE)
public enum OpenApiValidateExceptionEnum implements AbstractExceptionEnum {

    CALLER_NOT_FOUND(101,"caller not found"),
    CALLER_SECRET_ERROR(102,"caller secret error"),
    CALLER_STATUS_EXP(103,"caller status exception"),
    TOKEN_EXPIRED(104,"token已过期"),
    TOKEN_VALIDATE_FAIL(105,"token校验未通过"),
    TOKEN_INVALID(106,"token invalid"),
    ;

    private final Integer code;

    private final String message;

    OpenApiValidateExceptionEnum(Integer code, String message){
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
