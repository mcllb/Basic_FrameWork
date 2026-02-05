package com.yunke.admin.modular.system.region.enums;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.lang.Dict;
import com.yunke.admin.common.enums.AbstractDictEnum;
import com.yunke.admin.common.enums.EnumDictTypeConstant;
import com.yunke.admin.framework.core.annotion.EnumDict;
import lombok.AllArgsConstructor;

import java.util.Arrays;

/**
 * @className RegionLevelEnum
 * @description: 行政区划行政级别枚举
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@EnumDict(EnumDictTypeConstant.SYS_REGION_LEVEL)
@AllArgsConstructor
public enum RegionLevelEnum implements AbstractDictEnum {

    /**
     * 省/直辖市
     */
    PROVINCE("1", "省/直辖市"),

    /**
     * 地市/州
     */
    CITY("2", "地市/州"),

    /**
     * 区县
     */
    AREA("3", "区/县"),

    /**
     * 镇
     */
    TOWN("4", "街道/镇"),

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
        Arrays.stream(RegionLevelEnum.values()).forEach(value -> {
            dict.set(value.getCode(),value.getText());
        });
        return dict;
    }
}
