package com.yunke.admin.modular.monitor.server.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yunke.admin.common.base.BaseController;
import com.yunke.admin.framework.core.annotion.OpLog;
import com.yunke.admin.framework.core.enums.OpLogAnnotionOpTypeEnum;
import com.yunke.admin.framework.core.response.ResponseData;
import com.yunke.admin.framework.core.response.SuccessResponseData;
import com.yunke.admin.modular.monitor.server.model.vo.ServerVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/monitor/server/")
public class ServerController extends BaseController {

    @OpLog(title = "查询服务器信息",opType = OpLogAnnotionOpTypeEnum.QUERY)
    @SaCheckPermission("monitor:server:list")
    @GetMapping(value = "getServerInfo")
    public ResponseData getServerInfo(){
        ServerVO server = new ServerVO();
        server.copyTo();
        return new SuccessResponseData(server);
    }

}
