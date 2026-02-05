package com.yunke.admin.framework.core.validation;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.yunke.admin.common.enums.DataDictTypeEnum;
import com.yunke.admin.framework.cache.DataDictCache;
import lombok.extern.slf4j.Slf4j;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;

/**
 * @className DataDictValidatorClass
 * @description: 数据字典字段值校验注解实现
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Slf4j
public class DataDictValidatorClass implements ConstraintValidator<DataDictValidator,Object>, Annotation {

    private Dict dict = new Dict();

    private boolean isMultiple = false;

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }

    @Override
    public void initialize(DataDictValidator constraintAnnotation) {
        DataDictTypeEnum dictTypeEnum = constraintAnnotation.value();
        String dictType = dictTypeEnum.name();
        if(StrUtil.isEmpty(dictType)){
            throw new RuntimeException("校验数据字典字段异常，字典类型为空");
        }
        DataDictCache dataDictCache = SpringUtil.getBean(DataDictCache.class);
        dict = dataDictCache.get(dictType);
        isMultiple = constraintAnnotation.multiple();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if(ObjectUtil.isNull(dict)){
            return true;
        }
        if(isMultiple){
            if(value instanceof Collection){
                List dictKeys = (List)value;
                if(CollUtil.isEmpty(dictKeys)){
                    return true;
                }
                return dict.keySet().containsAll(dictKeys);
            }else{
                throw new RuntimeException("校验数据字典字段异常,多选数据字典字段必须是List类型");
            }
        }
        return dict.containsKey(value);
    }
}
