package com.yunke.admin.modular.system.permission.enums;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.lang.Dict;
import com.yunke.admin.common.enums.AbstractDictEnum;
import com.yunke.admin.common.enums.EnumDictTypeConstant;
import com.yunke.admin.framework.core.annotion.EnumDict;
import lombok.Getter;

import java.util.Arrays;


@EnumDict(EnumDictTypeConstant.SYS_PERMISSION_TYPE)
@Getter
public enum PermissionTypeEnum implements AbstractDictEnum {

    /**
     * 目录
     */
    DIR("0", "目录"),

    /**
     * 菜单
     */
    MENU("1", "菜单"),

    /**
     * 按钮
     */
    BTN("2", "按钮");


    private final String code;

    private final String text;

    PermissionTypeEnum(String code, String text) {
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
        Arrays.stream(PermissionTypeEnum.values()).forEach(permissionTypeEnum -> {
            dict.set(permissionTypeEnum.getCode(),permissionTypeEnum.getText());
        });
        return dict;
    }

}
