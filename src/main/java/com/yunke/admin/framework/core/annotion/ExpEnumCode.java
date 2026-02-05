package com.yunke.admin.framework.core.annotion;


import java.lang.annotation.*;

/**
 * @className ExpEnumCode
 * @description: 异常枚举注解
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ExpEnumCode {

    /**
     * 模块编码
     */
    int value() default 99;


}
