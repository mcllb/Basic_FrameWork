
package com.yunke.admin.framework.core.enums;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.lang.Dict;
import com.yunke.admin.common.enums.AbstractDictEnum;
import com.yunke.admin.common.enums.EnumDictTypeConstant;
import com.yunke.admin.framework.core.annotion.EnumDict;

import java.util.Arrays;

/**
 * @className VisLogTypeEnum
 * @description: 访问日志类型枚举
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@EnumDict(EnumDictTypeConstant.VIS_LOG_TYPE)
public enum VisLogTypeEnum implements AbstractDictEnum {

    /**
     * 登录日志
     */
    LOGIN("1", "登录"),

    /**
     * 退出日志
     */
    EXIT("2", "登出");

    private final String code;

    private final String text;

    VisLogTypeEnum(String code, String text) {
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
        Arrays.stream(VisLogTypeEnum.values()).forEach(visLogTypeEnum -> {
            dict.set(visLogTypeEnum.getCode(),visLogTypeEnum.getText());
        });
        return dict;
    }
}
