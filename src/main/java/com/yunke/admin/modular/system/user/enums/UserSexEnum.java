package com.yunke.admin.modular.system.user.enums;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.lang.Dict;
import com.yunke.admin.common.enums.AbstractDictEnum;
import com.yunke.admin.common.enums.EnumDictTypeConstant;
import com.yunke.admin.framework.core.annotion.EnumDict;
import lombok.Getter;

import java.util.Arrays;


@EnumDict(EnumDictTypeConstant.SYS_USER_SEX)
@Getter
public enum UserSexEnum implements AbstractDictEnum {

    /**
     * 未知
     */
    UNKNOWN ("0", "未知"),

    /**
     * 男
     */
    MALE("1", "男"),

    /**
     * 女
     */
    FEMALE("2", "女"),

    ;

    private final String code;

    private final String text;

    UserSexEnum(String code, String text) {
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
        Arrays.stream(UserSexEnum.values()).forEach(userSexEnum -> {
            dict.set(userSexEnum.getCode(),userSexEnum.getText());
        });
        return dict;
    }

}
