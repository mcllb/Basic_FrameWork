package com.yunke.admin.modular.api.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.yunke.admin.common.base.BaseController;
import com.yunke.admin.framework.core.response.ResponseData;
import com.yunke.admin.framework.core.response.SuccessResponseData;
import com.yunke.admin.framework.openapi.token.CreateTokenParam;
import com.yunke.admin.framework.openapi.token.OpenApiTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className OpenApiController
 * @description:
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@RestController
@RequestMapping("/api")
@Validated
public class OpenApiController extends BaseController {
    @Autowired
    private OpenApiTokenService openApiTokenService;

    @SaIgnore
    @PostMapping(value = "getToken")
    public ResponseData getToken(@Validated @RequestBody CreateTokenParam createTokenParam){
        return new SuccessResponseData(openApiTokenService.createToken(createTokenParam));
    }

}
