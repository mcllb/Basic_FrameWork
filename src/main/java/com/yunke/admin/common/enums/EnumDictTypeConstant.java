package com.yunke.admin.common.enums;

/**
 * @className EnumDictTypeConstant
 * @description: 枚举字典类型常量
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
public enum EnumDictTypeConstant {

    /**
     * 通用状态
     */
    COMMON_STATUS,

    /**
     * 通用是否
     */
    COMMON_IF,

    /**
     * 通用布尔
     */
    COMMON_BOOLEAN,

    /**
     * 权限类型
     */
    SYS_PERMISSION_TYPE,

    /**
     * 菜单打开类型
     */
    SYS_MENU_OPEN_TYPE,

    /**
     * 用户性别
     */
    SYS_USER_SEX,

    /**
     * 部门角色
     */
    SYS_DEPT_ROLE,

    /**
     * 用户模拟登陆
     */
    SYS_USER_MOCK_LOGIN,

    /**
     * 系统参数配置类型
     */
    SYS_CONFIG_TYPE,

    /**
     * 系统参数数据类型
     */
    SYS_CONFIG_DATA_TYPE,

    /**
     * 日志状态枚举
     */
    LOG_STATUS,

    /**
     * 登录日志访问类型枚举
     */
    VIS_LOG_TYPE,

    /**
     * 操作日志操作类型枚举
     */
    OP_LOG_OP_TYPE,

    /**
     * 缺陷状态
     */
    ISSUE_STATUS,

    /**
     * 缺陷来源
     */
    ISSUE_FROM,

    /**
     * 缺陷等级
     */
    ISSUE_LEVEL,

    /**
     * 缺陷类型
     */
    ISSUE_TYPE,

    /**
     * 缺陷优先级
     */
    ISSUE_PRIORITY,

    /**
     * 行政区划级别
     */
    SYS_REGION_LEVEL,
    /**
     * 快捷方式类型
     */
    SYS_SHORTCUT_TYPE,

    /**
     * 登录类型
     */
    SA_LOGIN_TYPE,

    /**
     * 登录设备类型
     */
    SA_LOGIN_DEVICE_TYPE,

    /********************************** 以下为业务中用的到的枚举 ***********************************/

    /**
     * 短信发送类型
     */
    SHORT_MESSAGE_SEND_TYPE,
    /**
     * 预警事件推送状态
     */
    WARN_PUSH_STATUS,
    /**
     * 预警事件处置状态
     */
    WARN_RESULT_STATUS;



}
