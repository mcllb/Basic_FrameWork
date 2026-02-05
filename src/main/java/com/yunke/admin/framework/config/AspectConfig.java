package com.yunke.admin.framework.config;

import com.yunke.admin.framework.aspect.LogLoginAspect;
import com.yunke.admin.framework.aspect.LogOperAspect;
import com.yunke.admin.framework.aspect.OpenApiAspect;
import com.yunke.admin.framework.aspect.RepeatSubmitAspect;
import com.yunke.admin.framework.service.RepeatSubmitService;
import com.yunke.admin.framework.service.impl.DefaultRepeatSubmitService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AspectConfig {

    @Bean
    public LogOperAspect logOperAspect(){
        return new LogOperAspect();
    }

    @Bean
    public LogLoginAspect logLoginAspect(){
        return new LogLoginAspect();
    }

    @Bean
    public RepeatSubmitAspect repeatSubmitAspect(){
        return new RepeatSubmitAspect();
    }

    @Primary
    @Bean
    public RepeatSubmitService repeatSubmitService(){
        return new DefaultRepeatSubmitService();
    }

    @Bean
    public OpenApiAspect openApiAspect(){
        return new OpenApiAspect();
    }

}
