package com.yunke.admin.framework.core.exception;


import com.yunke.admin.common.enums.AbstractExceptionEnum;

/**
 * @className CacheException
 * @description: 缓存异常处理类
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
public class CacheException extends BaseException {

    private final Integer code;

    private final String errorMessage;

    public CacheException(AbstractExceptionEnum exception) {
        super(exception.getMessage());
        this.code = exception.getCode();
        this.errorMessage = exception.getMessage();
    }
}
