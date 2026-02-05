package com.yunke.admin.framework.config;

import com.yunke.admin.framework.cache.*;
import com.yunke.admin.framework.datasource.MasterDsConfig;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
@AutoConfigureAfter({RedisTemplateConfig.class,ProjectConfig.class,JetCacheConfig.class, MasterDsConfig.class})
public class CacheConfig {

    @Bean
    public ParamConfigCache paramConfigCache(){
        return new ParamConfigCache();
    }

    @Bean
    public CaptchaCache captchaCache(){
        return new CaptchaCache();
    }

    @Bean
    public EnumDictCache enumDictCache(){
        return new EnumDictCache();
    }

    @Bean
    public DataDictCache dataDictCache(){
        return new DataDictCache();
    }

    @Bean
    public DeptCache deptCache(){
        return new DeptCache();
    }

    @Bean
    public UserCache userCache(){
        return new UserCache();
    }

    @Bean
    public MockLoginTokenCache mockLoginTokenCache(){
        return new MockLoginTokenCache();
    }

    //@Bean
    //public RegionCache regionCache(){
    //    return new RegionCache();
    //}

    @Bean
    public RolePermissionCache rolePermissionCache(){
        return new RolePermissionCache();
    }

    @Bean
    public UserRoleCache userRoleCache(){
        return new UserRoleCache();
    }

    @Bean
    public OpenapiTokenCache openapiTokenCache(){
        return new OpenapiTokenCache();
    }


}
