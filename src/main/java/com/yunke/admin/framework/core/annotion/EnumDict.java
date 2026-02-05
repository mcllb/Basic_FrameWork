package com.yunke.admin.framework.core.annotion;

import com.yunke.admin.common.enums.EnumDictTypeConstant;
import java.lang.annotation.*;

/**
 * @className EnumDict
 * @description: 标记枚举字典
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumDict {

    EnumDictTypeConstant value();

}
