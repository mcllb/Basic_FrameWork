package com.yunke.admin.common.util;


import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.setting.dialect.Props;
import com.yunke.admin.framework.cache.DataDictCache;
import com.yunke.admin.framework.cache.DeptCache;
import com.yunke.admin.framework.cache.UserCache;
import com.yunke.admin.framework.cache.RegionCache;
import com.yunke.admin.framework.core.annotion.*;
import com.yunke.admin.framework.core.exception.ServiceException;
import com.yunke.admin.modular.system.dept.model.entity.Dept;
import com.yunke.admin.modular.system.region.model.vo.RegionVO;
import com.yunke.admin.modular.system.user.model.entity.User;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Slf4j
public class BeanUtil extends cn.hutool.core.bean.BeanUtil {

    /**
     * 缓存枚举字典
     */
    private static final ConcurrentHashMap<Class,Dict> enumDictMap = MapUtil.newConcurrentHashMap();

    private static ForkJoinPool forkJoinPool;

    static {
        Props props = new Props("application.properties");
        int parallelism = props.getInt("yk.fork-join-pool.parallelism", 4);
        forkJoinPool = new ForkJoinPool(parallelism);
        log.info("初始化ForkJoinPool，parallelism={}",forkJoinPool.getParallelism());
    }

    /**
     * 枚举字典、数据字典、用户字段、部门字段、行政区划字段文本字段后缀
     */
    private static final String TEXT_FIELD_SUFFIX = "Text";

    /**
     * 获取数据字典缓存
     * @return
     */
    private static DataDictCache getDataDictCache(){
        return SpringUtil.getBean(DataDictCache.class);
    }

    /**
     * 获取用户缓存
     * @return
     */
    private static UserCache getUserCache(){
        return SpringUtil.getBean(UserCache.class);
    };

    /**
     * 获取部门缓存
     * @return
     */
    private static DeptCache getDeptCache(){
        return SpringUtil.getBean(DeptCache.class);
    }

    /**
     * 获取枚举字典缓存
     * @return
     */
    private static Dict getEnumDictCache(Class dictType){
        if(enumDictMap.containsKey(dictType)){
            return enumDictMap.get(dictType);
        }
        Dict dict = null;
        Method getDictMethod = null;
        try {
            getDictMethod = dictType.getMethod("getDict");
            Object[] ojects = dictType.getEnumConstants();
            dict = (Dict)getDictMethod.invoke(ojects[0]);
            enumDictMap.put(dictType, dict);
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("获取枚举字典失败，dictType={}",dictType.getClass());
        }
        return dict;
    }

    /**
     * 拷贝集合bean
     *
     * @param sources
     * @param target
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target) {
        if(sources == null){
            return null;
        }
        Assert.notNull(target);
        List<T> list = new ArrayList<>(sources.size());
        for (S source : sources) {
            T t = target.get();
            copyProperties(source, t);
            list.add(t);
        }
        return list;
    }


    public static void fillListDictField(List<?> beans){
        if(CollUtil.isNotEmpty(beans)){
            ForkJoinTask<?> submit = forkJoinPool.submit(() -> {
                beans.parallelStream().forEach(BeanUtil::fillDictField);
            });
            try {
                submit.get(30, TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException("填充字典字段发生异常");
            }
        }
    }

    /**
     * 填充bean字典文本字段
     * 数据字典优先
     * @param bean
     */
    public static void fillDictField(Object bean){
        if(bean == null){
            return;
        }
        if(!isBean(bean.getClass())){
            log.warn(">>> 填充bean数据字典字段发生失败,Class[{}]不是一个bean,bean={}", bean.getClass(), bean);
            return;
        }
        // 获取所有属性字段
        Field[] fields = ReflectUtil.getFieldsDirectly(bean.getClass(), true);
        for(Field field : fields){
            if(AnnotationUtil.hasAnnotation(field, DataDictField.class)){// 判断属性字段是否数据字典字段
                fillDataDictField(bean,field);
            }else if(AnnotationUtil.hasAnnotation(field, EnumDictField.class)){// 判断属性字段是否枚举字典字段
                fillEnumDictField(bean,field);
            }
        }
    }
    

