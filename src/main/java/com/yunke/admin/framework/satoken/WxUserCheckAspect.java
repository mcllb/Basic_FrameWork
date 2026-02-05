package com.yunke.admin.framework.satoken;

import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ArrayUtil;
import com.yunke.admin.common.enums.AuthExceptionEnum;
import com.yunke.admin.common.util.SaUtil;
import com.yunke.admin.framework.aspect.ControllerRequestPointCutDef;
import com.yunke.admin.framework.core.annotion.WxUserCheck;
import com.yunke.admin.framework.core.exception.AuthException;
import com.yunke.admin.modular.system.wx.enums.WxUserTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
  * @className WxUserCheckLoginHandle
  * @description TODO
  * <p></p>
  * @version 1.0
  * @author tianlei
  * @date 2026/1/22
  */
@Slf4j
@Aspect
@Component
@Order(-100)
public class WxUserCheckAspect implements ControllerRequestPointCutDef {


    public WxUserCheckAspect() {

    }

    /**
     * controller 方法注解
     */
    @Pointcut("@annotation(com.yunke.admin.framework.core.annotion.WxUserCheck)")
    private void annotatedMethod(){

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
        WxUserCheck annotation = method.getAnnotation(WxUserCheck.class);
        if(annotation.checkLogin()){
            StpUtil.checkLogin();
            boolean wxLogin = SaUtil.isWxLogin();
            if(!wxLogin){
                throw new AuthException(AuthExceptionEnum.NOT_WX_LOGIN);
            }
        }
        if(WxUserTypeEnum.SYS.getCode().equals(annotation.userType().getCode())){
            boolean sysWxLogin = SaUtil.isSysWxLogin();
            if(!sysWxLogin){
                throw new AuthException(AuthExceptionEnum.NOT_SYS_WX_LOGIN);
            }
        }else if(WxUserTypeEnum.CUST.getCode().equals(annotation.userType().getCode())){
            boolean custWxLogin = SaUtil.isCustWxLogin();
            if(!custWxLogin){
                throw new AuthException(AuthExceptionEnum.NOT_CUST_WX_LOGIN);
            }
        }

        String[] roleArray = annotation.roles();
        if(ArrayUtil.isNotEmpty(roleArray)){
            if(SaMode.OR == annotation.mode()){
                StpUtil.checkRoleOr(roleArray);
            }else{
                StpUtil.checkRoleAnd(roleArray);
            }
        }

    }

}
