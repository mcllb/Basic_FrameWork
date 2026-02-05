package com.yunke.admin.framework.cache.base;

/**
 * @className CacheConstant
 * @description: 缓存KEY与名称常量
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
public interface CacheConstant {

    /**
     * 登录验证码缓存key
     */
    String CACHE_NAME_CAPTCHA = "CAPTCHA:";
    String CACHE_TITLE_CAPTCHA = "登录验证码缓存";

    /**
     * 数据字典缓存key
     */
    String CACHE_NAME_DICT = "DICT:";
    String CACHE_TITLE_DICT = "数据字典缓存";

    /**
     * 组织机构缓存key
     */
    String CACHE_NAME_DEPT = "DEPT:";
    String CACHE_TITLE_DEPT = "组织机构缓存";

    /**
     * 枚举字典缓存key
     */
    String CACHE_NAME_ENUM = "ENUM:";
    String CACHE_TITLE_ENUM = "枚举字典缓存";

    /**
     * 模拟登陆token缓存key
     */
    String CACHE_NAME_MOCK_LOGIN_TOKEN = "MOCK_LOGIN_TOKEN:";
    String CACHE_TITLE_MOCK_LOGIN_TOKEN = "模拟登陆token缓存";

    /**
     * 参数配置缓存key
     */
    String CACHE_NAME_PARAM_CONFIG = "PARAM_CONFIG:";
    String CACHE_TITLE_PARAM_CONFIG = "参数配置缓存";

    /**
     * 行政区划缓存key
     */
    String CACHE_NAME_REGION = "REGION:";
    String CACHE_TITLE_REGION = "行政区划缓存";

    /**
     * 角色与权限缓存key
     */
    String CACHE_NAME_ROLE_PERMISSION = "ROLE_PERMISSION:";
    String CACHE_TITLE_ROLE_PERMISSION = "角色权限缓存";

    /**
     * 用户缓存key
     */
    String CACHE_NAME_USER = "USER:";
    String CACHE_TITLE_USER = "用户缓存";

    /**
     * 用户与角色缓存key
     */
    String CACHE_NAME_USER_ROLE = "USER_ROLE:";
    String CACHE_TITLE_USER_ROLE = "用户角色缓存";


}
