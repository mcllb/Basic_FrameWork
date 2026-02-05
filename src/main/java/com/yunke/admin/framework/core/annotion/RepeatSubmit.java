package com.yunke.admin.framework.core.annotion;

import java.lang.annotation.*;

/**
 * @className RepeatSubmit
 * @description: 防重复提交注解
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatSubmit {

    /**
     * 间隔时间(s)，小于此时间视为重复提交
     */
    int interval() default 2;

    /**
     * 提示消息
     */
    String message() default "不允许重复提交，请稍候再试";

    /**
     * 校验类的bean名称
     */
    String serviceName() default "";

}
