
package com.yunke.admin.framework.core.enums;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.lang.Dict;
import com.yunke.admin.common.enums.AbstractDictEnum;
import com.yunke.admin.common.enums.EnumDictTypeConstant;
import com.yunke.admin.framework.core.annotion.EnumDict;
import java.util.Arrays;

/**
 * @className LogStatusEnum
 * @description: 日志状态枚举
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@EnumDict(EnumDictTypeConstant.LOG_STATUS)
public enum LogStatusEnum implements AbstractDictEnum {

    /**
     * 失败
     */
    FAIL("N", "失败"),

    /**
     * 成功
     */
    SUCCESS("Y", "成功");

    private final String code;

    private final String text;

    LogStatusEnum(String code, String text) {
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
        Arrays.stream(LogStatusEnum.values()).forEach(logSuccessStatusEnum -> {
            dict.set(logSuccessStatusEnum.getCode(),logSuccessStatusEnum.getText());
        });
        return dict;
    }
}
