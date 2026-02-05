package com.yunke.admin.framework.core.validation;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @className EnumDictValidatorClass
 * @description: 枚举值校验注解实现
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Slf4j
public class EnumDictValidatorClass implements ConstraintValidator<EnumDictValidator, Object>, Annotation {

    private List<Object> values = CollUtil.newArrayList();

    private boolean isMultiple = false;

    private String separator = "";

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }

    @Override
    public void initialize(EnumDictValidator enumDictValidator) {
        isMultiple = enumDictValidator.multiple();
        separator = enumDictValidator.separator();
        Class<?> clz = enumDictValidator.value();
        Object[] ojects = clz.getEnumConstants();
        try {
            Method method = clz.getMethod("getCode");
            if (Objects.isNull(method)) {
                throw new RuntimeException(String.format("枚举对象{}缺少字段名为value的字段",
                        clz.getName()));
            }
            Object value = null;
            for (Object obj : ojects) {
                value = method.invoke(obj);
                values.add(value);
            }
        } catch (Exception e) {
            log.error("[处理枚举校验异常]", e);
            throw new RuntimeException("处理枚举校验异常",e);
        }

    }


    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if(isMultiple){
            if(ObjectUtil.isNull(value)){
                return true;
            }
            if(value instanceof Collection){
                List dictKeys = (List)value;
                return CollUtil.isEmpty(dictKeys) || values.containsAll(dictKeys) ? true : false;
            }else{
                throw new RuntimeException("校验枚举字段异常,多选枚举字段必须是List类型");
            }
        }
        return ObjectUtil.isEmpty(value) || values.contains(value) ? true : false;
    }
}
