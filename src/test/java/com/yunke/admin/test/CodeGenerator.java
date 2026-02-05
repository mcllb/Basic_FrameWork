package com.yunke.admin.test;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeGenerator {

    //作者
    public static String author = "mcllb";
    //包名称
    public static String basePackagePath = "com.yunke.admin.modular.business.";
    //模块名称
    public static String moduleName = "repair";
    //模块描述
    public static String moduleComment = "报修信息管理";
    //权限前缀
    public static String permissionPrefix = "biz:repair-info";
    //访问前缀
    public static String requestMappingPrefix = "biz/repair-info/";
    //表名称
    public static String tableName = "biz_repair_info";
    //去除表前缀
    public static String TablePrefix = "biz_";

    public static void main(String[] args) {

        // 1. 创建代码生成器对象
        AutoGenerator mpg = new AutoGenerator();

        // ========== 2. 全局配置 (关键修正部分) ==========
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");

        // 正确设置输出目录（3.4.2版本的正确方式）
        gc.setOutputDir(projectPath + "/src/main/java");

        // 设置其他全局参数
        gc.setAuthor(author);
        gc.setOpen(false); // 生成后不打开输出目录
        gc.setFileOverride(true); // 覆盖已有文件
        gc.setServiceName("%sService"); // 去掉Service接口的I前缀
        gc.setIdType(IdType.ASSIGN_ID); // 主键策略为自增
        // gc.setSwagger2(true); // 如果项目引入了swagger，可以开启此注解

        mpg.setGlobalConfig(gc); // 将配置应用到生成器

        // ========== 3. 数据源配置 ==========
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://180.97.207.222:20008/operation-platform?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("dx@7306");
        mpg.setDataSource(dsc);

        // ========== 4. 包配置 ==========
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.yunke.admin.modular.business");
        pc.setModuleName(moduleName); // 你的模块名
        pc.setEntity("model.entity");
        pc.setMapper("mapper");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setController("controller");
        mpg.setPackageInfo(pc);

        // ========== 5. 自定义配置 ==========
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                String basePackage =  basePackagePath + moduleName;
                map.put("entityPackage", basePackage + ".model.entity");
                map.put("paramPackage", basePackage + ".model.param");
                map.put("voPackage", basePackage + ".model.vo");
                map.put("servicePackage", basePackage + ".service");
                map.put("serviceImplPackage", basePackage + ".service.impl");
                map.put("mapperPackage", basePackage + ".mapper");
                map.put("moduleComment", moduleComment);
                map.put("permissionPrefix", permissionPrefix);
                map.put("requestMappingPrefix", requestMappingPrefix);
                this.setMap(map);
            }
        };


        // 自定义输出配置：指定Mapper XML文件的生成目录
        List<FileOutConfig> focList = new ArrayList<>();

        // 1. entity类
        focList.add(new FileOutConfig("/templates/generation/entity.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 方法2：使用正确的包路径拼接
                // 注意：pc.getParent() 是 "com.yunke.admin.modular.business"
                // pc.getModuleName() 是 "customer"
                String parentPath = pc.getParent().replace(".", "/");  // com/yunke/admin/modular/business
                String modulePath = pc.getModuleName();                 // customer
                return projectPath + "/src/main/java/"
                        + parentPath + "/"
                        + "model/entity/"
                        + tableInfo.getEntityName() + ".java";
            }
        });

        // 2. AddParam类
        focList.add(new FileOutConfig("/templates/generation/addparam.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 方法2：使用正确的包路径拼接
                // 注意：pc.getParent() 是 "com.yunke.admin.modular.business"
                // pc.getModuleName() 是 "customer"
                String parentPath = pc.getParent().replace(".", "/");  // com/yunke/admin/modular/business
                String modulePath = pc.getModuleName();                 // customer
                return projectPath + "/src/main/java/"
                        + parentPath + "/"
                        + "model/param/"
                        + tableInfo.getEntityName() + "AddParam.java";
            }
        });

        // 3. EditParam类
        focList.add(new FileOutConfig("/templates/generation/editparam.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 方法2：使用正确的包路径拼接
                // 注意：pc.getParent() 是 "com.yunke.admin.modular.business"
                // pc.getModuleName() 是 "customer"
                String parentPath = pc.getParent().replace(".", "/");  // com/yunke/admin/modular/business
                String modulePath = pc.getModuleName();                 // customer
                return projectPath + "/src/main/java/"
                        + parentPath + "/"
                        + "model/param/"
                        + tableInfo.getEntityName() + "EditParam.java";
            }
        });

        // 2. QueryParam类
        focList.add(new FileOutConfig("/templates/generation/queryparam.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 方法2：使用正确的包路径拼接
                // 注意：pc.getParent() 是 "com.yunke.admin.modular.business"
                // pc.getModuleName() 是 "customer"
                String parentPath = pc.getParent().replace(".", "/");  // com/yunke/admin/modular/business
                String modulePath = pc.getModuleName();                 // customer
                return projectPath + "/src/main/java/"
                        + parentPath + "/"
                        + "model/param/"
                        + tableInfo.getEntityName() + "PageQueryParam.java";
            }
        });

        // 5. VO类
        focList.add(new FileOutConfig("/templates/generation/vo.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 方法2：使用正确的包路径拼接
                // 注意：pc.getParent() 是 "com.yunke.admin.modular.business"
                // pc.getModuleName() 是 "customer"
                String parentPath = pc.getParent().replace(".", "/");  // com/yunke/admin/modular/business
                String modulePath = pc.getModuleName();                 // customer
                return projectPath + "/src/main/java/"
                        + parentPath + "/"
                        + "model/vo/"
                        + tableInfo.getEntityName() + "VO.java";
            }
        });

        // 6. service类
        focList.add(new FileOutConfig("/templates/generation/service.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 方法2：使用正确的包路径拼接
                // 注意：pc.getParent() 是 "com.yunke.admin.modular.business"
                // pc.getModuleName() 是 "customer"
                String parentPath = pc.getParent().replace(".", "/");  // com/yunke/admin/modular/business
                String modulePath = pc.getModuleName();                 // customer
                return projectPath + "/src/main/java/"
                        + parentPath + "/"
                        + "service/"
                        + tableInfo.getEntityName() + "Service.java";
            }
        });

        // 7. serviceImpl类
        focList.add(new FileOutConfig("/templates/generation/serviceimpl.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 方法2：使用正确的包路径拼接
                // 注意：pc.getParent() 是 "com.yunke.admin.modular.business"
                // pc.getModuleName() 是 "customer"
                String parentPath = pc.getParent().replace(".", "/");  // com/yunke/admin/modular/business
                String modulePath = pc.getModuleName();                 // customer
                return projectPath + "/src/main/java/"
                        + parentPath + "/"
                        + "service/impl/"
                        + tableInfo.getEntityName() + "ServiceImpl.java";
            }
        });

        // 如果Controller也需要自定义，添加自定义配置
        focList.add(new FileOutConfig("/templates/generation/controller.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/src/main/java/"
                        + pc.getParent().replace(".", "/") + "/"
                        //+ pc.getModuleName() + "/"
                        + "controller/"
                        + tableInfo.getEntityName() + "Controller.java";
            }
        });

        // 如果Controller也需要自定义，添加自定义配置
        focList.add(new FileOutConfig("/templates/generation/xml.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/src/main/java/"
                        + pc.getParent().replace(".", "/") + "/"
                        //+ pc.getModuleName() + "/"
                        + "mapper/"
                        + tableInfo.getEntityName() + "Mapper.xml";
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // ========== 6. 模板配置 ==========
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setEntity(null);        // 禁用默认实体类
        //templateConfig.setMapper(null);        // 禁用默认Mapper
        templateConfig.setService(null);       // 禁用默认Service
        templateConfig.setServiceImpl(null);   // 禁用默认ServiceImpl
        templateConfig.setXml(null);           // 禁用默认XML
        templateConfig.setController(null);    // 禁用默认Controller，如果需要自定义也添加自定义配置
        mpg.setTemplate(templateConfig);

        // ========== 7. 策略配置 ==========
        StrategyConfig strategy = new StrategyConfig();
        // 表名生成策略：下划线转驼峰
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 列名生成策略：下划线转驼峰
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // 实体类使用Lombok
        strategy.setEntityLombokModel(true);
        // 生成@RestController控制器
        strategy.setRestControllerStyle(true);
        // 需要生成的表名，多个用逗号分隔
        strategy.setInclude(tableName);
        // 生成实体时去掉的表前缀
        strategy.setTablePrefix(TablePrefix);
        // 控制器映射路径使用连字符风格
        strategy.setControllerMappingHyphenStyle(true);
        // 可选：设置逻辑删除字段名（如果表中有此字段）
        // strategy.setLogicDeleteFieldName("deleted");
        // 可选：设置父类实体（如果有公共字段）
        // strategy.setSuperEntityClass("com.yunke.admin.common.BaseEntity");
        // strategy.setSuperEntityColumns("id", "create_time", "update_time");

        mpg.setStrategy(strategy);

        // ========== 8. 模板引擎 ==========
        mpg.setTemplateEngine(new VelocityTemplateEngine());

        // ========== 9. 执行生成 ==========
        mpg.execute();

        System.out.println("✅ 代码生成完成！📁");
    }
}