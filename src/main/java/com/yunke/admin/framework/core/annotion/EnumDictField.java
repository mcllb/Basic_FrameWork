package com.yunke.admin.framework.core.annotion;



import java.lang.annotation.*;

/**
 * @className EnumDictField
 * @description: 标记枚举字典字段
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumDictField {
    /**
     * 枚举类型
     */
    Class<?> value();

    /**
     * 是否多选
     */
    boolean multiple() default false;

    /**
     * 分割符
     */
    String separator() default "";

    /**
     * 字典值字段名称
     * 为空时则为字典字段名称后+Text
     * @return
     */
    String valueFieldName() default "";

}
