package com.yunke.admin.modular.system.issue.enums;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.lang.Dict;
import com.yunke.admin.common.enums.AbstractDictEnum;
import com.yunke.admin.common.enums.EnumDictTypeConstant;
import com.yunke.admin.framework.core.annotion.EnumDict;
import lombok.Getter;

import java.util.Arrays;

@EnumDict(EnumDictTypeConstant.ISSUE_FROM)
@Getter
public enum IssueFromEnum implements AbstractDictEnum {

    /**
     * 用户反馈
     */
    CUSTOMER ("customer", "用户反馈"),

    /**
     * 自测发现
     */
    SELF ("self", "自测发现"),

    ;

    private final String code;

    private final String text;

    IssueFromEnum(String code, String text) {
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
