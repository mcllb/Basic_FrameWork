package com.yunke.admin.framework.core.response;

import com.yunke.admin.common.enums.AbstractExceptionEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ErrorResponseData extends ResponseData {

    public ErrorResponseData(String message) {
        super(false, DEFAULT_ERROR_CODE, message, null);
    }

    public ErrorResponseData(Integer code, String message) {
        super(false, code, message, null);
    }

    public ErrorResponseData (AbstractExceptionEnum abstractBaseExceptionEnum){
        super(false,abstractBaseExceptionEnum.getCode(),abstractBaseExceptionEnum.getMessage(),null);
    }
}
