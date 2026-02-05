package com.yunke.admin.framework.cache;

import cn.hutool.core.annotation.AnnotationUtil;
import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CachePenetrationProtect;
import com.alicp.jetcache.anno.CacheUpdate;
import com.alicp.jetcache.anno.Cached;
import com.yunke.admin.common.constant.CommonConstant;
import com.yunke.admin.common.enums.AbstractDictEnum;
import com.yunke.admin.common.enums.CacheExceptionEnum;
import com.yunke.admin.common.enums.EnumDictTypeConstant;
import com.yunke.admin.common.model.DictVO;
import com.yunke.admin.framework.cache.base.AbstractBaseJetcache;
import com.yunke.admin.framework.core.annotion.EnumDict;
import com.yunke.admin.framework.core.exception.CacheException;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Set;

@Slf4j
public class EnumDictCache extends AbstractBaseJetcache<DictVO> {

    public static final String CACHE_NAME = CACHE_NAME_ENUM;

    private static final String CACHE_TITLE = CACHE_TITLE_ENUM;

    public static final String BASE_PACKAGE = CommonConstant.BASE_PACKAGE;

    private EnumDictCache self;

    @Autowired
    public void setCache(EnumDictCache cache) {
        this.self = cache;
    }

    public EnumDictCache() {
        super(CACHE_NAME,CACHE_TITLE);
    }

    @Override
    public DictVO load(String key) {
        Reflections reflections = new Reflections(BASE_PACKAGE);
        Set<Class<? extends AbstractDictEnum>> monitorClasses = reflections.getSubTypesOf(AbstractDictEnum.class);
        Optional<Class<? extends AbstractDictEnum>> op = monitorClasses.stream().filter(monitorClass -> {
            if (AnnotationUtil.hasAnnotation(monitorClass, EnumDict.class)) {
                EnumDictTypeConstant numDictTypeConstant = AnnotationUtil.getAnnotationValue(monitorClass, EnumDict.class);
                String enumDictType = numDictTypeConstant.name();
                if (enumDictType.equals(key)) {
                    return true;
                }
            }
            return false;
        }).findFirst();
        if(op.isPresent()){
            Class<? extends AbstractDictEnum> aClass = op.get();
            AbstractDictEnum[] enumConstants = aClass.getEnumConstants();
            DictVO dict = new DictVO();
            for (AbstractDictEnum anEnum : enumConstants) {
                dict.set(anEnum.getCode(),anEnum.getText());
            }
            return dict;
        }
        return null;
    }

    @CachePenetrationProtect
    @Cached(name=CACHE_NAME, key="#key", expire = -1)
    @Override
    public DictVO get(String key) {
        DictVO dictVO = this.load(key);
        return dictVO;
    }

    @CacheUpdate(name=CACHE_NAME, key="#key", value="#value")
    @Override
    public void put(String key, DictVO value) {

    }

    @CacheInvalidate(name=CACHE_NAME, key="#key")
    @Override
    public void remove(String key) {

    }

    @Override
    public void loadCache() {
        Reflections reflections = new Reflections(BASE_PACKAGE);
        Set<Class<? extends AbstractDictEnum>> monitorClasses = reflections.getSubTypesOf(AbstractDictEnum.class);
        log.debug(">>> 检索到枚举字典数量：{}",monitorClasses.size());
        monitorClasses.stream().forEach(monitorClass -> {
            try {
                log.debug(">>> 检索到枚举字典：{}",monitorClass.getName());
                // 判断是否枚举字典
                if(!AnnotationUtil.hasAnnotation(monitorClass, EnumDict.class)){
                    throw new CacheException(CacheExceptionEnum.NOT_IS_ENUM_DICT);
                }
                EnumDictTypeConstant numDictTypeConstant = AnnotationUtil.getAnnotationValue(monitorClass, EnumDict.class);
                String enumDictType = numDictTypeConstant.name();
                log.debug(">>> 枚举字典类型：{}",enumDictType);

                // 判断枚举字典类型是否重复

                AbstractDictEnum[] enumConstants = monitorClass.getEnumConstants();
                DictVO dict = new DictVO();
                for (AbstractDictEnum anEnum : enumConstants) {
                    dict.set(anEnum.getCode(),anEnum.getText());
                }
                this.self.put(enumDictType,dict);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void init() {
        log.debug("EnumDictCache init ====>");
        self.get("init");
    }
}
