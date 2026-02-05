package com.yunke.admin.framework.openapi.validate;

import com.yunke.admin.framework.openapi.token.DefaultOpenApiTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @className DefaultOpenApiValidateService
 * @description: 默认openapi验证接口实现类
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Service
public class DefaultOpenApiValidateService implements OpenApiValidateService{
    @Autowired
    private DefaultOpenApiTokenService defaultTokenService;

    @Override
    public void validate(OpenApiValidateParam openApiValidateParam) {
        this.validateToken(openApiValidateParam);
    }

    /**
     * 校验 token
     * @param openApiValidateParam
     */
    protected void validateToken(OpenApiValidateParam openApiValidateParam) {
        String tokenValue = defaultTokenService.getTokenValue(openApiValidateParam.getRequest());
        defaultTokenService.validateToken(tokenValue);
    }
}
