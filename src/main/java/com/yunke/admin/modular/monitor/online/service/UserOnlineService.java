package com.yunke.admin.modular.monitor.online.service;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.yunke.admin.common.constant.CommonConstant;
import com.yunke.admin.common.util.BeanUtil;
import com.yunke.admin.common.util.SaUtil;
import com.yunke.admin.modular.monitor.online.model.param.UserOnlineQueryParam;
import com.yunke.admin.modular.monitor.online.model.vo.UserOnlineVO;
import com.yunke.admin.modular.system.auth.enums.LoginDeviceEnum;
import com.yunke.admin.modular.system.auth.model.vo.LoginUserVO;
import com.yunke.admin.modular.system.auth.model.vo.WxLoginUserVO;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
  * @className UserOnlineService
  * @description 在线用户Service服务
  * <p></p>
  * @version 1.0
  * @author tianlei
  * @date 2026/2/4
  */
@Service
public class UserOnlineService {

    public List<UserOnlineVO> queryUserOnlineList(UserOnlineQueryParam queryParam) {

        List<UserOnlineVO> ret = CollUtil.newArrayList();

        // 查询所有token
        List<String> tokenValues = StpUtil.searchTokenValue("", 0, -1,false);

        for (String value : tokenValues) {
            String token = value.replace(SaUtil.getTokenKeyPrefix(), "");

            // 排除异常token
            if(SaUtil.checkTokenIsAbnormal(token)){
                continue;
            }

            // 排除已过临时有效期的token
            long tokenActivityTimeout = StpUtil.stpLogic.getTokenActiveTimeoutByToken(token);
            if(tokenActivityTimeout < 0 && tokenActivityTimeout != -1){
                continue;
            }

            SaSession tokenSession = StpUtil.stpLogic.getTokenSessionByToken(token,false);
            if(tokenSession != null){
                UserOnlineVO userOnlineVO = null;
                LoginUserVO loginUser = tokenSession.getModel(CommonConstant.LOGIN_USER_SESSION_KEY, LoginUserVO.class);
                if(loginUser != null){
                    userOnlineVO = new UserOnlineVO();
                    BeanUtil.copyProperties(loginUser,userOnlineVO);
                    userOnlineVO.setTokenSessionId(tokenSession.getId());
                    userOnlineVO.setDevice(LoginDeviceEnum.PC.getCode());
                }else{
                    WxLoginUserVO wxLoginUser = tokenSession.getModel(CommonConstant.WX_LOGIN_USER_SESSION_KEY, WxLoginUserVO.class);
                    if(wxLoginUser != null){
                        userOnlineVO = new UserOnlineVO();
                        BeanUtil.copyProperties(wxLoginUser,userOnlineVO);
                        userOnlineVO.setTokenSessionId(tokenSession.getId());
                        userOnlineVO.setDevice(LoginDeviceEnum.WX.getCode());
                    }
                }
                if(userOnlineVO != null){
                    ret.add(userOnlineVO);
                }
            }
        }

        if(StrUtil.isNotEmpty(queryParam.getUserName())) {
            ret = ret.stream().filter(user -> user.getUserName().contains(queryParam.getUserName())
            ).collect(Collectors.toList());
        }
        if(StrUtil.isNotEmpty(queryParam.getAccount())) {
            ret = ret.stream().filter(user -> user.getAccount().contains(queryParam.getAccount())
            ).collect(Collectors.toList());
        }
        if(StrUtil.isNotEmpty(queryParam.getDevice())) {
            ret = ret.stream().filter(user -> user.getDevice().contains(queryParam.getDevice())
            ).collect(Collectors.toList());
        }

        Collections.reverse(ret);
        ret.removeAll(Collections.singleton(null));
        BeanUtil.fillListEnumDictField(ret);
        return ret;
    }

}
