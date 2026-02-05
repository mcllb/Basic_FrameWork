package com.yunke.admin.framework.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = {"classpath:spring-executor.properties"}, ignoreResourceNotFound=false, encoding="UTF-8")
@ConfigurationProperties(prefix = "shard.executor.thread")
@Data
public class ShardThreadPoolConfig {

    @Value("${shard.executor.thread.core-pool-size:10}")
    private int corePoolSize;
    @Value("${shard.executor.thread.max-pool-size:20}")
    private int maxPoolSize;
    @Value("${shard.executor.thread.queue-capacity:50}")
    private int queueCapacity;
    @Value("${shard.executor.thread.name-prefix:async-service-}")
    private String namePrefix;
    @Value("${shard.executor.thread.keep-alive-seconds:60}")
    private int keepAliveSeconds;

}
