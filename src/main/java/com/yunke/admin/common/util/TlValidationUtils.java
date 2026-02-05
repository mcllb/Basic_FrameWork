package com.yunke.admin.common.util;

import cn.hutool.core.collection.CollUtil;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TlValidationUtils {


    /**
     * 默认校验器，使用默认校验配置
     * 校验器有多种实现，hibernate校验器只是其中一种实现
     */
    private static final Validator defaultValidator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 以上代码也可以这样写
     */
    private static final Validator defaultValidator1 = Validation.byDefaultProvider()
            .configure()
            .buildValidatorFactory().getValidator();

    /**
     * hibernate默认校验器
     */
    private static final Validator hibernateDefaultValidator = Validation.byProvider(HibernateValidator.class)
            .configure()
            .buildValidatorFactory().getValidator();

    /**
     * hibernate快速失败校验器
     */
    private static final Validator hibernateFailFastValidator = Validation.byProvider(HibernateValidator.class)
            .configure().failFast(true)
            .buildValidatorFactory().getValidator();

    /**
     * 隐藏构造器
     */
    private TlValidationUtils() {
    }

    /**
     * 快速校验参数
     *
     * @param t   需要校验的参数
     * @param <T> 泛型，即要校验的参数类型
     * @return 错误提示信息。通过校验返回null，不通过校验则返回第一个错误字段的提示信息
     */
    public static <T> String validateFastFail(T t) {
        if (t == null) {
            return "the input parameter is null";
        }

        //校验参数，返回错误信息集合，通过校验时返回空集合（不为null）
        //因为这个校验器启用了快速失败模式，遇到第一个错误字段就返回，所以这个集合中最多只有一个元素
        Set<ConstraintViolation<T>> constraintViolations = hibernateFailFastValidator.validate(t);
        if (CollUtil.isEmpty(constraintViolations)) {
            return null;
        }

        return constraintViolations.iterator().next().getMessage();
    }

    /**
     * 校验参数
     *
     * @param t   要校验的参数
     * @param <T> 泛型，即要校验的参数类型
     * @return 错误提示信息集合，通过校验返回空集合
     */
    public static <T> List<String> validate(T t) {
        if (t == null) {
            return Collections.emptyList();
        }

        Set<ConstraintViolation<T>> constraintViolations = hibernateDefaultValidator.validate(t);

        return constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
    }

}
