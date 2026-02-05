package com.yunke.admin.modular.system.auth.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.yunke.admin.common.base.BaseController;
import com.yunke.admin.common.enums.AuthExceptionEnum;
import com.yunke.admin.common.util.BeanUtil;
import com.yunke.admin.common.util.SaUtil;
import com.yunke.admin.common.util.StrUtil;
import com.yunke.admin.framework.core.response.ErrorResponseData;
import com.yunke.admin.framework.core.response.ResponseData;
import com.yunke.admin.framework.core.response.SuccessResponseData;
import com.yunke.admin.modular.system.auth.enums.LoginDeviceEnum;
import com.yunke.admin.modular.system.auth.model.param.LoginParam;
import com.yunke.admin.modular.system.auth.model.param.MockLoginParam;
import com.yunke.admin.modular.system.auth.model.param.WxLoginParam;
import com.yunke.admin.modular.system.auth.model.param.WxRegisterParam;
import com.yunke.admin.modular.system.auth.model.vo.LoginUserVO;
import com.yunke.admin.modular.system.auth.model.vo.UserTokenVO;
import com.yunke.admin.modular.system.auth.model.vo.WxLoginUserVO;
import com.yunke.admin.modular.system.auth.model.vo.WxUserTokenVO;
import com.yunke.admin.modular.system.auth.service.AuthService;
import com.yunke.admin.modular.system.permission.model.vo.RouterVo;
import com.yunke.admin.modular.system.user.model.vo.UserInfoVO;
import com.yunke.admin.modular.system.user.model.vo.WxUserInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * @className AuthController
 * @description: 用户认证控制器
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Api(tags = "微信小程序登录接口")
@ApiSupport(author = "tianlei",order = 1)
@RestController
public class AuthController extends BaseController {

    @Autowired
    private AuthService authService;



    @PostMapping("auth/login")
    public ResponseData<UserTokenVO> login(@Validated @RequestBody LoginParam loginParam) {
        UserTokenVO  userTokenVO = authService.login(loginParam);
        return ResponseData.success(userTokenVO);
    }

    @PostMapping("auth/mocklogin")
    public ResponseData<UserTokenVO> mocklogin(@Validated @RequestBody MockLoginParam mockLoginParam) {
        UserTokenVO  userTokenVO = authService.mockLogin(mockLoginParam);
        return ResponseData.success(userTokenVO);
    }

    @PostMapping("auth/logout")
    public ResponseData logout() {
        authService.logout(LoginDeviceEnum.PC);
        return ResponseData.success();
    }

    @GetMapping(value = "auth/getUserInfo")
    public ResponseData<UserInfoVO> getUserInfo(){
        UserInfoVO userInfoVO = new UserInfoVO();
        LoginUserVO loginUser = SaUtil.getLoginUser();
        BeanUtil.copyProperties(loginUser,userInfoVO);
        return new SuccessResponseData<>(userInfoVO);
    }

    @GetMapping(value = "auth/getUserRoleCodes")
    public ResponseData getUserRoleCodes(){
        return new SuccessResponseData<>(SaUtil.getRoleCodeList());
    }

    @GetMapping(value = "auth/getUserPermissionCodes")
    public ResponseData<Set<String>> getUserPermissionCodes(){
        return new SuccessResponseData<>(SaUtil.getPermissionCodeList());

    }


    @GetMapping(value = "auth/getRouters")
    public ResponseData<List<RouterVo>> getRouters(){
        return new SuccessResponseData<>(SaUtil.getRoutes());
    }

    /********************************  微信端登录接口 ***********************************/

    @ApiOperation(value = "微信用户登录",position = 1)
    @PostMapping("auth/wxLogin")
    public ResponseData<WxUserTokenVO> wxLogin(@Validated @RequestBody WxLoginParam loginParam) {
        WxUserTokenVO  userTokenVO = authService.wxLogin(loginParam);
        if(StrUtil.isEmpty(userTokenVO.getTokenValue())){
            ResponseData responseData = new ErrorResponseData(AuthExceptionEnum.WX_OPID_NOT_EXIST);
            responseData.setData(userTokenVO);
            return responseData;
        }
        return ResponseData.success(userTokenVO);
    }

    @ApiOperation(value = "微信用户注册",position = 2)
    @PostMapping("auth/wxRegister")
    public ResponseData<WxUserTokenVO> wxRegister(@Validated @RequestBody WxRegisterParam registerParam) {
        WxUserTokenVO  userTokenVO = authService.wxRegister(registerParam);
        return ResponseData.success(userTokenVO);
    }



    @ApiOperation(value = "微信用户退出登录",position = 3)
    @PostMapping("auth/wxLogout")
    public ResponseData wxLogout() {
        authService.logout(LoginDeviceEnum.PC);
        return ResponseData.success();
    }

    @ApiOperation(value = "微信用户获取用户信息",position = 4)
    @GetMapping(value = "auth/getWxUserInfo")
    public ResponseData<WxUserInfoVO> getWxUserInfo(){
        WxUserInfoVO userInfoVO = new WxUserInfoVO();
        WxLoginUserVO loginUser = SaUtil.getWxLoginUser();
        BeanUtil.copyProperties(loginUser,userInfoVO);
        userInfoVO.setId(loginUser.getUserId());
        return new SuccessResponseData<>(userInfoVO);
    }



}
