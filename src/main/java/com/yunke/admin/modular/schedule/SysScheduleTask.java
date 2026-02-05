package com.yunke.admin.modular.schedule;

import com.yunke.admin.framework.job.SysFileClear;
import com.yunke.admin.framework.job.SysLogClear;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @className SysScheduleTask
 * @description: 系统定时任务
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Slf4j
public class SysScheduleTask {

    @Autowired
    private SysLogClear sysLogClear;
    @Autowired
    private SysFileClear sysFileClear;

    @Scheduled(cron = "0 15 1 * * *")
    public void deleteVisLog(){
        sysLogClear.clearVisLog();
    }

    @Scheduled(cron = "0 15 1 * * *")
    public void deleteOperLog(){
        sysLogClear.clearOperLog();
    }

    @Scheduled(cron = "0 5 0/1 * * *")
    public void deleteTempFile(){
        sysFileClear.clearTempFile();
    }

}
