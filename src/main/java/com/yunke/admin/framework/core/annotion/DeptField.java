package com.yunke.admin.framework.core.annotion;



import java.lang.annotation.*;

/**
 * @className DeptField
 * @description: 标记部门字段
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DeptField {

    /**
     * 是否多选
     */
    boolean multiple() default false;

    /**
     * 分割符
     */
    String separator() default ",";

    /**
     * 值字段名称
     * 为空时则为字典字段名称后+Text
     * @return
     */
    String valueFieldName() default "";

}
