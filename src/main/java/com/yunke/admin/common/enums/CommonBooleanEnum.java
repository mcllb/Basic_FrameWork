package com.yunke.admin.common.enums;


import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.lang.Dict;
import com.yunke.admin.framework.core.annotion.EnumDict;
import lombok.Getter;

import java.util.Arrays;


@EnumDict(EnumDictTypeConstant.COMMON_BOOLEAN)
public enum CommonBooleanEnum implements AbstractDictEnum {


    /**
     * 成功
     */
    SUCCESS("success", "成功"),

    /**
     * 失败
     */
    FAIL("fail", "失败"),


    ;

    private final String code;

    private final String text;

    CommonBooleanEnum(String code, String text) {
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
    public Dict getDict(){
        Dict dict = Dict.create();
        Arrays.stream(CommonBooleanEnum.values()).forEach(commonIfEnum -> {
            dict.set(commonIfEnum.getCode(),commonIfEnum.getText());
        });
        return dict;
    }


}
