package com.yunke.admin.framework.config;

import cn.hutool.core.thread.ThreadUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.SimpleApplicationEventMulticaster;

import java.util.concurrent.ExecutorService;

@Configuration
public class EventConfig {

    @Bean
    public SimpleApplicationEventMulticaster applicationEventMulticaster() {
        ExecutorService executorService = ThreadUtil.newExecutor(5);
        SimpleApplicationEventMulticaster simpleApplicationEventMulticaster = new SimpleApplicationEventMulticaster();
        simpleApplicationEventMulticaster.setTaskExecutor(executorService);
        return simpleApplicationEventMulticaster;
    }

}
