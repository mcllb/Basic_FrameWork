package com.yunke.admin.framework.core.annotion;

import com.yunke.admin.framework.openapi.validate.DefaultOpenApiValidateService;
import com.yunke.admin.framework.openapi.validate.OpenApiValidateService;

import java.lang.annotation.*;

/**
 * @className OpenApi
 * @description: OpenApi 接口注解
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface OpenApi {

    /**
     *  接口校验的实现类
     */
    Class<? extends OpenApiValidateService> validateBeanClass() default DefaultOpenApiValidateService.class;

    /**
     * 接口校验类Bean名称
     */
    String validateBean() default "";

}
