package com.yunke.admin.framework.aspect;


import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.yunke.admin.common.util.ServletUtil;
import com.yunke.admin.framework.core.annotion.RepeatSubmit;
import com.yunke.admin.framework.core.exception.ServiceException;
import com.yunke.admin.framework.service.RepeatSubmitService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Slf4j
@Aspect
public class RepeatSubmitAspect {

    /**
     * 日志切入点
     */
    @Pointcut("@annotation(com.yunke.admin.framework.core.annotion.RepeatSubmit)")
    private void getRepeatSubmitPointCut() {
    }

    @Around("getRepeatSubmitPointCut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 获取执行方法
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        // 获取防重复提交注解
        RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);
        // 方法参数
        Object[] args = proceedingJoinPoint.getArgs();

        HttpServletRequest request = ServletUtil.getRequest();

        RepeatSubmitService repeatSubmitService;

        String serviceName = annotation.serviceName();
        if(StrUtil.isNotEmpty(serviceName)){
            log.debug("查找RepeatSubmitService bean={}",serviceName);
            repeatSubmitService = SpringUtil.getBean(serviceName, RepeatSubmitService.class);
            if(repeatSubmitService == null){
                log.error("未获取到RepeatSubmitService bean={},将使用默认实现",serviceName);
                repeatSubmitService = SpringUtil.getBean(RepeatSubmitService.class);
            }
        }else{
            repeatSubmitService = SpringUtil.getBean(RepeatSubmitService.class);
        }

        boolean repeatSubmit = repeatSubmitService.isRepeatSubmit(request, annotation, method, args);
        if(repeatSubmit){
            throw new ServiceException(annotation.message());
        }

        return proceedingJoinPoint.proceed();
    }


}
