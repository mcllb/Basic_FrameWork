package com.yunke.admin.modular.system.auth.service;

import com.yunke.admin.modular.system.auth.enums.LoginDeviceEnum;
import com.yunke.admin.modular.system.auth.model.param.LoginParam;
import com.yunke.admin.modular.system.auth.model.param.MockLoginParam;
import com.yunke.admin.modular.system.auth.model.param.WxLoginParam;
import com.yunke.admin.modular.system.auth.model.param.WxRegisterParam;
import com.yunke.admin.modular.system.auth.model.vo.UserTokenVO;
import com.yunke.admin.modular.system.auth.model.vo.WxUserTokenVO;

public interface AuthService {

    /**
     * @description: 账号密码登录
     * <p></p>
     * @param loginParam
     * @return com.yunke.admin.modular.system.auth.model.vo.UserTokenVO
     * @auth: tianlei
     * @date: 2026/1/15 20:26
     */
    UserTokenVO login(LoginParam loginParam);

    UserTokenVO mockLogin(MockLoginParam mockLoginParam);

    void logout(LoginDeviceEnum loginDevice);

    /**
     * @description: 微信用户登录
     * <p></p>
     * @param loginParam 登录请求参数
     * @return WxUserTokenVO
     * @auth: tianlei
     * @date: 2026/1/22 09:58
     */
    WxUserTokenVO wxLogin(WxLoginParam loginParam);

    /**
     * @description: 微信用户注册
     * <p></p>
     * @param registerParam
     * @return WxUserTokenVO
     * @auth: tianlei
     * @date: 2026/2/2 09:50
     */
    WxUserTokenVO wxRegister(WxRegisterParam registerParam);

}
