package com.yunke.admin.framework.openapi.token;

import javax.servlet.http.HttpServletRequest;

/**
 * @className OpenApiTokenService
 * @description: openapi token接口
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
public interface OpenApiTokenService {

    /**
     * 创建token
     * @param createTokenParam
     * @return
     */
    String createToken(CreateTokenParam createTokenParam);

    /**
     * 获取token名称
     * @return
     */
    String getTokenName();

    /**
     * 获取token值
     * @param request
     * @return
     */
    String getTokenValue(HttpServletRequest request);

    /**
     * 校验token
     * @param token
     */
    void validateToken(String token) throws RuntimeException;

}
