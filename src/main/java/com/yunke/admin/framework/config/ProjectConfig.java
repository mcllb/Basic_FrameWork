package com.yunke.admin.framework.config;

import cn.hutool.core.io.FileUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

@Slf4j
@Component
@ConfigurationProperties(prefix = "project")
@Data
public class ProjectConfig {

    public static final String PREFIX = "project";

    /**
     * 项目名称
     */
    private String name;

    /**
     * 版本号
     */
    private String version;

    /**
     * 默认用户密码
     */
    private String defaultPassword;

    /**
     * 验证码开关
     */
    private boolean captchaEnable;

    /**
     * 验证码类型
     */
    private String captchaType;

    /**
     * 网站署名
     */
    private String copyRight;

    /**
     * 附件上传路径
     */
    private String uploadDir;

    /**
     * redis key前缀
     */
    private String redisKeyPrefix;

    /**
     * 是否记录mybatis
     */
    private boolean mybatisLog;
    /**
     * 是否初始化缓存
     */
    private boolean initCache;

    /**
     * 是否启用管理员角色
     * 启用后admin账号登录时需要授权角色，未启用时则拥有所有权限
     */
    private boolean enableAdminRole;

    /**
     * 启用模拟登陆
     */
    private boolean enableMockLogin;

    /**
     * 公共静态资源路径
     */
    private String publicResourceDir;

    /**
     * 雪花id workerId
     */
    private long workerId = 1;
    /**
     * 雪花id datacenterId
     */
    private long datacenterId = 1;

    @PostConstruct
    public void init(){
        String uploadDir = this.getUploadDir();
        File file = FileUtil.file(uploadDir);
        FileUtil.exist(file);
        if(!FileUtil.exist(file)){
            file.mkdirs();
        }
        log.info("uploadDir：{}",uploadDir);
    }


}
