package com.yunke.admin.framework.core.annotion;


import com.yunke.admin.framework.core.enums.OpLogAnnotionOpTypeEnum;

import java.lang.annotation.*;

/**
 * @className OpLog
 * @description: 操作日志注解
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface OpLog {

    /**
     * 业务的名称,例如:"修改菜单"
     */
    String title() default "";

    /**
     * 业务操作类型枚举
     */
    OpLogAnnotionOpTypeEnum opType() default OpLogAnnotionOpTypeEnum.OTHER;

    /**
     * 是否保存请求参数
     */
    boolean isSaveRequestParam() default true;

    /**
     * 是否保存响应结果
     */
    boolean isSaveResponseData() default false;

}
