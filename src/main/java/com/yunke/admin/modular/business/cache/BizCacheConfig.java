package com.yunke.admin.modular.business.cache;

import com.yunke.admin.framework.config.CacheConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@ConditionalOnBean(CacheConfig.class)
@Configuration
public class BizCacheConfig implements Ordered {


    @Override
    public int getOrder() {
        return 2;
    }
}
