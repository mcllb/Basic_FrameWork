package com.yunke.admin.modular.system.permission.enums;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.lang.Dict;
import com.yunke.admin.common.enums.AbstractDictEnum;
import com.yunke.admin.common.enums.EnumDictTypeConstant;
import com.yunke.admin.framework.core.annotion.EnumDict;

import java.util.Arrays;


@EnumDict(EnumDictTypeConstant.SYS_MENU_OPEN_TYPE)
public enum PermissionOpenTypeEnum implements AbstractDictEnum {

    /**
     * 内链
     */
    IFRAME("1", "内链"),

    /**
     * 外链
     */
    TATGET("2", "外链");

    private final String code;

    private final String text;

    PermissionOpenTypeEnum(String code, String text) {
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
        Arrays.stream(PermissionOpenTypeEnum.values()).forEach(permissionOpenTypeEnum -> {
            dict.set(permissionOpenTypeEnum.getCode(),permissionOpenTypeEnum.getText());
        });
        return dict;
    }

}
