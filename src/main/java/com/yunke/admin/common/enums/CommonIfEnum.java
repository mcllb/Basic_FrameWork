package com.yunke.admin.common.enums;


import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.lang.Dict;
import com.yunke.admin.framework.core.annotion.EnumDict;
import lombok.Getter;

import java.util.Arrays;


@EnumDict(EnumDictTypeConstant.COMMON_IF)
public enum CommonIfEnum implements AbstractDictEnum {


    /**
     * 是
     */
    YES("1", "是"),

    /**
     * 否
     */
    NO("0", "否"),


    ;

    private final String code;

    private final String text;

    CommonIfEnum(String code, String text) {
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
        Arrays.stream(CommonIfEnum.values()).forEach(commonIfEnum -> {
            dict.set(commonIfEnum.getCode(),commonIfEnum.getText());
        });
        return dict;
    }


}
