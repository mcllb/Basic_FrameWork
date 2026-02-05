package com.yunke.admin.modular.system.user.enums;

import com.yunke.admin.common.constant.SysExpCodePrefixConstant;
import com.yunke.admin.common.enums.AbstractExceptionEnum;
import com.yunke.admin.framework.core.annotion.ExpEnumCode;
import com.yunke.admin.framework.core.util.ExpEnumCodeUtil;

@ExpEnumCode(SysExpCodePrefixConstant.USER_EXCEPTION_CODE)
public enum UserExceptionEnum implements AbstractExceptionEnum {

    /**
     * 用户账号重复
     */
    USER_ACCOUNT_REPEAT(101,"用户账号重复"),

    /**
     * 手机号重复
     */
    USER_PHONE_REPEAT(102,"手机号重复"),

    /**
     * 邮箱重复
     */
    USER_EMAIL_REPEAT(103,"邮箱重复"),

    /**
     * 新密码原密码不可相同
     */
    USER_OLDPWD_NEWPWD_SAME(104,"新密码原密码不可相同"),

    /**
     * 原密码错误
     */
    USER_OLDPWD_ERROR(105,"原密码错误"),

    /**
     * 不能删除管理员账号
     */
    CAN_NOT_DELETE_ADMIN(106,"不能删除管理员账号"),

    ;

    private final Integer code;

    private final String message;

    UserExceptionEnum(Integer code,String message){
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
