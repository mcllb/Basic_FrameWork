package com.yunke.admin.framework.openapi.validate;


/**
 * @className OpenApiValidateService
 * @description: openapi校验接口
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
public interface OpenApiValidateService {

    /**
     * 校验方法
     * @param openApiValidateParam
     */
    void validate(OpenApiValidateParam openApiValidateParam);

}
