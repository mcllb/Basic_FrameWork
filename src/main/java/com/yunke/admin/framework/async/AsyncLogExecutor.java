package com.yunke.admin.framework.async;

import com.yunke.admin.modular.monitor.log.model.entity.LogOper;
import com.yunke.admin.modular.monitor.log.model.entity.LogLogin;
import com.yunke.admin.modular.monitor.log.service.LogOperService;
import com.yunke.admin.modular.monitor.log.service.LogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncLogExecutor {

    @Autowired
    private LogOperService logOperService;

    @Autowired
    private LogLoginService logLoginService;


    @Async
    public void saveOpLog(LogOper logOper){
        logOperService.save(logOper);
    }

    @Async
    public void saveVisLog(LogLogin logLogin){
        logLoginService.save(logLogin);
    }

}
