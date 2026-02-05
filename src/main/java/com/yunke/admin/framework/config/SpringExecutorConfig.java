package com.yunke.admin.framework.config;

import com.yunke.admin.framework.async.RequestDecorator;
import com.yunke.admin.framework.config.properties.AsyncThreadPoolConfig;
import com.yunke.admin.framework.config.properties.ShardThreadPoolConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Configuration
public class SpringExecutorConfig {

    @Autowired
    private AsyncThreadPoolConfig asyncThreadPoolConfig;
    @Autowired
    private ShardThreadPoolConfig shardThreadPoolConfig;

    @Primary
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        log.info("初始化线程池taskExecutor=================");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数
        executor.setCorePoolSize(asyncThreadPoolConfig.getCorePoolSize());
        // 最大线程数
        executor.setMaxPoolSize(asyncThreadPoolConfig.getMaxPoolSize());
        // 任务队列大小
        executor.setQueueCapacity(asyncThreadPoolConfig.getQueueCapacity());
        // 线程前缀名
        executor.setThreadNamePrefix(asyncThreadPoolConfig.getNamePrefix());
        // 线程的空闲时间
        executor.setKeepAliveSeconds(asyncThreadPoolConfig.getKeepAliveSeconds());
        // 增加 TaskDecorator 属性的配置
        //executor.setTaskDecorator(new ContextDecorator());
        // 拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //用来设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 线程初始化
        executor.initialize();
        return executor;
    }


    @Bean(name = "shareTaskExecutor")
    public Executor shareTaskExecutor() {
        log.info("初始化线程池shareTaskExecutor=================");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数
        executor.setCorePoolSize(shardThreadPoolConfig.getCorePoolSize());
        // 最大线程数
        executor.setMaxPoolSize(shardThreadPoolConfig.getMaxPoolSize());
        // 任务队列大小
        executor.setQueueCapacity(shardThreadPoolConfig.getQueueCapacity());
        // 线程前缀名
        executor.setThreadNamePrefix(shardThreadPoolConfig.getNamePrefix());
        // 线程的空闲时间
        executor.setKeepAliveSeconds(shardThreadPoolConfig.getKeepAliveSeconds());
        // 增加 TaskDecorator 属性的配置
        executor.setTaskDecorator(new RequestDecorator());
        // 拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //用来设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 线程初始化
        executor.initialize();
        return executor;
    }

}