    /**
     * 填充bean数据字典文本字段
     * 文本字段名必须是数据字典字段名+Text
     * 数据字典字段必须用@DataDictField注解标记
     * @param bean
     */
    public static void fillDataDictField(Object bean){
        if(bean == null){
            return;
        }
        if(!isBean(bean.getClass())){
            log.warn(">>> 填充bean数据字典字段发生失败,Class[{}]不是一个bean,bean={}", bean.getClass(), bean);
            return;
        }
        // 获取所有属性字段
        Field[] fields = ReflectUtil.getFieldsDirectly(bean.getClass(), true);
        for(Field field : fields){
            // 判断属性字段是否数据字典字段
            boolean isDataDictField = AnnotationUtil.hasAnnotation(field, DataDictField.class);
            if(isDataDictField){
                fillDataDictField(bean,field);
            }
        }
    }

    public static void fillDataDictField(Object bean,Field field){
        Object fieldValue = ReflectUtil.getFieldValue(bean, field);
        if(fieldValue != null){
            // 判断是否有文本字段
            DataDictField annotation = AnnotationUtil.getAnnotation(field, DataDictField.class);
            String textFieldName = annotation.valueFieldName();
            if(StrUtil.isEmpty(textFieldName)){
                textFieldName = field.getName() + TEXT_FIELD_SUFFIX;
            }
            boolean hasTextField = ReflectUtil.hasField(bean.getClass(), textFieldName);
            if(hasTextField){
                // 数据字典类型
                String dictType = annotation.value().name();
                boolean isMultiple = annotation.multiple();
                // 获取数据字典缓存

                Dict dict = getDataDictCache().get(dictType);
                if(dict != null){
                    if(isMultiple){
                        String separator = annotation.separator();
                        String[] dictKeys = StrUtil.splitToArray(StrUtil.toString(fieldValue),separator);
                        List<String> dictValues = CollUtil.newArrayList();
                        for(String key : dictKeys){
                            if(dict.containsKey(key)){
                                String dictValue = dict.getStr(key);
                                dictValues.add(dictValue);
                            }
                        }
                        String fieldText = CollUtil.join(dictValues, separator);
                        // 设置文本字段值
                        ReflectUtil.setFieldValue(bean,textFieldName,fieldText);
                    }else{
                        // 设置文本字段值
                        ReflectUtil.setFieldValue(bean,textFieldName,dict.get(fieldValue));
                    }
                }else{
                    log.warn(">>> 填充bean数据字典字段失败,获取数据字典缓存为null,字典类型={},Class={},字典字段={}",fieldValue, bean.getClass(),field.getName());
                }
            }else{
                log.warn(">>> 填充bean数据字典字段失败,Class[{}]缺少字段{},字典字段={}", bean.getClass(),textFieldName,field.getName());
            }
        }
    }

