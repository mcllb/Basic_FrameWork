package com.yunke.admin.modular.system.config.enums;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.lang.Dict;
import com.yunke.admin.common.enums.AbstractDictEnum;
import com.yunke.admin.common.enums.EnumDictTypeConstant;
import com.yunke.admin.framework.core.annotion.EnumDict;

import java.util.Arrays;

/**
 * @className ParamConfigDataTypeEnum
 * @description: 系统参数数据类型枚举
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@EnumDict(EnumDictTypeConstant.SYS_CONFIG_DATA_TYPE)
public enum ParamConfigDataTypeEnum implements AbstractDictEnum {

    STRING("java.lang.String","字符串"),

    INT("java.lang.Integer","整型"),

    FLOAT("java.lang.Float","浮点型"),

    BOOLEAN("java.lang.Boolean","布尔型"),

    ;

    private String code;

    private String text;

    ParamConfigDataTypeEnum(String code,String text){
        this.code = code;
        this.text = text;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getDictType() {
        String dictType = AnnotationUtil.getAnnotationValue(this.getClass(), EnumDict.class);
        return dictType;
    }

    @Override
    public Dict getDict() {
        Dict dict = Dict.create();
        Arrays.stream(ParamConfigDataTypeEnum.values()).forEach(configDataTypeEnum -> {
            dict.set(configDataTypeEnum.getCode(),configDataTypeEnum.getText());
        });
        return dict;
    }
}
