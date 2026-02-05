package com.yunke.admin.common.enums;


import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.lang.Dict;
import com.yunke.admin.framework.core.annotion.EnumDict;

import java.util.Arrays;


@EnumDict(EnumDictTypeConstant.COMMON_STATUS)
public enum CommonStatusEnum implements AbstractDictEnum {


    /**
     * 正常
     */
    ENABLE("1", "正常"),

    /**
     * 停用
     */
    DISABLE("0", "停用"),


    ;

    private final String code;

    private final String text;

    CommonStatusEnum(String code, String text) {
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
        Arrays.stream(CommonStatusEnum.values()).forEach(commonStatusEnum -> {
            dict.set(commonStatusEnum.getCode(),commonStatusEnum.getText());
        });
        return dict;
    }


}
