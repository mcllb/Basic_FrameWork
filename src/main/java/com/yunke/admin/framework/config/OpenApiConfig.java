package com.yunke.admin.framework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "yk.openapi")
@Data
public class OpenApiConfig {

    public static final String PREFIX = "yk.openapi";

    /**
     * token名称
     */
    private String tokenName = "YK-OpenApi-Token";
    /**
     * token过期时间，单位s
     */
    private int tokenExpireTime = 7200;

}
