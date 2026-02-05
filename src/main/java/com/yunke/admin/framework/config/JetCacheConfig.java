package com.yunke.admin.framework.config;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.yunke.admin.common.constant.CommonConstant;
import com.yunke.admin.framework.datasource.MasterDsConfig;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(MasterDsConfig.class)
@EnableMethodCache(basePackages = CommonConstant.BASE_PACKAGE)
public class JetCacheConfig {

}
