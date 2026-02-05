package com.yunke.admin.common.enums;

import com.yunke.admin.common.constant.SysExpCodePrefixConstant;
import com.yunke.admin.framework.core.annotion.ExpEnumCode;
import com.yunke.admin.framework.core.util.ExpEnumCodeUtil;

/**
 * @className AuthExceptionEnum
 * @description: 认证异常枚举
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@ExpEnumCode(SysExpCodePrefixConstant.AUTH_EXCEPTION_CODE)
public enum AuthExceptionEnum implements AbstractExceptionEnum {

    /**
     * 验证码过期
     */
    VER_CODE_EXPIRE(101,"验证码已过期"),

    /**
     * 验证码错误
     */
    VER_CODE_ERROR(102, "验证码错误"),

    /**
     * 校验验证码发生异常
     */
    CHECK_VER_CODE_ERROR(103, "校验验证码发生异常"),

    /**
     * 验证码校验未通过
     */
    VER_CODE_CHECK_FAIL(104, "验证码校验未通过"),

    /**
     * 缺少验证码参数
     */
    VER_CODE_PARAM_LOSS(105, "缺少验证码参数"),

    /**
     * 账号不存在
     */
    ACCOUNT_NOT_EXIST(106, "账号不存在"),

    /**
     * 账号状态异常
     */
    ACCOUNT_STATUS_EXCEPTION(107, "账号状态异常"),

    /**
     * 密码错误
     */
    PWD_ERROR(108, "密码错误"),

    /**
     * 请求token错误
     */
    REQUEST_TOKEN_ERROR(109, "请求token错误"),

    /**
     * 用户授权异常
     */
    USER_AUTH_ERROR(110, "用户账号授权异常"),

    /**
     * 模拟登陆账号与token不匹配
     */
    ACCOUNT_TOKEN_UN_MATCH(111,"模拟登陆账号与token不匹配"),

    /**
     * 模拟登陆未启用
     */
    MOCK_LOGIN_DISABLE(112,"模拟登陆未启用"),

    /**
     * 账号模拟登陆未启用
     */
    ACCOUNT_MOCK_LOGIN_DISABLE(113,"此账号模拟登陆未启用"),

    /**
     * 微信用户未注册
     */
    WX_OPID_NOT_EXIST(114, "微信用户未注册"),

    /**
     * 系统没有此用户
     */
    WX_USER_NOT_EXIST(115, "系统没有此用户"),

    /**
     * 非微信登录
     */
    NOT_WX_LOGIN(116, "非微信登录禁止访问"),

    /**
     * 非系统用户微信登录
     */
    NOT_SYS_WX_LOGIN(117, "非系统用户微信登录禁止访问"),

    /**
     * 非企业用户微信登录
     */
    NOT_CUST_WX_LOGIN(118, "非企业用户微信登录禁止访问"),

    ;

    private final Integer code;

    private final String message;

    AuthExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return ExpEnumCodeUtil.getExpEnumCode(this.getClass(),code);
    }

    @Override
    public String getMessage() {
        return message;
    }

}
