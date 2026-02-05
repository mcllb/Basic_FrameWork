package com.yunke.admin.framework.aspect;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.yunke.admin.common.util.ServletUtil;
import com.yunke.admin.framework.core.annotion.OpenApi;
import com.yunke.admin.framework.openapi.validate.OpenApiValidateParam;
import com.yunke.admin.framework.openapi.validate.OpenApiValidateService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Slf4j
@Aspect
public class OpenApiAspect implements ControllerRequestPointCutDef {

    /**
     * controller 方法注解
     */
    @Pointcut("@annotation(com.yunke.admin.framework.core.annotion.OpenApi)")
    private void annotatedMethod() {

    }

    /**
     * 切入点
     */
    @Pointcut("apiMethod() && annotatedMethod()")
    private void getPointCut(){

    }

    /**
     * 前置处理
     * @param point
     * @throws Throwable
     */
    @Before("getPointCut()")
    public void doAround(JoinPoint point) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        OpenApi annotation = method.getAnnotation(OpenApi.class);
        OpenApiValidateService validateService = null;
        String validateBean = annotation.validateBean();
        if(StrUtil.isNotEmpty(validateBean)){
            validateService = SpringUtil.getBean(validateBean, OpenApiValidateService.class);
        }
        if(validateService == null){
            validateService = SpringUtil.getBean(annotation.validateBeanClass());
        }
        log.debug("OpenApiValidateService={}",validateService.getClass().getName());
        OpenApiValidateParam openApiValidateParam = new OpenApiValidateParam();
        openApiValidateParam.setRequest(ServletUtil.getRequest());

        try {
            validateService.validate(openApiValidateParam);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
