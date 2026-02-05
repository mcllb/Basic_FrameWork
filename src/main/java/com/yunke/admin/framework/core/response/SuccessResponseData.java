package com.yunke.admin.framework.core.response;


public class SuccessResponseData<T> extends ResponseData {

    public SuccessResponseData() {
        super(true, DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MESSAGE, null);
    }

    public SuccessResponseData(T data) {
        super(true, DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MESSAGE, data);
    }

    public SuccessResponseData(Integer code, String message, T data) {
        super(true, code, message, data);
    }
}
