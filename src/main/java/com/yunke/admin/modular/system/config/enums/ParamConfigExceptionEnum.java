package com.yunke.admin.modular.system.config.enums;

import com.yunke.admin.common.constant.SysExpCodePrefixConstant;
import com.yunke.admin.common.enums.AbstractExceptionEnum;
import com.yunke.admin.framework.core.annotion.ExpEnumCode;
import com.yunke.admin.framework.core.util.ExpEnumCodeUtil;

/**
 * @className ParamConfigExceptionEnum
 * @description: 系统配置异常枚举
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@ExpEnumCode(SysExpCodePrefixConstant.PARAM_CONFIG_EXCEPTION_CODE)
public enum ParamConfigExceptionEnum implements AbstractExceptionEnum {

    /**
     * 参数名称重复
     */
    CONFIG_NAME_REPEAT(101, "参数名称重复"),

    /**
     * 参数键名重复
     */
    CONFIG_KEY_REPEAT(102, "参数键名重复"),

    ;

    private final Integer code;

    private final String message;

    ParamConfigExceptionEnum(Integer code, String message) {
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
