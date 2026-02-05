package com.yunke.admin.framework.core.exception;


import com.yunke.admin.common.enums.AbstractExceptionEnum;
import lombok.Getter;

/**
 * @className ServiceException
 * @description: 通用异常处理类
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Getter
public class ServiceException extends BaseException {

    private Integer code;

    private String errorMessage;

    private static String DEFAULT_ERROR_MESSAGE   = "系统发生错误，请联系系统管理员";

    public ServiceException(AbstractExceptionEnum exception) {
        super(exception.getMessage(),exception.getCode());
        this.code = exception.getCode();
        this.errorMessage = exception.getMessage();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException() {
        super(DEFAULT_ERROR_MESSAGE);
    }
}
