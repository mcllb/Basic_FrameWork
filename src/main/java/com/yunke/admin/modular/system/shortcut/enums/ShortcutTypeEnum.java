package com.yunke.admin.modular.system.shortcut.enums;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.lang.Dict;
import com.yunke.admin.common.enums.AbstractDictEnum;
import com.yunke.admin.common.enums.EnumDictTypeConstant;
import com.yunke.admin.framework.core.annotion.EnumDict;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@EnumDict(EnumDictTypeConstant.SYS_SHORTCUT_TYPE)
@AllArgsConstructor
public enum ShortcutTypeEnum implements AbstractDictEnum {

    /**
     * 系统菜单
     */
    SYSTEM("system", "系统菜单"),

    /**
     * 自定义
     */
    CUSTOM("custom", "自定义"),

    ;

    private final String code;

    private final String text;

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
