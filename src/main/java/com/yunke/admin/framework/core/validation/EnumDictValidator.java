package com.yunke.admin.framework.core.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @className EnumDictValidator
 * @description: 枚举值校验注解,多选枚举字段必须为List类型
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Constraint(validatedBy = EnumDictValidatorClass.class)
public @interface EnumDictValidator {

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

    String message() default "非法的参数";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
