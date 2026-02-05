package com.yunke.admin.modular.system.wx.enums;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.lang.Dict;
import com.yunke.admin.common.enums.AbstractDictEnum;
import com.yunke.admin.framework.core.annotion.EnumDict;
import java.util.Arrays;

/**
  * @className WxUserTypeEnum
  * @description 微信用户类型
  * <p></p>
  * @version 1.0
  * @author tianlei
  * @date 2026/1/22
  */
public enum WxUserTypeEnum implements AbstractDictEnum {

    /**
     * 系统用户
     */
    SYS ("sys", "系统用户"),

    /**
     * 企业用户
     */
    CUST("cust", "企业用户"),

    /**
     * 默认
     */
    DFAULT("default", "默认"),

    ;

    private final String code;

    private final String text;

    WxUserTypeEnum(String code, String text) {
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
        Arrays.stream(this.values()).forEach(wxUserType -> {
            dict.set(wxUserType.getCode(),wxUserType.getText());
        });
        return dict;
    }
}
