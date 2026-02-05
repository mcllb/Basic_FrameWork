package com.yunke.admin.framework.async;

import com.yunke.admin.common.util.ServletUtil;
import com.yunke.admin.framework.core.util.ThreadUtil;
import com.yunke.admin.framework.request.RequestWrapper;
import org.springframework.core.task.TaskDecorator;

import javax.servlet.http.HttpServletRequest;

public class RequestDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable runnable) {
        HttpServletRequest request = ServletUtil.getRequest();
        HttpServletRequest ttlRequest = new RequestWrapper(request);
        return () -> {
            try {
                ThreadUtil.shareRequest(ttlRequest);
                runnable.run();
            } finally {
                ThreadUtil.removeRequest();
                ThreadUtil.remove();
            }
        };
    }
}
