package com.yunke.admin.framework.datasource;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.github.yulichang.interceptor.MPJInterceptor;
import com.yunke.admin.framework.config.DruidConfig;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import javax.sql.DataSource;

/**
 * @className MasterDsConfig
 * @description: Mybatis主数据源配置
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Configuration
@AutoConfigureAfter({DruidConfig.class})
@MapperScan(basePackages = {"com.yunke.admin.modular.system.**.mapper",
        "com.yunke.admin.modular.monitor.**.mapper",
        "com.yunke.admin.modular.openapi.**.mapper",
        "com.yunke.admin.modular.business.**.mapper"},
        sqlSessionFactoryRef = "sqlSessionFactory",
        sqlSessionTemplateRef = "sqlSessionTemplate")
public class MasterDsConfig {

    public final static String[] MAPPER_BASE_PACKAGES = {
        "com.yunke.admin.modular.system.**.mapper",
        "com.yunke.admin.modular.monitor.**.mapper",
        "com.yunke.admin.modular.openapi.**.mapper",
        "com.yunke.admin.modular.business.**.mapper"
    };

    public final static String[] MAPPER_XML_LOCATIONS = {
        "classpath:com/yunke/admin/modular/system/**/mapper/*Mapper.xml",
        "classpath:com/yunke/admin/modular/monitor/**/mapper/*Mapper.xml",
        "classpath:com/yunke/admin/modular/openapi/**/mapper/*Mapper.xml",
        "classpath:com/yunke/admin/modular/business/**/mapper/*Mapper.xml"
    };

    @Autowired
    @Qualifier("masterDs")
    private DataSource masterDs;
    @Autowired
    @Qualifier("mysqlMybatisPlusInterceptor")
    private MybatisPlusInterceptor mybatisPlusInterceptor;
    @Autowired
    private MPJInterceptor mpjInterceptor;
    @Autowired
    @Qualifier("globalConfig")
    private GlobalConfig globalConfig;

    @Primary
    @Bean(name="sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        mybatisSqlSessionFactoryBean.setDataSource(masterDs);
        // 设置全局配置
        mybatisSqlSessionFactoryBean.setGlobalConfig(globalConfig);
        //指定mapper xml目录
        Resource[] resources = {};
        for(String location : MAPPER_XML_LOCATIONS) {
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resource = resolver.getResources(location);
            ArrayUtil.append(resources,resource);
        }
        mybatisSqlSessionFactoryBean.setMapperLocations(resources);
        // 设置分页插件、mpj
        Interceptor[] plugins = {mybatisPlusInterceptor,mpjInterceptor};
        mybatisSqlSessionFactoryBean.setPlugins(plugins);
        return mybatisSqlSessionFactoryBean.getObject();
    }

    @Primary
    @Bean(name="sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory());
        return template;
    }

    @Primary
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(){
        return new DataSourceTransactionManager(masterDs);
    }


}


