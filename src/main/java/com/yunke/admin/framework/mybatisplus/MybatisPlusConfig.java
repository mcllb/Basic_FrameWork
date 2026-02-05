package com.yunke.admin.framework.mybatisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.github.yulichang.injector.MPJSqlInjector;
import com.yunke.admin.framework.mybatisplus.injector.GeneralMybatisPlusSqlInjector;
import com.yunke.admin.framework.mybatisplus.interceptor.CustomInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @className MybatisPlusConfig
 * @description: MP配置类
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Configuration
public class MybatisPlusConfig {

    @Autowired
    private MetaObjectHandler metaObjectHandler;
    @Autowired
    private CustomIdGenerator customIdGenerator;


    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new CustomMetaObjectHandler();
    }

    /**
     * mysql分页插件
     */
    @Bean(name = "mysqlMybatisPlusInterceptor")
    public MybatisPlusInterceptor mysqlMybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        interceptor.addInnerInterceptor(new CustomInterceptor());
        return interceptor;
    }

    /**
     * sqlserver分页插件
     */
    @Bean(name = "sqlserverMybatisPlusInterceptor")
    public MybatisPlusInterceptor sqlserverMybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.SQL_SERVER));
        return interceptor;
    }

    /**
     * sqlserver2008分页插件
     */
    @Bean(name = "sqlserver2008PaginationInterceptor")
    public PaginationInterceptor sqlserver2008PaginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType(DbType.SQL_SERVER.getDb());
        //设置数据方言 sqlserver2008及以下版本用SQLServer2005Dialect
        page.setDialectClazz("com.baomidou.mybatisplus.extension.plugins.pagination.dialects.SQLServer2005Dialect");
        return page;
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.setUseDeprecatedExecutor(false);
    }



    @Bean
    public MPJSqlInjector mpjSqlInjector() {
        return new GeneralMybatisPlusSqlInjector();
    }

    /**
     * @description: MP全局配置
     * <p></p>
     * @param
     * @return com.baomidou.mybatisplus.core.config.GlobalConfig
     * @auth: tianlei
     * @date: 2026/1/16 10:57
     */
    @Bean("globalConfig")
    public GlobalConfig globalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setIdentifierGenerator(customIdGenerator);
        // 启用字段自动填充
        globalConfig.setMetaObjectHandler(metaObjectHandler);
        // MPJ连表插件
        globalConfig.setSqlInjector(mpjSqlInjector());
        return globalConfig;
    }



}
