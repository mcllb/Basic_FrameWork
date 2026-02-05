package com.yunke.admin.framework.core.util;


import com.yunke.admin.framework.core.annotion.ExpEnumCode;

/**
 * @className ExpEnumCodeUtil
 * @description: 异常枚举code值快速创建
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
public class ExpEnumCodeUtil {

    public static Integer getExpEnumCode(Class<?> clazz, int code) {

        // 默认的异常响应码
        Integer defaultCode = Integer.valueOf("" + 99 + 999);

        if (clazz == null) {
            return defaultCode;
        } else {
            ExpEnumCode expEnumType = clazz.getAnnotation(ExpEnumCode.class);
            if (expEnumType == null) {
                return defaultCode;
            }
            return Integer.valueOf("" + expEnumType.value()  + code);
        }

    }

}