    public static void fillListDataDictField(Collection<?> beans){
        if(CollUtil.isNotEmpty(beans)){
            ForkJoinTask<?> submit = forkJoinPool.submit(() -> {
                beans.parallelStream().forEach(BeanUtil::fillDataDictField);
            });
            try {
                submit.get(30, TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException("填充数据字典字段发生异常");
            }
        }
    }

    /**
     * 填充bean枚举字典文本字段
     * 文本字段名必须是枚举字典字段名+Text
     * 枚举字典字段必须用@EnumDictField注解标记
     * @param bean
     */
    public static void fillEnumDictField(Object bean){
        if(bean == null){
            return;
        }
        if(!isBean(bean.getClass())){
            log.warn(">>> 填充bean枚举字典字段发生失败,Class[{}]不是一个bean,bean={}", bean.getClass(), bean);
            return;
        }
        // 获取所有属性字段
        Field[] fields = ReflectUtil.getFieldsDirectly(bean.getClass(), true);
        for(Field field : fields){
            // 判断属性字段是否枚举字典字段
            boolean isEnumDictField = AnnotationUtil.hasAnnotation(field, EnumDictField.class);
            if(isEnumDictField){
                fillEnumDictField(bean,field);
            }
        }
    }
    
    public static void fillEnumDictField(Object bean,Field field){
        Object fieldValue = ReflectUtil.getFieldValue(bean, field);
        if(fieldValue != null){
            // 判断是否有文本字段
            EnumDictField annotation = AnnotationUtil.getAnnotation(field, EnumDictField.class);
            String textFieldName = annotation.valueFieldName();
            if(StrUtil.isEmpty(textFieldName)){
                textFieldName = field.getName() + TEXT_FIELD_SUFFIX;
            }
            boolean hasTextField = ReflectUtil.hasField(bean.getClass(), textFieldName);
            if(hasTextField){
                Dict dict = getEnumDictCache(annotation.value());
                if(dict != null){
                    boolean isMultiple = annotation.multiple();
                    if(isMultiple){
                        String separator = annotation.separator();
                        String[] dictKeys = StrUtil.splitToArray(StrUtil.toString(fieldValue),separator);
                        List<String> dictValues = CollUtil.newArrayList();
                        for(String key : dictKeys){
                            if(dict.containsKey(key)){
                                String dictValue = dict.getStr(key);
                                dictValues.add(dictValue);
                            }
                        }
                        String fieldText = CollUtil.join(dictValues, separator);
                        // 设置文本字段值
                        ReflectUtil.setFieldValue(bean,textFieldName,fieldText);
                    }else{
                        // 设置文本字段值
                        ReflectUtil.setFieldValue(bean,textFieldName,dict.get(fieldValue));
                    }
                }else{
                    log.warn(">>> 填充bean枚举字典字段失败,获取枚举字典为null,字典类型={},Class={},字典字段={}",fieldValue, bean.getClass(),field.getName());
                }
            }else{
                log.warn(">>> 填充bean枚举字典字段失败,Class[{}]缺少字段{},字典字段={}", bean.getClass(),textFieldName,field.getName());
            }
        }
    }

    public static void fillListEnumDictField(Collection<?> beans){
        if(CollUtil.isNotEmpty(beans)){
            ForkJoinTask<?> submit = forkJoinPool.submit(() -> {
                beans.parallelStream().forEach(BeanUtil::fillEnumDictField);
            });
            try {
                submit.get(30, TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException("填充枚举字典字段发生异常");
            }
        }
    }

    /**
     * 填充bean 用户文本字段
     * 文本字段名必须是用户字段名+Text
     * 用户字段必须用@UserField注解标记
     * @param bean
     */
    public static void fillUserField(Object bean){
        if(bean == null){
            return;
        }
        if(!isBean(bean.getClass())){
            log.warn(">>> 填充bean用户字段发生失败,Class[{}]不是一个bean,bean={}", bean.getClass(), bean);
            return;
        }
        // 获取所有属性字段
        Field[] fields = ReflectUtil.getFieldsDirectly(bean.getClass(), true);
        for(Field field : fields){
            // 判断属性字段是否为用户字段
            boolean isUserField = AnnotationUtil.hasAnnotation(field, UserField.class);
            if(isUserField){
                fillUserField(bean,field);
            }
        }
    }

    public static void fillUserField(Object bean,Field field){
        Object fieldValue = ReflectUtil.getFieldValue(bean, field);
        if(fieldValue != null){
            // 判断是否有文本字段
            UserField annotation = AnnotationUtil.getAnnotation(field, UserField.class);
            String textFieldName = annotation.valueFieldName();
            if(StrUtil.isEmpty(textFieldName)){
                textFieldName = field.getName() + TEXT_FIELD_SUFFIX;
            }
            boolean hasTextField = ReflectUtil.hasField(bean.getClass(), textFieldName);
            if(hasTextField){
                // 获取用户缓存
                boolean isMultiple = annotation.multiple();
                if(isMultiple){
                    String separator = annotation.separator();
                    String[] userIds = StrUtil.splitToArray(StrUtil.toString(fieldValue),separator);
                    List<String> userNames = CollUtil.newArrayList();
                    for(String userId : userIds){
                        User user = getUserCache().get(userId);
                        if(user != null){
                            userNames.add(user.getUserName());
                        }else{
                            log.warn(">>> 填充bean用户字段失败,获取用户缓存为null,用户id={},Class={},用户字段={}",fieldValue, bean.getClass(),field.getName());
                        }
                    }
                    String fieldText = CollUtil.join(userNames, separator);
                    // 设置文本字段值
                    ReflectUtil.setFieldValue(bean,textFieldName,fieldText);
                }else{
                    String userId = StrUtil.toString(fieldValue);
                    User user = getUserCache().get(userId);
                    if(user != null){
                        // 设置文本字段值
                        ReflectUtil.setFieldValue(bean,textFieldName,user.getUserName());
                    }else{
                        log.warn(">>> 填充bean用户字段失败,获取用户缓存为null,用户id={},Class={},用户字段={}",fieldValue, bean.getClass(),field.getName());
                    }
                }
            }else{
                log.warn(">>> 填充bean用户字段失败,Class[{}]缺少字段{},用户字段={}", bean.getClass(),textFieldName,field.getName());
            }
        }
    }

    public static void fillListUserField(Collection<?> beans){
        if(CollUtil.isNotEmpty(beans)){
            ForkJoinTask<?> submit = forkJoinPool.submit(() -> {
                beans.parallelStream().forEach(BeanUtil::fillUserField);
            });
            try {
                submit.get(30, TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException("填充用户字段发生异常");
            }
        }
    }

    /**
     * 填充bean 部门文本字段
     * 文本字段名必须是部门字段名+Text
     * 部门字段必须用@DeptField注解标记
     * @param bean
     */
    public static void fillDeptField(Object bean){
        if(bean == null){
            return;
        }
        if(!isBean(bean.getClass())){
            log.warn(">>> 填充bean部门字段发生失败,Class[{}]不是一个bean,bean={}", bean.getClass(), bean);
            return;
        }
        // 获取所有属性字段
        Field[] fields = ReflectUtil.getFieldsDirectly(bean.getClass(), true);
        for(Field field : fields){
            // 判断属性字段是否为部门字段
            boolean isDeptField = AnnotationUtil.hasAnnotation(field, DeptField.class);
            if(isDeptField){
                fillDeptField(bean,field);
            }
        }
    }

    public static void fillDeptField(Object bean,Field field){
        Object fieldValue = ReflectUtil.getFieldValue(bean, field);
        if(fieldValue != null){
            // 判断是否有文本字段
            DeptField annotation = AnnotationUtil.getAnnotation(field, DeptField.class);
            String textFieldName = annotation.valueFieldName();
            if(StrUtil.isEmpty(textFieldName)){
                textFieldName = field.getName() + TEXT_FIELD_SUFFIX;
            }
            boolean hasTextField = ReflectUtil.hasField(bean.getClass(), textFieldName);
            if(hasTextField){
                boolean isMultiple = annotation.multiple();
                if(isMultiple){
                    String separator = annotation.separator();
                    String[] deptIds = StrUtil.splitToArray(StrUtil.toString(fieldValue),separator);
                    List<String> deptNames = CollUtil.newArrayList();
                    for(String deptId : deptIds){
                        Dept dept = getDeptCache().get(deptId);
                        if(dept != null){
                            deptNames.add(dept.getDeptName());
                        }else{
                            log.warn(">>> 填充bean部门字段失败,获取部门缓存为null,部门id={},Class={},部门字段={}",fieldValue, bean.getClass(),field.getName());
                        }
                    }
                    String fieldText = CollUtil.join(deptNames, separator);
                    // 设置文本字段值
                    ReflectUtil.setFieldValue(bean,textFieldName,fieldText);
                }else{
                    String deptId = StrUtil.toString(fieldValue);
                    Dept dept = getDeptCache().get(deptId);
                    if(dept != null){
                        // 设置文本字段值
                        ReflectUtil.setFieldValue(bean,textFieldName,dept.getDeptName());
                    }else{
                        log.warn(">>> 填充bean部门字段失败,获取部门缓存为null,部门id={},Class={},部门字段={}",fieldValue, bean.getClass(),field.getName());
                    }
                }
            }else{
                log.warn(">>> 填充bean部门字段失败,Class[{}]缺少字段{},部门字段={}", bean.getClass(),textFieldName,field.getName());
            }
        }
    }

    public static void fillListDeptField(Collection<?> beans){
        if(CollUtil.isNotEmpty(beans)){
            ForkJoinTask<?> submit = forkJoinPool.submit(() -> {
                beans.parallelStream().forEach(BeanUtil::fillDeptField);
            });
            try {
                submit.get(30, TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException("填充部门字段发生异常");
            }
        }
    }

    /**
     * 填充bean行政区划文本字段
     * 文本字段名必须是行政区划字段名+Text
     * 行政区划字段必须用@RegionField注解标记
     * @param bean
     */
    public static void fillRegionField(Object bean){
        if(bean == null){
            return;
        }
        if(!isBean(bean.getClass())){
            log.warn(">>> 填充bean枚举字典字段发生失败,Class[{}]不是一个bean,bean={}", bean.getClass(), bean);
            return;
        }
        // 获取所有属性字段
        Field[] fields = ReflectUtil.getFieldsDirectly(bean.getClass(), true);
        for(Field field : fields){
            // 判断属性字段是否行政区划字段
            boolean isRegionField = AnnotationUtil.hasAnnotation(field, RegionField.class);
            if(isRegionField){
                Object fieldValue = ReflectUtil.getFieldValue(bean, field);
                if(fieldValue != null){
                    // 判断是否有文本字段
                    String textFieldName = field.getName() + TEXT_FIELD_SUFFIX;
                    boolean hasTextField = ReflectUtil.hasField(bean.getClass(), textFieldName);
                    if(hasTextField) {
                        // 获取行政区划缓存
                        RegionCache regionCache = SpringUtil.getBean(RegionCache.class);
                        RegionVO regionVO = regionCache.get(fieldValue.toString());
                        if(regionVO != null){
                            // 设置文本字段值
                            ReflectUtil.setFieldValue(bean,textFieldName,regionVO.getRegionName());
                        }else{
                            log.warn(">>> 填充bean行政区划字段失败,获取行政区划缓存为null,行政区划id={},Class={},行政区划字段={}",fieldValue, bean.getClass(),field.getName());
                        }
                    }else{
                        log.warn(">>> 填充bean行政区划字段失败,Class[{}]缺少字段{},行政区划字段={}", bean.getClass(),textFieldName,field.getName());
                    }
                }
            }
        }
    }

    public static void fillListRegionField(Collection<?> beans){
        if(beans != null && beans.size() > 0){
            beans.stream().forEach(bean ->{
                fillRegionField(bean);
            });
        }
    }


    public static void fill(Object bean){
        if(bean != null){
            fillDataDictField(bean);
            fillEnumDictField(bean);
            fillDeptField(bean);
            fillUserField(bean);
        }
    }

    public static void fill(Object bean,FillType fillType){
        switch (fillType){
            case DATA_DICT:
                fillDataDictField(bean);
                break;
            case ENUM_DICT:
                fillEnumDictField(bean);
                break;
            case DEPT:
                fillDeptField(bean);
                break;
            case USER:
                fillUserField(bean);
                break;
            case DEFAULT:
                fill(bean);
                break;
            default:
                log.warn("未匹配到填充类型fillType={}",fillType);
        }
    }

    public static void fill(Object bean,FillType... fillTypes){
        if ( ArrayUtil.contains(fillTypes, FillType.DATA_DICT)) {
            fillDataDictField(bean);
        }
        if (ArrayUtil.contains(fillTypes, FillType.ENUM_DICT)) {
            fillEnumDictField(bean);
        }
        if (ArrayUtil.contains(fillTypes, FillType.DEPT)) {
            fillDeptField(bean);
        }
        if (ArrayUtil.contains(fillTypes, FillType.USER)) {
            fillUserField(bean);
        }
        if (ArrayUtil.contains(fillTypes, FillType.DEFAULT)) {
            fill(bean);
        }
    }

    public static void fillList(Collection<?> beans){
        if(CollUtil.isNotEmpty(beans)){
            ForkJoinTask<?> submit = forkJoinPool.submit(() -> {
                beans.parallelStream().forEach(BeanUtil::fill);
            });
            try {
                submit.get(30, TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException("填充Bean字段发生异常");
            }
        }
    }

    public static void fillList(Collection<?> beans,FillType fillType){
        if(CollUtil.isNotEmpty(beans)){
            switch (fillType){
                case DATA_DICT:
                    fillListDataDictField(beans);
                    break;
                case ENUM_DICT:
                    fillListEnumDictField(beans);
                    break;
                case DEPT:
                    fillListDeptField(beans);
                    break;
                case USER:
                    fillListUserField(beans);
                    break;
                case DEFAULT:
                    fillList(beans);
                    break;
                default:
                    log.warn("未匹配到填充类型fillType={}",fillType);
            }
        }
    }

    public static void fillList(Collection<?> beans,FillType... fillTypes){
        if(CollUtil.isNotEmpty(beans)){
            ForkJoinTask<?> submit = forkJoinPool.submit(() -> {
                Arrays.stream(fillTypes).parallel().forEach(item -> {
                    fillList(beans,item);
                });
            });
            try {
                submit.get(30, TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException("填充Bean字段发生异常");
            }
        }
    }



    public static void main(String[] args) {

    }

}
