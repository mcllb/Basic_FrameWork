package com.yunke.admin.modular.system.user.enums;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.lang.Dict;
import com.yunke.admin.common.enums.AbstractDictEnum;
import com.yunke.admin.common.enums.EnumDictTypeConstant;
import com.yunke.admin.framework.core.annotion.EnumDict;
import lombok.Getter;

import java.util.Arrays;

@EnumDict(EnumDictTypeConstant.SYS_USER_MOCK_LOGIN)
@Getter
public enum UserMockLoginEnum implements AbstractDictEnum {

    /**
     * 启用
     */
    ENABLE("1", "启用"),

    /**
     * 禁用
     */
    DISABLE("0", "禁用"),

    ;

    UserMockLoginEnum(String code, String text){
        this.code = code;
        this.text = text;
    }

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
        Arrays.stream(UserMockLoginEnum.values()).forEach(userMockLoginEnum -> {
            dict.set(userMockLoginEnum.getCode(),userMockLoginEnum.getText());
        });
        return dict;
    }

}
