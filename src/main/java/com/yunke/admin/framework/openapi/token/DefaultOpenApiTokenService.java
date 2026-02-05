package com.yunke.admin.framework.openapi.token;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTValidator;
import com.yunke.admin.common.enums.CommonStatusEnum;
import com.yunke.admin.common.enums.OpenApiValidateExceptionEnum;
import com.yunke.admin.framework.cache.OpenapiTokenCache;
import com.yunke.admin.framework.config.OpenApiConfig;
import com.yunke.admin.framework.openapi.exception.OpenApiValidateException;
import com.yunke.admin.modular.openapi.model.entity.OpenapiCaller;
import com.yunke.admin.modular.openapi.service.OpenapiCallerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class DefaultOpenApiTokenService implements OpenApiTokenService {

    @Autowired
    private OpenapiCallerService openapiCallerService;
    @Autowired
    private OpenapiTokenCache tokenCache;
    @Autowired
    private OpenApiConfig openApiConfig;


    @Override
    public String createToken(CreateTokenParam createTokenParam) {
        OpenapiCaller caller = openapiCallerService.lambdaQuery()
                .eq(OpenapiCaller::getCallerKey, createTokenParam.getCallerKey())
                .one();
        //校验调用者是否存在
        if(null == caller){
            throw new OpenApiValidateException(OpenApiValidateExceptionEnum.CALLER_NOT_FOUND);
        }

        //校验安全密钥
        if(!SecureUtil.md5(caller.getSecretKey()).equals(createTokenParam.getSecretKey())){
            throw new OpenApiValidateException(OpenApiValidateExceptionEnum.CALLER_SECRET_ERROR);
        }

        //校验caller状态
        if(!CommonStatusEnum.ENABLE.getCode().equals(caller.getStatus())){
            throw new OpenApiValidateException(OpenApiValidateExceptionEnum.CALLER_STATUS_EXP);
        }

        //创建JWT token
        // 密钥
        byte[] key = caller.getSecretKey().getBytes();
        Date issuedAt = new Date();
        Date expiresAt = DateUtil.offsetSecond(issuedAt, openApiConfig.getTokenExpireTime()).toJdkDate();
        Map<String, Object> payloads = new HashMap<>();
        payloads.put("id",caller.getId());
        payloads.put("callerKey",caller.getCallerKey());
        try {
            String token = JWT.create()
                    .addPayloads(payloads)
                    .setKey(key)
                    .setIssuedAt(issuedAt)
                    .setExpiresAt(expiresAt)
                    .setNotBefore(issuedAt)
                    .sign();
            tokenCache.put(token,caller,openApiConfig.getTokenExpireTime());
            return token;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("生成token发生未知异常");
        }
    }

    @Override
    public String getTokenName() {
        return openApiConfig.getTokenName();
    }

    @Override
    public String getTokenValue(HttpServletRequest request) {
        return request.getHeader(getTokenName());
    }

    @Override
    public void validateToken(String token){
        if(StrUtil.isBlank(token)){
            throw new OpenApiValidateException(String.format("缺少header参数[%s]",openApiConfig.getTokenName()));
        }
        //从缓存中校验token
        OpenapiCaller caller = tokenCache.get(token);
        if(null == caller){
            throw new OpenApiValidateException(OpenApiValidateExceptionEnum.TOKEN_EXPIRED);
        }

        // 默认验证HS256的算法
        byte[] key = caller.getSecretKey().getBytes();
        JWT jwt = JWT.of(token).setKey(key);
        boolean verify = jwt.verify();
        if(!verify){
            throw new OpenApiValidateException(OpenApiValidateExceptionEnum.TOKEN_VALIDATE_FAIL);
        }
        //校验token callerKey
        String callerKey = (String) jwt.getPayload("callerKey");
        if(StrUtil.isBlank(callerKey) || !callerKey.equals(caller.getCallerKey())){
            throw new OpenApiValidateException(OpenApiValidateExceptionEnum.TOKEN_INVALID);
        }
        String callerId = (String) jwt.getPayload("id");
        if(!caller.getId().equals(callerId)){
            throw new OpenApiValidateException(OpenApiValidateExceptionEnum.TOKEN_INVALID);
        }

        //校验token有效期
        try {
            JWTValidator.of(token).validateDate();
        } catch (ValidateException e) {
            e.printStackTrace();
            throw new OpenApiValidateException(OpenApiValidateExceptionEnum.TOKEN_EXPIRED);
        }

    }
}
