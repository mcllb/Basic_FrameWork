package com.yunke.admin.common.constant;


/**
 * @className CommonConstant
 * @description: 通用常量
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
public interface CommonConstant {

    /**
     * id
     */
    String ID = "id";

    /**
     * 名称
     */
    String NAME = "name";

    /**
     * 编码
     */
    String CODE = "code";

    /**
     * 值
     */
    String VALUE = "value";

    /**
     * 用户代理
     */
    String USER_AGENT = "User-Agent";

    /**
     * 未知标识
     */
    String UNKNOWN = "Unknown";

    /**
     * 默认密码
     */
    String DEFAULT_PASSWORD = "123456";

    /**
     * 请求号在header中的唯一标识
     */
    String REQUEST_NO_HEADER_NAME = "Request-No";

    /**
     *  管理员账号ID
     */
    String ADMIN_ID = "admin";

    /**
     *  管理员账号
     */
    String ADMIN_ACCOUNT = "admin";

    /**
     *  管理员角色编码
     */
    String ADMIN_ROLE_CODE = "admin";

    /**
     *  redis key分隔符
     */
    String REDIS_KEY_SEPARATOR = ":";

    /**
     * 项目包名前缀
     */
    String BASE_PACKAGE = "com.yunke.admin";

    /**
     * 登录用户session缓存key
     */
    String LOGIN_USER_SESSION_KEY = "loginUser";

    /**
     * 微信登录用户session缓存key
     */
    String WX_LOGIN_USER_SESSION_KEY = "wxLoginUser";

}
