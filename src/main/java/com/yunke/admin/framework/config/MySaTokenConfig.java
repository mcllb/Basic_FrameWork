package com.yunke.admin.framework.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MySaTokenConfig implements WebMvcConfigurer {

    // 注册Sa-Token的注解拦截器，打开注解式鉴权功能
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册注解拦截器，并排除不需要注解鉴权的接口地址 (与登录拦截器无关)
        registry.addInterceptor(new SaInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/captcha")
                .excludePathPatterns("/common/**")
                .excludePathPatterns("/auth/login")
                .excludePathPatterns("/auth/wxlogin")
                .excludePathPatterns("/auth/wxRegisrer")

                // 模拟登陆接口
                .excludePathPatterns("/auth/mocklogin")
                .excludePathPatterns("/mockLoginToken")

                // 测试
                .excludePathPatterns("/test**")
                .excludePathPatterns("/test/**")

                //druid start
                .excludePathPatterns("/druid/**")
                //druid end

                // 附件组件 start
                .excludePathPatterns("/sys/file/download/*")
                .excludePathPatterns("/sys/file/previewImage/*")
                .excludePathPatterns("/sys/file/previewPdf/*")
                .excludePathPatterns("/sys/file/previewFile/*")
                // 附件组件 end

                //静态资源
                .excludePathPatterns("/static/**")
                //预览文件
                .excludePathPatterns("/upload/**")
                //公共资源
                .excludePathPatterns("/public/**")
                //api接口
                .excludePathPatterns("/api/**")
                //knife4j
                .excludePathPatterns("/doc.html")
                .excludePathPatterns("/webjars/**")

        ;

    }

}
