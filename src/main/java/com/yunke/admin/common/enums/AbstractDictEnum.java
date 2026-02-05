package com.yunke.admin.common.enums;

import cn.hutool.core.lang.Dict;
import org.springframework.stereotype.Component;

public interface AbstractDictEnum {

    String getCode();

    String getText();

    String getDictType();

    Dict getDict();
}
