package com.yunke.admin.framework.core.util;

import com.yunke.admin.common.constant.CommonConstant;
import com.yunke.admin.common.util.AddressUtil;
import com.yunke.admin.common.util.ServletUtil;
import com.yunke.admin.common.util.UaUtil;
import com.yunke.admin.modular.monitor.log.model.entity.LogAbstract;

public class LogAbstractUtil {

    public static void addRequestInfoToLog(LogAbstract logAbstract){
        // 设置请求方式
        logAbstract.setReqMethod(ServletUtil.getRequest().getMethod());
        // 设置url
        logAbstract.setUrl(ServletUtil.getRequest().getRequestURI());
        // 设置IP
        String ip = ServletUtil.getClientIP(ServletUtil.getRequest());
        logAbstract.setIp(ip);
        // 设置地址
        String location = AddressUtil.getCityInfo(ip);
        logAbstract.setLocation(location);
        // 设置用户代理
        String userAgent = ServletUtil.getHeaderIgnoreCase(ServletUtil.getRequest(), CommonConstant.USER_AGENT);
        logAbstract.setUserAgent(userAgent);
        // 设置浏览器信息
        String browser = UaUtil.getBrowser(ServletUtil.getRequest());
        logAbstract.setBrowser(browser);
        // 设置操作系统
        String os = UaUtil.getOs(ServletUtil.getRequest());
        logAbstract.setOs(os);

    }
}
