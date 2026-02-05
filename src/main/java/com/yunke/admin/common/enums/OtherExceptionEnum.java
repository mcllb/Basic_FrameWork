package com.yunke.admin.common.enums;


import com.yunke.admin.common.constant.SysExpCodePrefixConstant;
import com.yunke.admin.framework.core.annotion.ExpEnumCode;
import com.yunke.admin.framework.core.util.ExpEnumCodeUtil;

@ExpEnumCode(SysExpCodePrefixConstant.OTHER_EXCEPTION_CODE)
public enum OtherExceptionEnum implements AbstractExceptionEnum {

    SYSTEM_ERROR(101,"系统发生错误，请联系系统管理员"),

    TOKEN_ERROR(102,"token验证错误"),

    HTTP_METHOD_ERROR(103,"请求方法错误"),

    HTTP_HANDLER_ERROR(104,"请求路径不存在"),

    PARAMETER_ERROR(105, "参数校验失败");

    private final Integer code;

    private final String message;



    OtherExceptionEnum(Integer code, String message) {
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
