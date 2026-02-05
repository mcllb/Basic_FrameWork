package com.yunke.admin.framework.aspect;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.yunke.admin.common.util.AnnotationUtil;
import com.yunke.admin.common.util.JoinPointUtil;
import com.yunke.admin.common.util.SaUtil;
import com.yunke.admin.framework.async.AsyncLogExecutor;
import com.yunke.admin.framework.core.annotion.OpLog;
import com.yunke.admin.framework.core.annotion.OpLogHeader;
import com.yunke.admin.framework.core.enums.LogStatusEnum;
import com.yunke.admin.framework.core.exception.BaseException;
import com.yunke.admin.framework.core.util.LogAbstractUtil;
import com.yunke.admin.modular.monitor.log.model.entity.LogOper;
import com.yunke.admin.modular.system.auth.model.vo.LoginUserVO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

/**
 * @className LogOperAspect
 * @description: 操作日志切面
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/29
 */
@Slf4j
@Aspect
public class LogOperAspect {

    @Autowired
    private AsyncLogExecutor logExecutor;

    /**
     * 日志切入点
     */
    @Pointcut("@annotation(com.yunke.admin.framework.core.annotion.OpLog)")
    private void getLogPointCut() {
    }


    @Around("getLogPointCut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 操作计时器
        StopWatch sw = new StopWatch();
        try {
            sw.start();
            Object obj = proceedingJoinPoint.proceed();
            sw.stop();
            handleLog(proceedingJoinPoint,null,obj,sw.getTotalTimeMillis());
            return obj;
        }catch (Exception e){
            e.printStackTrace();
            handleLog(proceedingJoinPoint,e,null,sw.getTotalTimeMillis());
            throw e;
        }
    }


    protected void handleLog(final ProceedingJoinPoint proceedingJoinPoint, final Exception exception, Object result,long useTime){
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        OpLog annotation = method.getAnnotation(OpLog.class);
        LogOper logOper = new LogOper();
        LogAbstractUtil.addRequestInfoToLog(logOper);
        // 设置操作时间
        logOper.setOpTime(new Date());
        // 设置操作用时
        logOper.setUseTime(useTime);
        // 设置日志标题
        String logHeader = getLogHeader(method);
        String title = StrUtil.isNotEmpty(logHeader) ? logHeader + "-" + annotation.title() : annotation.title();
        logOper.setTitle(title);
        // 设置操作类型
        logOper.setOpType(annotation.opType().name());
        // 设置操作人
        logOper.setAccount(SaUtil.getAccount());
        logOper.setUserId(SaUtil.getUserId());
        logOper.setUserName(SaUtil.getUserName());
        logOper.setUserType(SaUtil.getUserType());
        logOper.setDevice(SaUtil.getLoginDevice());

        // 设置类名
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        logOper.setClassName(className);
        // 设置方法名
        String methodName = proceedingJoinPoint.getSignature().getName();
        logOper.setMethodName(methodName);

        // 设置请求参数
        if(annotation.isSaveRequestParam()){
            String param = JoinPointUtil.getArgsJsonString(proceedingJoinPoint);
            if(StrUtil.isNotEmpty(param) && StrUtil.length(param) > 4000){
                param = StrUtil.sub(param, 0, 4000);
            }
            logOper.setParam(param);
        }

        // 设置请求状态
        logOper.setStatus(LogStatusEnum.SUCCESS.getCode());
        if(exception != null){
            logOper.setStatus(LogStatusEnum.FAIL.getCode());
            int length = exception.getMessage().length() >= 100 ? 100 : exception.getMessage().length();
            String errotMessage = exception.getMessage().substring(0, length);
            if(exception instanceof BaseException){
                errotMessage = exception.getMessage();
            }
            logOper.setErrorMessage(errotMessage);
            String stackTrace = Arrays.toString(exception.getStackTrace());
            if(StrUtil.isNotEmpty(stackTrace) && StrUtil.length(stackTrace) > 4000){
                stackTrace = StrUtil.sub(stackTrace, 0, 4000);
            }
            logOper.setStackTrace(stackTrace);
        }
        if(annotation.isSaveResponseData()){
            if(result != null){
                String resultStr = JSONUtil.toJsonStr(result);
                if(StrUtil.isNotEmpty(resultStr) && StrUtil.length(resultStr) > 4000){
                    resultStr = StrUtil.sub(resultStr, 0, 4000);
                }
                logOper.setResult(resultStr);
            }
        }


        log.debug("保存操作日志：{}",logOper);
        logExecutor.saveOpLog(logOper);
    }

    /**
     * 获取操作日志业务模块名称
     * 即Controller 类@OpLogHeader注解值
     *
     * @param method
     * @return
     */
    private String getLogHeader(Method method){
        Class<?> methodDeclaringClass = method.getDeclaringClass();
        Object annotationValue = AnnotationUtil.getAnnotationValue(methodDeclaringClass, OpLogHeader.class);
        if(annotationValue != null){
            return String.valueOf(annotationValue);
        }
        return StrUtil.EMPTY;
    }

}
