package com.yunke.admin.modular.system.issue.enums;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.lang.Dict;
import com.yunke.admin.common.enums.AbstractDictEnum;
import com.yunke.admin.common.enums.EnumDictTypeConstant;
import com.yunke.admin.framework.core.annotion.EnumDict;
import lombok.Getter;

import java.util.Arrays;

@EnumDict(EnumDictTypeConstant.ISSUE_LEVEL)
@Getter
public enum IssueLevelEnum implements AbstractDictEnum {

    /**
     * 无
     */
    L0 ("0", "无"),

    /**
     * 微小
     */
    L4 ("4", "微小"),

    /**
     * 一般
     */
    L3 ("3", "一般"),

    /**
     * 严重
     */
    L2 ("2", "严重"),

    /**
     * 致命
     */
    L1 ("1", "致命"),

    ;

    private final String code;

    private final String text;

    IssueLevelEnum(String code, String text) {
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
        Arrays.stream(this.values()).forEach(value -> {
            dict.set(value.getCode(),value.getText());
        });
        return dict;
    }
}
