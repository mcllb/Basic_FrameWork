package com.yunke.admin.modular.system.issue.enums;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.lang.Dict;
import com.yunke.admin.common.enums.AbstractDictEnum;
import com.yunke.admin.common.enums.EnumDictTypeConstant;
import com.yunke.admin.framework.core.annotion.EnumDict;
import lombok.Getter;

import java.util.Arrays;

@EnumDict(EnumDictTypeConstant.ISSUE_TYPE)
@Getter
public enum IssueTypeEnum implements AbstractDictEnum {

    /**
     * 模块功能异常
     */
    MODULE_FUNCTION_ERROR ("A", "模块功能异常"),

    /**
     * 操作体验
     */
    OPERATING_EXPERIENCE  ("B", "操作体验"),

    /**
     * UI界面
     */
    UI ("C", "UI界面"),

    /**
     * 系统安全问题
     */
    SYSTEM_SECURITY ("D", "系统安全问题"),

    /**
     * 系统性能问题
     */
    SYSTEM_PERFORMANCE ("E", "系统性能问题"),

    /**
     * 模块功能修改
     */
    MODULE_FUNCTION_MODIFY ("F", "模块功能修改"),

    /**
     * 新增模块功能
     */
    MODULE_FUNCTION_ADD ("G", "新增模块功能"),

    ;

    private final String code;

    private final String text;

    IssueTypeEnum(String code, String text) {
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
