package com.yunke.admin.modular.openapi.model.param;

import com.yunke.admin.common.base.BaseAddParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @className OpenapiCallerAddParam
 * @description: 开放接口调用方管理_新增请求参数
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OpenapiCallerAddParam extends BaseAddParam {

    /**
     * 调用者名称
     */
    @NotBlank(message = "调用者名称不能为空，请检查参数callerName")
    private String callerName;
    /**
     * 备注
     */
    private String reamrk;
}