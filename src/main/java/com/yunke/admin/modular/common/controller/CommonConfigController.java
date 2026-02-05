package com.yunke.admin.modular.common.controller;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.annotation.SaIgnore;
import com.yunke.admin.common.base.BaseController;
import com.yunke.admin.framework.config.ProjectConfig;
import com.yunke.admin.framework.core.response.ResponseData;
import com.yunke.admin.framework.core.response.SuccessResponseData;
import com.yunke.admin.framework.core.util.SysConfigUtil;
import com.yunke.admin.modular.common.model.param.ConfigValueQueryParam;
import com.yunke.admin.modular.common.model.vo.SystemInfo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SaIgnore
@RestController
@RequestMapping(value = "/common/config/")
public class CommonConfigController extends BaseController {
    @GetMapping(value = "getSystemInfo")
    public ResponseData<SystemInfo> getSystemInfo(){
        SystemInfo systemInfo = new SystemInfo();
        ProjectConfig projectConfig = SysConfigUtil.getProjectConfig();
        systemInfo.setSystemName(projectConfig.getName());
        systemInfo.setVersion(projectConfig.getVersion());
        systemInfo.setCopyRight(projectConfig.getCopyRight());
        systemInfo.setCaptchaType(projectConfig.getCaptchaType());
        systemInfo.setCaptchaEnable(projectConfig.isCaptchaEnable());
        systemInfo.setTokenName(SaManager.getConfig().getTokenName());
        return new SuccessResponseData<SystemInfo>(systemInfo);
    }

    @GetMapping(value = "getConfigValue")
    public ResponseData<String> getConfigValue(@Validated ConfigValueQueryParam configValueQueryParam){
        String configValue = SysConfigUtil.getSysConfigValue(configValueQueryParam.getConfigKey(), String.class);
        return new SuccessResponseData<String>(configValue);
    }

}
