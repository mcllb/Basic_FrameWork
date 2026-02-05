package com.yunke.admin.common.constant;

/**
 * @className SysExpCodePrefixConstant
 * @description: 系统异常状态码前缀
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
public interface SysExpCodePrefixConstant {

    /**
     * 认证异常
     */
    int AUTH_EXCEPTION_CODE = 10;

    /**
     * sa-token异常
     */
    int SATOKEN_EXCEPTION_CODE = 11;

    /**
     * 字典值异常
     */
    int DICT_DATA_EXCEPTION_CODE = 12;

    /**
     * 字典类型异常
     */
    int DICT_TYPE_EXCEPTION_CODE = 13;

    /**
     * 部门管理异常
     */
    int DEPT_EXCEPTION_CODE = 14;

    /**
     * 系统配置异常
     */
    int PARAM_CONFIG_EXCEPTION_CODE = 15;

    /**
     * 菜单权限管理异常
     */
    int PERMISSION_EXCEPTION_CODE = 16;

    /**
     * 角色管理异常
     */
    int ROLE_EXCEPTION_CODE = 17;

    /**
     * 用户管理异常
     */
    int USER_EXCEPTION_CODE = 18;

    /**
     * 缓存异常
     */
    int CACHE_EXCEPTION_CODE = 19;

    /**
     * open-api异常
     */
    int OPENAPI_EXCEPTION_CODE = 20;

    /**
     * 其他异常
     */
    int OTHER_EXCEPTION_CODE = 21;

}
