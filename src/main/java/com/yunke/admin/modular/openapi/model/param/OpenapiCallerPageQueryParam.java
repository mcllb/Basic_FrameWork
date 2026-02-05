package com.yunke.admin.modular.openapi.model.param;

import com.yunke.admin.common.base.BasePageQueryParam;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @className OpenapiCallerPageQueryParam
 * @description: 开放接口调用方管理_分页查询请求参数
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OpenapiCallerPageQueryParam extends BasePageQueryParam {
    /**
     * 状态（1正常0停用）
     */
    private String status;
}
