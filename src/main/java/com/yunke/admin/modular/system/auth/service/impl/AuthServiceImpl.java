package com.yunke.admin.modular.system.auth.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yunke.admin.common.constant.CommonConstant;
import com.yunke.admin.common.enums.AuthExceptionEnum;
import com.yunke.admin.common.enums.CommonStatusEnum;
import com.yunke.admin.common.util.*;
import com.yunke.admin.framework.cache.MockLoginTokenCache;
import com.yunke.admin.framework.config.ProjectConfig;
import com.yunke.admin.framework.core.exception.AuthException;
import com.yunke.admin.framework.core.exception.ServiceException;
import com.yunke.admin.framework.core.util.PasswordHelper;
import com.yunke.admin.framework.core.util.SysConfigUtil;
import com.yunke.admin.modular.business.customer.model.entity.CustomerInfo;
import com.yunke.admin.modular.business.customer.service.CustomerInfoService;
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
import com.yunke.admin.modular.system.dept.model.entity.Dept;
import com.yunke.admin.modular.system.dept.service.DeptService;
import com.yunke.admin.modular.system.permission.service.PermissionService;
import com.yunke.admin.modular.system.role.model.entity.Role;
import com.yunke.admin.modular.system.role.service.RoleService;
import com.yunke.admin.modular.system.user.enums.UserMockLoginEnum;
import com.yunke.admin.modular.system.user.model.entity.User;
import com.yunke.admin.modular.system.user.model.entity.UserRole;
import com.yunke.admin.modular.system.user.service.UserRoleService;
import com.yunke.admin.modular.system.user.service.UserService;
import com.yunke.admin.modular.system.wx.enums.WxUserTypeEnum;
import com.yunke.admin.modular.system.wx.model.entity.WxUser;
import com.yunke.admin.modular.system.wx.service.WxUserService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordHelper passwordHelper;
    @Autowired
    private ProjectConfig projectConfig;
    @Autowired
    private MockLoginTokenCache mockLoginTokenCache;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private DeptService deptService;
    @Autowired
    private CaptchaService captchaService;
    @Autowired
    private WxUserService wxUserService;
    @Autowired
    private CustomerInfoService customerInfoService;
    @Autowired
    private WxMaService maService;

    private void checkCaptcha(LoginParam loginParam){
        ProjectConfig projectConfig = SysConfigUtil.getProjectConfig();
        if(!projectConfig.isCaptchaEnable()){
            return;
        }
        if(StrUtil.isEmpty(loginParam.getVerCode()) || StrUtil.isEmpty(loginParam.getVerKey())){
            throw new AuthException(AuthExceptionEnum.VER_CODE_PARAM_LOSS);
        }

        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCaptchaVerification(loginParam.getVerCode());

        try{
            ResponseModel responseModel = captchaService.verification(captchaVO);
            if(!responseModel.isSuccess()){
                throw new AuthException(AuthExceptionEnum.VER_CODE_CHECK_FAIL);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new AuthException(AuthExceptionEnum.CHECK_VER_CODE_ERROR);
        }
    }


    @Override
    public UserTokenVO login(LoginParam loginParam) {
        // 校验验证码
        checkCaptcha(loginParam);
        User user = userService.getUserByAccount(loginParam.getAccount());
        // 账号不存在
        if(user == null){
            throw new AuthException(AuthExceptionEnum.ACCOUNT_NOT_EXIST);
        }
        // 账号状态异常
        if(!user.getStatus().equals(CommonStatusEnum.ENABLE.getCode())){
            throw new AuthException(AuthExceptionEnum.ACCOUNT_STATUS_EXCEPTION);
        }
        // 验证账号密码
        String password = Base64.decodeStr(loginParam.getPassword());
        if(!passwordHelper.verifyPassword(user,password)){
            throw new AuthException(AuthExceptionEnum.PWD_ERROR);
        }
        // 验证账号是否有被授权
        if(!SaUtil.isAdmin(user.getId())){
            if(!this.checkHasGrantRole(user.getId())){
                throw new AuthException(AuthExceptionEnum.USER_AUTH_ERROR);
            }
        }
        return doLoginSuccess(user);
    }

    @Override
    public UserTokenVO mockLogin(MockLoginParam mockLoginParam) {
        // 全局模拟登陆未启用
        if(!projectConfig.isEnableMockLogin()){
            throw new AuthException(AuthExceptionEnum.MOCK_LOGIN_DISABLE);
        }
        // 校验token
        String account = mockLoginTokenCache.get(mockLoginParam.getToken());
        if(StrUtil.isEmpty(account)){
            // token错误或者已过期
            throw new AuthException(AuthExceptionEnum.REQUEST_TOKEN_ERROR);
        }
        // 校验登陆账号
        if(!account.equals(mockLoginParam.getAccount())){
            throw new AuthException(AuthExceptionEnum.ACCOUNT_TOKEN_UN_MATCH);
        }
        User user = userService.getUserByAccount(mockLoginParam.getAccount());
        // 账号不存在
        if(user == null){
            throw new AuthException(AuthExceptionEnum.ACCOUNT_NOT_EXIST);
        }
        // 账号模拟登陆未启用
        if(!user.getEnableMockLogin().equals(UserMockLoginEnum.ENABLE.getCode())){
            throw new AuthException(AuthExceptionEnum.ACCOUNT_MOCK_LOGIN_DISABLE);
        }

        // 账号状态异常
        if(!user.getStatus().equals(CommonStatusEnum.ENABLE.getCode())){
            throw new AuthException(AuthExceptionEnum.ACCOUNT_STATUS_EXCEPTION);
        }
        // 验证账号是否有被授权
        if(!SaUtil.isAdmin(user.getId())){
            if(!this.checkHasGrantRole(user.getId())){
                throw new AuthException(AuthExceptionEnum.USER_AUTH_ERROR);
            }
        }
        return doLoginSuccess(user);
    }

    private UserTokenVO doLoginSuccess(User user){
        StpUtil.login(user.getId(), LoginDeviceEnum.PC.getCode());
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();

        LoginUserVO loginUser = new LoginUserVO();
        BeanUtil.copyProperties(user,loginUser);
        // 设置部门信息
        Dept dept = deptService.getById(user.getDeptId());
        loginUser.setDeptName(dept.getDeptName());
        loginUser.setDeptCode(dept.getDeptCode());
        // 设置登录信息
        String ip = ServletUtil.getClientIP(ServletUtil.getRequest());
        loginUser.setLoginIp(ip);
        String location = AddressUtil.getCityInfo(ip);
        loginUser.setLoginLocation(location);
        String browser = UaUtil.getBrowser(ServletUtil.getRequest());
        loginUser.setBrowser(browser);
        String os = UaUtil.getOs(ServletUtil.getRequest());
        loginUser.setOs(os);
        loginUser.setLoginTime(new Date());
        loginUser.setLastOperateTime(new Date());
        loginUser.setToken(tokenInfo.getTokenValue());
        // 存入用户路由信息
        loginUser.setRouters(permissionService.getUserMenus(loginUser));

        SaSession tokenSession = StpUtil.getTokenSession();
        tokenSession.set(CommonConstant.LOGIN_USER_SESSION_KEY,loginUser);

        UserTokenVO userInfoVO = new UserTokenVO();
        userInfoVO.setAccount(user.getAccount());
        userInfoVO.setUserName(user.getUserName());
        userInfoVO.setTokenValue(tokenInfo.getTokenValue());
        userInfoVO.setTokenName(tokenInfo.getTokenName());
        userInfoVO.setLoginId(tokenInfo.getLoginId());
        userInfoVO.setLoginType(tokenInfo.getLoginType());
        userInfoVO.setLoginDevice(tokenInfo.getLoginDevice());
        userInfoVO.setTag(tokenInfo.getTag());
        return userInfoVO;
    }

    @Override
    public void logout(LoginDeviceEnum loginDevice) {
        StpUtil.logout(loginDevice.getCode());
    }

    @Override
    public WxUserTokenVO wxLogin(WxLoginParam loginParam) {
        Console.log("微信用户登录: loginParam={}",loginParam);
        try {
            WxMaJscode2SessionResult sessionInfo = maService.getUserService().getSessionInfo(loginParam.getCode());
            log.info("sessionInfo ===> {}",sessionInfo);
            String openid = sessionInfo.getOpenid();
            WxUserTokenVO wxUserTokenVO = new WxUserTokenVO();
            WxUser wxUser = wxUserService.getEnabledUserById(openid);
            if(wxUser == null){
                log.info("该用户未注册 openid: {}",openid);
                wxUserTokenVO.setLoginId(openid);
                wxUserTokenVO.setSessionKey(sessionInfo.getSessionKey());
                return wxUserTokenVO;
            }
            log.info("匹配到微信用户，允许登录，openid={}",wxUser.getId());
            return this.doWxLoginSuccess(wxUser,wxUserTokenVO);

        } catch (WxErrorException e) {
            throw new ServiceException("获取微信用户session失败");
        }
    }

    @Override
    public WxUserTokenVO wxRegister(WxRegisterParam registerParam) {
        Console.log("微信用户注册: registerParam={}",registerParam);
        try {
            WxMaPhoneNumberInfo phoneNumberInfo = maService.getUserService().getPhoneNumber(registerParam.getCode());
            Console.log("phoneNumberInfo: {}",phoneNumberInfo);
            String phoneNumber = phoneNumberInfo.getPhoneNumber();
            if(StrUtil.isBlank(phoneNumber)){
                log.error("未获取到微信用户手机号，code={},openId={},",registerParam.getCode(),registerParam.getOpenId());
                throw new ServiceException("未获取到微信用户手机号");
            }
            WxUser registerUser = null;
            //注册账号,先从企业用户里查,再从系统用户里查
            CustomerInfo customerInfo = customerInfoService.lambdaQuery()
                    .eq(CustomerInfo::getPhone, phoneNumber)
                    .one();
            if(customerInfo == null){
                User user = userService.lambdaQuery()
                        .eq(User::getPhone, phoneNumber)
                        .one();
                if(user == null){
                    throw new AuthException(AuthExceptionEnum.WX_USER_NOT_EXIST);
                }
                //注册系统用户
                registerUser = new WxUser();
                registerUser.setId(registerParam.getOpenId());
                registerUser.setPhone(phoneNumber);
                registerUser.setUserType(WxUserTypeEnum.SYS.getCode());
                registerUser.setUserId(user.getId());
                registerUser.setRegisterTime(new Date());
                wxUserService.register(registerUser);
                log.info("从系统用户中注册微信用户，registerUser={}",registerUser);
            }else{
                //注册企业用户
                registerUser = new WxUser();
                registerUser.setId(registerParam.getOpenId());
                registerUser.setPhone(phoneNumber);
                registerUser.setUserType(WxUserTypeEnum.CUST.getCode());
                registerUser.setUserId(customerInfo.getId());
                registerUser.setRegisterTime(new Date());
                wxUserService.register(registerUser);
                log.info("从业务客户表中注册微信用户，registerUser={}",registerUser);
            }
            WxUserTokenVO wxUserTokenVO = new WxUserTokenVO();
            return doWxLoginSuccess(registerUser,wxUserTokenVO);
        } catch (WxErrorException e) {
            log.error("获取微信用户手机号发生异常：",e.getMessage());
            throw new ServiceException("服务端获取微信用户手机号发生异常");
        }
    }

    private WxUserTokenVO doWxLoginSuccess(WxUser wxUser,WxUserTokenVO wxUserTokenVO){
        StpUtil.login(wxUser.getId(),LoginDeviceEnum.WX.getCode());
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();

        WxLoginUserVO loginUser = new WxLoginUserVO();
        loginUser.setId(wxUser.getId());
        loginUser.setAccount(wxUser.getPhone());
        String userId = wxUser.getUserId();
        loginUser.setUserId(userId);
        loginUser.setUserType(wxUser.getUserType());
        String userName = null;
        String deptName = null;
        String deptCode = null;
        String deptId = null;
        if(wxUser.getUserType().equals(WxUserTypeEnum.SYS.getCode())){
            User user = userService.getById(userId);
            if(user != null){
                userName = user.getUserName();
                Dept dept = deptService.getById(user.getDeptId());
                deptName = dept.getDeptName();
                deptCode = dept.getDeptCode();
                deptId = dept.getId();
            }
        }else if(wxUser.getUserType().equals(WxUserTypeEnum.CUST.getCode())){
            CustomerInfo customerInfo = customerInfoService.getById(userId);
            if(customerInfo != null){
                userName = customerInfo.getName();
                deptName = customerInfo.getCompanyName();
                deptCode = customerInfo.getId();
                deptId = customerInfo.getId();
            }
        }
        loginUser.setUserName(userName);
        // 设置部门信息
        loginUser.setDeptName(deptName);
        loginUser.setDeptCode(deptCode);
        loginUser.setDeptId(deptId);

        // 设置登录信息
        String ip = ServletUtil.getClientIP(ServletUtil.getRequest());
        loginUser.setLoginIp(ip);
        String location = AddressUtil.getCityInfo(ip);
        loginUser.setLoginLocation(location);
        String browser = UaUtil.getBrowser(ServletUtil.getRequest());
        loginUser.setBrowser(browser);
        String os = UaUtil.getOs(ServletUtil.getRequest());
        loginUser.setOs(os);
        loginUser.setLoginTime(new Date());
        loginUser.setLastOperateTime(new Date());
        loginUser.setToken(tokenInfo.getTokenValue());

        SaSession tokenSession = StpUtil.getTokenSession();
        tokenSession.set(CommonConstant.WX_LOGIN_USER_SESSION_KEY,loginUser);
        //返回token对象
        wxUserTokenVO.setUserName(loginUser.getUserName());
        wxUserTokenVO.setUserType(wxUser.getUserType());
        wxUserTokenVO.setTokenValue(tokenInfo.getTokenValue());
        wxUserTokenVO.setTokenName(tokenInfo.getTokenName());
        wxUserTokenVO.setLoginId(tokenInfo.getLoginId());
        wxUserTokenVO.setLoginType(tokenInfo.getLoginType());
        wxUserTokenVO.setLoginDevice(tokenInfo.getLoginDevice());
        wxUserTokenVO.setTag(tokenInfo.getTag());
        return wxUserTokenVO;
    }

    public boolean checkHasGrantRole(String userId){
        LambdaQueryWrapper<Role> roleLambdaQueryWrapper = new LambdaQueryWrapper<Role>();
        roleLambdaQueryWrapper.eq(Role::getStatus,CommonStatusEnum.ENABLE.getCode());
        Set<String> roleIds = roleService.list(roleLambdaQueryWrapper).stream().map(Role::getId).collect(Collectors.toSet());
        if(roleIds == null){
            roleIds = CollUtil.newHashSet();
        }
        LambdaQueryWrapper<UserRole> userRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userRoleLambdaQueryWrapper.eq(UserRole::getUserId,userId);
        userRoleLambdaQueryWrapper.in(UserRole::getRoleId, roleIds);
        int count = userRoleService.count(userRoleLambdaQueryWrapper);
        return count > 0;
    }

}
