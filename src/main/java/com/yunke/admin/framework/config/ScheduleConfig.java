package com.yunke.admin.framework.config;

import com.yunke.admin.framework.job.SysFileClear;
import com.yunke.admin.framework.job.SysLogClear;
import com.yunke.admin.modular.business.schedule.BizScheduleTask;
import com.yunke.admin.modular.schedule.SysScheduleTask;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@ConditionalOnProperty(name = "project.schedule-enabled",havingValue = "true",matchIfMissing = true)
@Configuration
@EnableScheduling
public class ScheduleConfig {

    @Bean
    public SysScheduleTask sysScheduleTask() {
        return new SysScheduleTask();
    }

    @Bean
    public BizScheduleTask bizScheduleTask() {
        return new BizScheduleTask();
    }

    @Bean
    public SysLogClear sysLogClear(){
        return new SysLogClear();
    }

    @Bean
    public SysFileClear sysFileClear(){
        return new SysFileClear();
    }

}
