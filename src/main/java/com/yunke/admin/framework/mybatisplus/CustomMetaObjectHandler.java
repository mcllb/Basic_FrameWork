package com.yunke.admin.framework.mybatisplus;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.yunke.admin.common.util.AnnotationUtil;
import com.yunke.admin.common.util.SaUtil;
import com.yunke.admin.framework.core.annotion.DisableFieldFill;
import com.yunke.admin.framework.core.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectionException;

import java.util.Date;

/**
 * @className CustomMetaObjectHandler
 * @description: Mybatis-Plus自定义sql字段填充器
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Slf4j
public class CustomMetaObjectHandler implements MetaObjectHandler {

    private static final String CREATE_USER = "createBy";

    private static final String CREATE_TIME = "createTime";

    private static final String UPDATE_USER = "updateBy";

    private static final String UPDATE_TIME = "updateTime";

    @Override
    public void insertFill(MetaObject metaObject) {
        boolean disableFieldFill = isDisableFieldFill(metaObject);
        log.debug("disableFieldFill={},metaObject={}",disableFieldFill,metaObject.getOriginalObject().getClass());
        if(disableFieldFill){
            log.warn(">>> 对象禁用字段填充，class={}",metaObject.getOriginalObject().getClass());
            return;
        }
        if(!ThreadUtil.isWebThread()){
            log.warn(">>> insertFill 非web环境，不做处理");
            return;
        }
        try {
            if(!SaUtil.isLogin()){
                log.warn(">>> insertFill web环境未登录，不做处理");
                return;
            }
            log.debug(">>> insertFill begin,metaObject={}", metaObject.getOriginalObject());
            //设置createBy（BaseEntity)
            if(ReflectUtil.hasField(metaObject.getOriginalObject().getClass(),CREATE_USER)){
                if(ObjectUtil.isNull(getFieldValByName(CREATE_USER,metaObject))){
                    setFieldValByName(CREATE_USER, SaUtil.getUserId(), metaObject);
                }
            }
            //设置createTime（BaseEntity)
            if(ReflectUtil.hasField(metaObject.getOriginalObject().getClass(),CREATE_TIME)){
                if(ObjectUtil.isNull(getFieldValByName(CREATE_TIME,metaObject))){
                    setFieldValByName(CREATE_TIME, new Date(), metaObject);
                }
            }
            log.debug(">>> insertFill end,metaObject={}", metaObject.getOriginalObject());
        } catch (ReflectionException e) {
            log.warn(">>> CustomMetaObjectHandler处理过程中无相关字段，不做处理");
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        boolean disableFieldFill = isDisableFieldFill(metaObject);
        log.debug("disableFieldFill={},metaObject={}",disableFieldFill,metaObject.getOriginalObject().getClass());
        if(disableFieldFill){
            log.warn(">>> 对象禁用字段填充，class={}",metaObject.getOriginalObject().getClass());
            return;
        }
        if(!ThreadUtil.isWebThread()){
            log.warn(">>> updateFill 非web环境，不做处理");
            return;
        }
        try {
            if(!SaUtil.isLogin()){
                log.debug(">>> updateFill web环境未登录，不做处理");
                return;
            }
            log.warn(">>> updateFill begin,metaObject={}",metaObject.getOriginalObject());
            //设置updateBy（BaseEntity)
            if(ReflectUtil.hasField(metaObject.getOriginalObject().getClass(),UPDATE_TIME)){
                if(ObjectUtil.isNull(getFieldValByName(UPDATE_USER,metaObject))){
                    setFieldValByName(UPDATE_USER, SaUtil.getUserId(), metaObject);
                }
            }

            //设置updateTime（BaseEntity)
            if(ReflectUtil.hasField(metaObject.getOriginalObject().getClass(),UPDATE_TIME)){
                if(ObjectUtil.isNull(getFieldValByName(UPDATE_TIME,metaObject))){
                    setFieldValByName(UPDATE_TIME, new Date(), metaObject);
                }
            }
            log.warn(">>> updateFill end,metaObject={}",metaObject.getOriginalObject());
        } catch (ReflectionException e) {
            log.warn(">>> CustomMetaObjectHandler处理过程中无相关字段，不做处理");
        }
    }

    private boolean isDisableFieldFill(MetaObject metaObject){
        return AnnotationUtil.hasAnnotation(metaObject.getOriginalObject().getClass(), DisableFieldFill.class);
    }

}