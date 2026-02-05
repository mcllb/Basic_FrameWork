package com.yunke.admin.modular.xxljob.jobhandler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.yunke.admin.framework.job.SysLogClear;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @className SysLogJob
 * @description: 系统日志Job
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Component
public class SysLogJob {

    @Autowired
    private SysLogClear sysLogClear;

    @XxlJob("deleteVisLog")
    public void deleteVisLog() throws Exception{
        sysLogClear.clearVisLog();
        XxlJobHelper.handleSuccess();
    }

    @XxlJob("deleteOperLog")
    public void deleteOperLog() throws Exception{
        sysLogClear.clearOperLog();
        XxlJobHelper.handleSuccess();
    }

}
