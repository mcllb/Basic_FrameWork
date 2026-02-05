package com.yunke.admin.common.util;

import cn.hutool.core.thread.AsyncUtil;
import com.yunke.admin.framework.core.util.ThreadUtil;
import org.springframework.core.env.Environment;

public class SpringUtil extends cn.hutool.extra.spring.SpringUtil {

    private SpringUtil(){

    }

    private static Environment environment = SpringUtil.getBean(Environment.class);

    public static Environment getEnvironment(){
        return environment;
    }

    public static void publishEventAsync(Object event){
        ThreadUtil.getTaskExecutor().execute(() -> SpringUtil.publishEvent(event));
    }



}
