package com.yunke.admin.framework.aspect;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.yunke.admin.common.util.JoinPointUtil;
import com.yunke.admin.common.util.SaUtil;
import com.yunke.admin.framework.async.AsyncLogExecutor;
import com.yunke.admin.framework.core.enums.LogStatusEnum;
import com.yunke.admin.framework.core.enums.VisLogTypeEnum;
import com.yunke.admin.framework.core.exception.BaseException;
import com.yunke.admin.framework.core.util.LogAbstractUtil;
import com.yunke.admin.modular.monitor.log.model.entity.LogLogin;
import com.yunke.admin.modular.system.auth.enums.LoginDeviceEnum;
import com.yunke.admin.modular.system.auth.model.param.LoginParam;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Date;

/**
 * @className LogLoginAspect
 * @description: 登录日志切面
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/29
 */
@Aspect
@Slf4j
public class LogLoginAspect {

    @Autowired
    private AsyncLogExecutor logExecutor;

    @Pointcut("execution(public * com.yunke.admin.modular.system.auth.controller.AuthController.login(..))")
    public void getLoginPointCut(){

    }

    @Pointcut("execution(public * com.yunke.admin.modular.system.auth.controller.AuthController.wxLogin(..))")
    public void getWxLoginPointCut(){

    }

    @Pointcut("execution(public * com.yunke.admin.modular.system.auth.controller.AuthController.logout())")
    public void getLogoutPointCut(){

    }

    @Pointcut("execution(public * com.yunke.admin.modular.system.auth.controller.AuthController.wxLogout())")
    public void getWxLogoutPointCut(){

    }

    @Around("getLoginPointCut()")
    public Object doAroundLogin(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String account = "";
        try {
            Object[] args = proceedingJoinPoint.getArgs();
            LoginParam loginParam = (LoginParam)args[0];
            account = loginParam.getAccount();
            Object obj = proceedingJoinPoint.proceed();
            handleLog(VisLogTypeEnum.LOGIN,account,LoginDeviceEnum.PC.getCode(),null,proceedingJoinPoint,obj);
            return obj;
        }catch (Exception e){
            e.printStackTrace();
            handleLog(VisLogTypeEnum.LOGIN,account,LoginDeviceEnum.PC.getCode(),e,proceedingJoinPoint,null);
            throw e;
        }
    }

    @Around("getWxLoginPointCut()")
    public Object doAroundWxLogin(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            Object obj = proceedingJoinPoint.proceed();
            handleLog(VisLogTypeEnum.LOGIN,SaUtil.getAccount(),LoginDeviceEnum.WX.getCode(),null,proceedingJoinPoint,obj);
            return obj;
        }catch (Exception e){
            e.printStackTrace();
            handleLog(VisLogTypeEnum.LOGIN,"",LoginDeviceEnum.WX.getCode(),e,proceedingJoinPoint,null);
            throw e;
        }
    }

    @Around("getLogoutPointCut()")
    public Object doAroundLogout(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            String account = SaUtil.getAccount();
            Object obj = proceedingJoinPoint.proceed();
            handleLog(VisLogTypeEnum.EXIT,account,LoginDeviceEnum.PC.getCode(),null,proceedingJoinPoint,obj);
            return obj;
        }catch (Exception e){
            e.printStackTrace();
            handleLog(VisLogTypeEnum.EXIT,"",LoginDeviceEnum.PC.getCode(),e,proceedingJoinPoint,null);
            throw e;
        }
    }

    @Around("getWxLogoutPointCut()")
    public Object doAroundWxLogout(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            String account = SaUtil.getAccount();
            Object obj = proceedingJoinPoint.proceed();
            handleLog(VisLogTypeEnum.EXIT,account, LoginDeviceEnum.WX.getCode(),null, proceedingJoinPoint,obj);
            return obj;
        }catch (Exception e){
            e.printStackTrace();
            handleLog(VisLogTypeEnum.EXIT,"",LoginDeviceEnum.WX.getCode(), e, proceedingJoinPoint,null);
            throw e;
        }
    }

    protected void handleLog(final VisLogTypeEnum visLogType, final String account,final String device,final Exception exception,final ProceedingJoinPoint proceedingJoinPoint,Object result){
        LogLogin logLogin = new LogLogin();
        LogAbstractUtil.addRequestInfoToLog(logLogin);
        logLogin.setVisType(visLogType.getCode());
        logLogin.setTitle(visLogType.getText());
        logLogin.setAccount(account);
        logLogin.setVisTime(new Date());


        // 设置类名
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        logLogin.setClassName(className);
        // 设置方法名
        String methodName = proceedingJoinPoint.getSignature().getName();
        logLogin.setMethodName(methodName);

        // 设置请求参数
        String param = JoinPointUtil.getArgsJsonString(proceedingJoinPoint);
        if(StrUtil.isNotEmpty(param) && StrUtil.length(param) > 4000){
            param = StrUtil.sub(param, 0, 4000);
        }
        logLogin.setParam(param);

        // 设置请求状态
        logLogin.setStatus(LogStatusEnum.SUCCESS.getCode());
        if(exception != null){
            logLogin.setStatus(LogStatusEnum.FAIL.getCode());
            logLogin.setDevice(device);
            int length = exception.getMessage().length() >= 100 ? 100 : exception.getMessage().length();
            String errotMessage = exception.getMessage().substring(0, length);
            if(exception instanceof BaseException){
                errotMessage = exception.getMessage();
            }
            logLogin.setErrorMessage(errotMessage);
            String stackTrace = Arrays.toString(exception.getStackTrace());
            if(StrUtil.isNotEmpty(stackTrace) && StrUtil.length(stackTrace) > 4000){
                stackTrace = StrUtil.sub(stackTrace, 0, 4000);
            }
            logLogin.setStackTrace(stackTrace);
        }else{
            logLogin.setLoginId(SaUtil.getLoginId());
            logLogin.setUserId(SaUtil.getUserId());
            logLogin.setUserName(SaUtil.getUserName());
            logLogin.setUserType(SaUtil.getUserType());
            logLogin.setDevice(SaUtil.getLoginDevice());
        }
        if(result != null){
            String resultStr = JSONUtil.toJsonStr(result);
            if(StrUtil.isNotEmpty(resultStr) && StrUtil.length(resultStr) > 4000){
                resultStr = StrUtil.sub(resultStr, 0, 4000);
            }
            logLogin.setResult(resultStr);
        }
        log.debug("保存访问日志：{}",logLogin);
        logExecutor.saveVisLog(logLogin);
    }

}
