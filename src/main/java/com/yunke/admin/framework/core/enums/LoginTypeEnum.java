package com.yunke.admin.framework.core.enums;


import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.lang.Dict;
import com.yunke.admin.common.enums.AbstractDictEnum;
import com.yunke.admin.common.enums.EnumDictTypeConstant;
import com.yunke.admin.framework.core.annotion.EnumDict;
import lombok.Getter;
import java.util.Arrays;


/**
 * @className LoginTypeEnum
 * @description: 登录类型枚举
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@EnumDict(EnumDictTypeConstant.SA_LOGIN_TYPE)
@Getter
public enum LoginTypeEnum implements AbstractDictEnum {


    /**
     * PC端
     */
    PC("PC", "PC端"),

    ;

    private final String code;

    private final String text;

    LoginTypeEnum(String code, String text) {
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
        Arrays.stream(LoginTypeEnum.values()).forEach(commonIfEnum -> {
            dict.set(commonIfEnum.getCode(),commonIfEnum.getText());
        });
        return dict;
    }


}
