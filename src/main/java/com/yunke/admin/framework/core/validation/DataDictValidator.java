package com.yunke.admin.framework.core.validation;

import com.yunke.admin.common.enums.DataDictTypeEnum;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @className DataDictValidator
 * @description: 数据字典字段值校验注解,多选字典字段必须为List类型
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Constraint(validatedBy = DataDictValidatorClass.class)
public @interface DataDictValidator {

    /**
     * 字典类型
     */
    DataDictTypeEnum value();

    /**
     * 是否多选
     */
    boolean multiple() default false;

    String message() default "非法的参数";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
