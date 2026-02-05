package com.yunke.admin.modular.system.config.enums;


import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.lang.Dict;
import com.yunke.admin.common.enums.AbstractDictEnum;
import com.yunke.admin.common.enums.EnumDictTypeConstant;
import com.yunke.admin.framework.core.annotion.EnumDict;
import lombok.Getter;

import java.util.Arrays;


/**
 * @className ParamConfigTypeEnum
 * @description: 参数配置类型枚举
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@EnumDict(EnumDictTypeConstant.SYS_CONFIG_TYPE)
@Getter
public enum ParamConfigTypeEnum implements AbstractDictEnum {


    /**
     * 系统参数
     */
    SYSTEM("system", "系统参数"),

    /**
     * 业务参数
     */
    BUSINESS("business", "业务参数"),


    ;

    private final String code;

    private final String text;

    ParamConfigTypeEnum(String code, String text) {
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
        Arrays.stream(ParamConfigTypeEnum.values()).forEach(commonIfEnum -> {
            dict.set(commonIfEnum.getCode(),commonIfEnum.getText());
        });
        return dict;
    }


}
