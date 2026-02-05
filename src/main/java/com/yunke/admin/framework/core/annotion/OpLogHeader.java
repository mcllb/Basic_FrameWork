package com.yunke.admin.framework.core.annotion;

import java.lang.annotation.*;

/**
 * @className OpLogHeader
 * @description: 操作日志头部注解
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface OpLogHeader {

    /**
     * 操作日志业务模块名称
     */
    String value() default "";


}
