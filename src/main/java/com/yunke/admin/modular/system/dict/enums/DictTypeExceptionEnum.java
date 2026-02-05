package com.yunke.admin.modular.system.dict.enums;

import com.yunke.admin.common.constant.SysExpCodePrefixConstant;
import com.yunke.admin.common.enums.AbstractExceptionEnum;
import com.yunke.admin.framework.core.annotion.ExpEnumCode;
import com.yunke.admin.framework.core.util.ExpEnumCodeUtil;

/**
 * @className DictTypeExceptionEnum
 * @description: 系统字典类型异常枚举
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@ExpEnumCode(SysExpCodePrefixConstant.DICT_TYPE_EXCEPTION_CODE)
public enum DictTypeExceptionEnum implements AbstractExceptionEnum {

    /**
     * 字典类型编码重复
     */
    DICT_TYPE_CODE_REPEAT(101, "字典类型编码重复"),

    /**
     * 字典类型名称重复
     */
    DICT_TYPE_NAME_REPEAT(102, "字典类型名称重复");

    ;

    DictTypeExceptionEnum(Integer code,String message){
        this.code = code;
        this.message = message;
    }

    private final Integer code;

    private final String message;

    @Override
    public Integer getCode() {
        return ExpEnumCodeUtil.getExpEnumCode(this.getClass(),code);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
