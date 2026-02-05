package com.yunke.admin.framework.config;

import cn.hutool.core.util.StrUtil;
import com.yunke.admin.framework.filter.CorsFilter;
import com.yunke.admin.framework.filter.SqlFilter;
import com.yunke.admin.framework.filter.TrimFilter;
import com.yunke.admin.framework.filter.XssFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import java.util.HashMap;
import java.util.Map;

/**
 * @className FilterConfig
 * @description: 过滤器配置
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Slf4j
@Configuration
public class FilterConfig {


    @Value("${xss.excludes}")
    private String xssExcludes;

    @Value("${xss.url-patterns}")
    private String xssUrlPatterns = "/*";

    @Value("${trim.excludes}")
    private String trimExcludes;

    @Value("${trim.url-patterns}")
    private String trimUrlPatterns = "/*";

    @Value("${sql.excludes}")
    private String sqlExcludes;

    @Value("${sql.url-patterns}")
    private String sqlUrlPatterns = "/*";

    @Value("${sa-token.token-name}")
    private String tokenName;

    @Bean
    public FilterRegistrationBean corsFilterRegistration(){
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new CorsFilter());
        registration.setName("corsFilter");
        registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE);
        Map<String, String> initParameters = new HashMap<String, String>();
        initParameters.put("tokenName", tokenName);
        registration.setInitParameters(initParameters);
        return registration;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @ConditionalOnProperty(value = "trim.enabled",havingValue = "true")
    @Bean
    public FilterRegistrationBean trimFilterRegistration() {
        log.debug("trimFilterRegistration ===========trimUrlPatterns={},trimExcludes={}",trimUrlPatterns,trimExcludes);
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new TrimFilter());
        registration.addUrlPatterns(StrUtil.splitToArray(trimUrlPatterns, ","));
        registration.setName("trimFilter");
        registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE + 1);
        Map<String, String> initParameters = new HashMap<String, String>();
        initParameters.put("excludes", trimExcludes);
        registration.setInitParameters(initParameters);
        return registration;
    }


    @SuppressWarnings({"rawtypes", "unchecked"})
    @ConditionalOnProperty(value = "xss.enabled",havingValue = "true")
    @Bean
    public FilterRegistrationBean xssFilterRegistration() {
        log.debug("xssFilterRegistration ===========xssUrlPatterns={},xssExcludes={}",xssUrlPatterns,xssExcludes);
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns(StrUtil.splitToArray(xssUrlPatterns, ","));
        registration.setName("xssFilter");
        registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE + 2);
        Map<String, String> initParameters = new HashMap<String, String>();
        initParameters.put("excludes", xssExcludes);
        registration.setInitParameters(initParameters);
        return registration;
    }

    @ConditionalOnProperty(value = "sql.enabled",havingValue = "true")
    @Bean
    public FilterRegistrationBean sqlFilterRegistration() {
        log.debug("sqlFilterRegistration ===========sqlUrlPatterns={},sqlExcludes={}",sqlUrlPatterns,sqlExcludes);
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new SqlFilter());
        registration.addUrlPatterns(StrUtil.splitToArray(sqlUrlPatterns, ","));
        registration.setName("sqlFilter");
        registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE + 3);
        Map<String, String> initParameters = new HashMap<String, String>();
        initParameters.put("excludes", sqlExcludes);
        registration.setInitParameters(initParameters);
        return registration;
    }


}
