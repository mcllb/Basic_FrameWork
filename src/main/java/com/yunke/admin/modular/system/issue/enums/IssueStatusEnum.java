package com.yunke.admin.modular.system.issue.enums;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.lang.Dict;
import com.yunke.admin.common.enums.AbstractDictEnum;
import com.yunke.admin.common.enums.EnumDictTypeConstant;
import com.yunke.admin.framework.core.annotion.EnumDict;
import lombok.Getter;

import java.util.Arrays;

@EnumDict(EnumDictTypeConstant.ISSUE_STATUS)
@Getter
public enum IssueStatusEnum implements AbstractDictEnum {

    /**
     * 打开
     */
    OPEN ("open", "打开"),

    /**
     * 修复中
     */
    REPAIRING ("repairing", "修复中"),

    /**
     * 已修复
     */
    REPAIRED ("repaired", "已修复"),

    /**
     * 关闭
     */
    CLOSE ("close", "关闭"),

    /**
     * 重新打开
     */
    REOPEN ("reopen", "重新打开"),

    ;

    private final String code;

    private final String text;

    IssueStatusEnum(String code, String text) {
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
