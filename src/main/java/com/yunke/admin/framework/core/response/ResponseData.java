package com.yunke.admin.framework.core.response;

import com.yunke.admin.common.enums.AbstractExceptionEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @className ResponseData
 * @description: 通用请求响应参数
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@ApiModel("通用请求响应参数")
@Data
public class ResponseData<T> implements Serializable {
    private static final long serialVersionUID = -3249897236746249101L;

    public static final String DEFAULT_SUCCESS_MESSAGE = "请求成功";

    public static final String DEFAULT_ERROR_MESSAGE = "请求失败";

    public static final Integer DEFAULT_SUCCESS_CODE = 10000;

    public static final Integer DEFAULT_ERROR_CODE = 10001;


    /**
     * 请求是否成功
     */
    @ApiModelProperty(value = "请求是否成功")
    private Boolean success;

    /**
     * 响应状态码
     */
    @ApiModelProperty(value = "响应状态码（成功10000，其他值为失败）")
    private Integer code;

    /**
     * 响应信息
     */
    @ApiModelProperty(value = "响应信息描述")
    private String message;

    /**
     * 响应数据
     */
    @ApiModelProperty(value = "响应数据")
    private T data;

    /**
     * 响应时间戳
     */
    @ApiModelProperty(value = "响应时间戳")
    private long timestamp = System.currentTimeMillis();

    public ResponseData() {
    }

    public ResponseData (Boolean success, Integer code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ResponseData bool(Boolean success){
        if(success){
            return success();
        }
        return fail(DEFAULT_ERROR_MESSAGE);
    }

    public static ResponseData count(Integer count){
        return bool(count > 0);
    }

    public static SuccessResponseData success() {
        return new SuccessResponseData();
    }

    public static SuccessResponseData success(Object data) {
        return new SuccessResponseData(data);
    }

    public static SuccessResponseData success(Integer code, String message, Object object) {
        return new SuccessResponseData(code, message, object);
    }

    public static SuccessResponseData success(Integer code, String message) {
        return new SuccessResponseData(code, message, null);
    }

    public static ErrorResponseData fail(String message) {
        return new ErrorResponseData(message);
    }

    public static ErrorResponseData fail(Integer code, String message) {
        return new ErrorResponseData(code, message);
    }


    public static ErrorResponseData exception(AbstractExceptionEnum abstractBaseExceptionEnum){
        return fail(abstractBaseExceptionEnum.getCode(),abstractBaseExceptionEnum.getMessage());
    }


}
