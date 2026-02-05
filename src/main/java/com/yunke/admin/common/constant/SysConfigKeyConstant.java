package com.yunke.admin.common.constant;

/**
 * @className SysConfigKeyConstant
 * @description: 配置属性key常量，值对应sys_config表中的config_key
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
public interface SysConfigKeyConstant {

    /**
     * 系统名称
     */
    String SYSTEM_NAME = "system.name";

    /**
     * 系统用户默认密码
     */
    String SYSTEM_DEFAULT_PASSWORD = "system.default-password";

    /**
     * 登陆验证码类型
     */
    String SYSTEM_CAPTCHA_TYPE = "system.captcha.type";

    /**
     * 登陆验证码开关
     */
    String SYSTEM_CAPTCHA_ENABLE = "system.captcha.enable";

    /**
     * 系统版本
     */
    String SYSTEM_VERSION = "system.version";

    /**
     * 网站署名
     */
    String SYSTEM_COPYRIGHT = "system.copyright";

    /**
     * 系统日志保留天数
     */
    String SYSTEM_LOG_RETENTION_DAYS = "system.logretentiondays";

}
