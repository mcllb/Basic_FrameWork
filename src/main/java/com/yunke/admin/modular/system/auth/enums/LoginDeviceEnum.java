package com.yunke.admin.modular.system.auth.enums;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.lang.Dict;
import com.yunke.admin.common.enums.AbstractDictEnum;
import com.yunke.admin.common.enums.EnumDictTypeConstant;
import com.yunke.admin.framework.core.annotion.EnumDict;

import java.util.Arrays;

/**
  * @className LoginDeviceEnum
  * @description 登录设备类型
  * <p></p>
  * @version 1.0
  * @author tianlei
  * @date 2026/1/22
  */
@EnumDict(EnumDictTypeConstant.SA_LOGIN_DEVICE_TYPE)
public enum LoginDeviceEnum implements AbstractDictEnum {

    /**
     * PC端
     */
    PC ("pc", "PC端"),

    /**
     * 微信端
     */
    WX("wx", "微信端"),

    ;

    private final String code;

    private final String text;

    LoginDeviceEnum(String code, String text) {
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
