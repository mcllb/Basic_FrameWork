package com.yunke.admin.framework.core.exception;

import com.yunke.admin.common.enums.AbstractExceptionEnum;
import lombok.Getter;

/**
 * @className 认证相关的异常
 * @description: //TODO
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Getter
public class AuthException extends BaseException {

    private final Integer code;

    private final String errorMessage;

    public AuthException(AbstractExceptionEnum exception) {
        super(exception.getMessage());
        this.code = exception.getCode();
        this.errorMessage = exception.getMessage();
    }

}
