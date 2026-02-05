package com.yunke.admin.modular.business.schedule;

import org.springframework.scheduling.annotation.Scheduled;

/**
  * @className BizScheduleTask
  * @description 业务中用到的定时任务写这里
  * <p></p>
  * @version 1.0
  * @author tianlei
  * @date 2026/2/4
  */
public class BizScheduleTask {

    @Scheduled(cron = "0 15 1 * * *")
    public void bizScheduleTest(){
        System.out.println("bizScheduleTest ===== ");
    }
}
