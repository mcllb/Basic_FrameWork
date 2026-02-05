package com.yunke.admin.common.enums;

import com.yunke.admin.common.constant.SysExpCodePrefixConstant;
import com.yunke.admin.framework.core.annotion.ExpEnumCode;
import com.yunke.admin.framework.core.util.ExpEnumCodeUtil;

@ExpEnumCode(SysExpCodePrefixConstant.CACHE_EXCEPTION_CODE)
public enum CacheExceptionEnum implements AbstractExceptionEnum {

    NOT_IS_ENUM_DICT(101, "不是枚举缓存"),

    ENUM_DICT_TYPE_REPEAT(102, "枚举字典类型重复");

    private final Integer code;

    private final String message;

    CacheExceptionEnum(Integer code, String message) {
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
