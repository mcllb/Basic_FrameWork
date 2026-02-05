package com.yunke.admin.modular.system.dict.enums;

import com.yunke.admin.common.constant.SysExpCodePrefixConstant;
import com.yunke.admin.common.enums.AbstractExceptionEnum;
import com.yunke.admin.framework.core.annotion.ExpEnumCode;
import com.yunke.admin.framework.core.util.ExpEnumCodeUtil;

/**
 * @className DictDataExceptionEnum
 * @description: 系统字典值异常枚举
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@ExpEnumCode(SysExpCodePrefixConstant.DICT_DATA_EXCEPTION_CODE)
public enum DictDataExceptionEnum implements AbstractExceptionEnum {

    /**
     * 字典值编码重复
     */
    DICT_TYPE_CODE_REPEAT(101, "字典值编码重复"),

    /**
     * 字典值重复
     */
    DICT_TYPE_VALUE_REPEAT(102, "字典值重复");

    ;

    DictDataExceptionEnum(Integer code, String message){
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
