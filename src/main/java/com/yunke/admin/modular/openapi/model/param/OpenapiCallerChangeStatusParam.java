package com.yunke.admin.modular.openapi.model.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class OpenapiCallerChangeStatusParam {

    /**
     * 主键
     */
    @NotEmpty(message = "主键不能为空，请检查参数id")
    private String id;
    /**
     * 状态
     */
    @NotEmpty(message = "状态不能为空，请检查参数status")
    private String status;

}
