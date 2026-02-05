package com.yunke.admin.modular.monitor.online.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.yunke.admin.common.base.BaseController;
import com.yunke.admin.common.constant.CommonConstant;
import com.yunke.admin.common.util.BeanUtil;
import com.yunke.admin.common.util.SaUtil;
import com.yunke.admin.framework.core.annotion.OpLog;
import com.yunke.admin.framework.core.annotion.OpLogHeader;
import com.yunke.admin.framework.core.enums.OpLogAnnotionOpTypeEnum;
import com.yunke.admin.framework.core.exception.ServiceException;
import com.yunke.admin.framework.core.response.ResponseData;
import com.yunke.admin.framework.core.response.SuccessResponseData;
import com.yunke.admin.modular.monitor.online.model.param.UserOnlineQueryParam;
import com.yunke.admin.modular.monitor.online.model.vo.UserOnlineVO;
import com.yunke.admin.modular.monitor.online.service.UserOnlineService;
import com.yunke.admin.modular.system.auth.model.vo.LoginUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@OpLogHeader("在线用户")
@RestController
@RequestMapping("/monitor/online/")
@Validated
public class UserOnlineController extends BaseController {
    @Autowired
    private UserOnlineService userOnlineService;

    @OpLog(title = "查询列表", opType = OpLogAnnotionOpTypeEnum.QUERY)
    @SaCheckPermission("monitor:online:list")
    @PostMapping(value = "list")
    public ResponseData list(@RequestBody UserOnlineQueryParam queryParam) {

        return new SuccessResponseData(userOnlineService.queryUserOnlineList(queryParam));
    }


    @OpLog(title = "踢出",opType = OpLogAnnotionOpTypeEnum.OTHER)
    //@SaCheckPermission("monitor:online:kickout")
    @SaCheckRole("admin")
    @GetMapping(value = "kikcout")
    public ResponseData kikcout(@NotEmpty(message = "登录令牌不能为空，请检查参数token") String token){
        if(token.equals(StpUtil.getTokenValue())){
            throw new ServiceException("不能踢出自身");
        }
        String kikcLoginId = Convert.toStr(StpUtil.getLoginIdByToken(token));
        if(CommonConstant.ADMIN_ID.equals(kikcLoginId)){
            if(!SaUtil.isAdmin()){
                throw new ServiceException("非admin用户禁止踢出admin账号");
            }
        }
        StpUtil.kickoutByTokenValue(token);
        return ResponseData.bool(true);
    }
}
