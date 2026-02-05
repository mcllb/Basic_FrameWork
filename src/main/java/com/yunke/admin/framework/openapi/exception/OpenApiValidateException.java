package com.yunke.admin.framework.openapi.exception;


import com.yunke.admin.common.enums.OpenApiValidateExceptionEnum;
import com.yunke.admin.framework.core.exception.BaseException;
import lombok.Getter;

@Getter
public class OpenApiValidateException extends BaseException {

    private Integer code;

    private String errorMessage;

    private static String DEFAULT_ERROR_MESSAGE   = "系统发生错误，请联系系统管理员";

    public OpenApiValidateException(OpenApiValidateExceptionEnum exception) {
        super(exception.getMessage(),exception.getCode());
        this.code = exception.getCode();
        this.errorMessage = exception.getMessage();
    }

    public OpenApiValidateException(String message) {
        super(message);
    }

    public OpenApiValidateException() {
        super(DEFAULT_ERROR_MESSAGE);
    }
}
