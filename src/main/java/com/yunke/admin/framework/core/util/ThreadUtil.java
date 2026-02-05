package com.yunke.admin.framework.core.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Executor;

public class ThreadUtil extends cn.hutool.core.thread.ThreadUtil {

    public static TransmittableThreadLocal<HttpServletRequest> requestTransmittableThreadLocal = new TransmittableThreadLocal<HttpServletRequest>();

    public static TransmittableThreadLocal requestTransmittableThreadLocalAny = new TransmittableThreadLocal();

    public static void shareRequest(HttpServletRequest request){
        requestTransmittableThreadLocal.set(request);

    }

    public static HttpServletRequest getRequest(){
        HttpServletRequest request = requestTransmittableThreadLocal.get();
        if(request!=null){
            return request;
        }else{
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if(requestAttributes!=null){
                return  requestAttributes.getRequest();
            }else{
                return  null;
            }
        }
    }

    public static void removeRequest(){
        requestTransmittableThreadLocal.remove();
    }

    public static void share(Object value){
        requestTransmittableThreadLocalAny.set(value);
    }

    public static Object get(){
        return requestTransmittableThreadLocalAny.get();
    }

    public static <T> T get(Class<T> type){
        Object value = requestTransmittableThreadLocalAny.get();
        return Convert.convert(type, value);
    }

    public static <T> T get(Class<T> type,T defaultValue){
        Object value = requestTransmittableThreadLocalAny.get();
        return Convert.convert(type, value,defaultValue);
    }


    public static void remove(){
        requestTransmittableThreadLocalAny.remove();
    }

    /**
     * @description: 获取异步线程池
     * <p></p>
     * @return java.util.concurrent.Executor
     * @auth: tianlei
     * @date: 2026/1/14 15:04
     */
    public static Executor getTaskExecutor(){
        return SpringUtil.getBean("taskExecutor",Executor.class);
    }

    /**
     * @description: 获取共享request的异步线程池
     * <p></p>
     * @return java.util.concurrent.Executor
     * @auth: tianlei
     * @date: 2026/1/14 15:04
     */
    public static Executor getShareTaskExecutor(){
        return SpringUtil.getBean("shareTaskExecutor",Executor.class);
    }

    /**
     * @description: 获取当前线程名称
     * <p></p>
     * @return java.lang.String
     * @auth: tianlei
     * @date: 2026/1/14 15:04
     */
    public static String threadName(){
        return Thread.currentThread().getName();
    };

    /**
     * @description: 获取线程名称
     * <p></p>
     * @param thread
     * @return java.lang.String
     * @auth: tianlei
     * @date: 2026/1/14 15:08
     */
    public static String threaName(Thread thread){
        return thread.getName();
    }

    /**
     * @description: 判断当前线程是否为web线程
     * <p></p>
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/14 15:09
     */
    public static boolean isWebThread(){
        return isWebThread(Thread.currentThread());
    }

    /**
     * @description: 判断线程是否为web线程
     * <p></p>
     * @param thread
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/14 15:10
     */
    public static boolean isWebThread(Thread thread){
        return thread.getName().startsWith("http");
    }


}
