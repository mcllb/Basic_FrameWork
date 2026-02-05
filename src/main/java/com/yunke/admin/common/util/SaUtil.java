package com.yunke.admin.common.util;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.yunke.admin.common.constant.CommonConstant;
import com.yunke.admin.framework.cache.RolePermissionCache;
import com.yunke.admin.framework.cache.UserRoleCache;
import com.yunke.admin.modular.system.auth.enums.LoginDeviceEnum;
import com.yunke.admin.modular.system.auth.model.vo.LoginUserVO;
import com.yunke.admin.modular.system.auth.model.vo.WxLoginUserVO;
import com.yunke.admin.modular.system.permission.model.entity.Permission;
import com.yunke.admin.modular.system.permission.model.vo.RouterVo;
import com.yunke.admin.modular.system.role.model.entity.Role;
import com.yunke.admin.modular.system.wx.enums.WxUserTypeEnum;
import javafx.scene.layout.StackPane;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @className SaUtil
 * @description: 登录用户信息获取工具类（请勿修改）
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/26
 */
public class SaUtil {

    private SaUtil() {

    }

    /**
     * @description: 获取登录用户信息 （可能存在缓存不是最新的）
     * <p></p>
     * @return LoginUserVO
     * @auth: tianlei
     * @date: 2026/1/22 17:24
     */
    public static LoginUserVO getLoginUser() {

        SaSession tokenSession = StpUtil.getTokenSession();
        if(tokenSession == null){
            return null;
        }
        LoginUserVO loginUser = tokenSession.getModel(CommonConstant.LOGIN_USER_SESSION_KEY, LoginUserVO.class);
        if(loginUser != null){
            loginUser.setRoles(getRoleCodeList());
            loginUser.setPermissions(getPermissionCodeList());
        }
        return loginUser;
    }

    /**
     * @description: 获取微信登录用户信息 （可能存在缓存不是最新的）
     * <p></p>
     * @return WxLoginUserVO
     * @auth: tianlei
     * @date: 2026/1/22 17:24
     */
    public static WxLoginUserVO getWxLoginUser() {

        SaSession tokenSession = StpUtil.getTokenSession();
        if(tokenSession == null){
            return null;
        }
        WxLoginUserVO loginUser = tokenSession.getModel(CommonConstant.WX_LOGIN_USER_SESSION_KEY, WxLoginUserVO.class);
        return loginUser;
    }

    public static List<Role> getRoleList() {
        if(SaUtil.isWxLogin()){
            if(SaUtil.isCustWxLogin()){
                return CollUtil.newArrayList();
            }
            UserRoleCache userRoleCache = SpringUtil.getBean(UserRoleCache.class);
            return userRoleCache.get(SaUtil.getWxLoginUser().getUserId());
        }else{
            UserRoleCache userRoleCache = SpringUtil.getBean(UserRoleCache.class);
            List<Role> roles = userRoleCache.get(SaUtil.getLoginId());
            return roles;
        }
    }

    public static List<Permission> getPermissionList() {
        if(SaUtil.isCustWxLogin()){
            return CollUtil.newArrayList();
        }
        List<Permission> permissions = CollUtil.newArrayList();
        List<Role> roleList = getRoleList();
        if (CollUtil.isNotEmpty(roleList)) {
            RolePermissionCache rolePermissionCache = SpringUtil.getBean(RolePermissionCache.class);
            roleList.forEach(role -> {
                List<Permission> permissionList = rolePermissionCache.get(role.getId());
                if(CollUtil.isNotEmpty(permissionList)){
                    permissions.addAll(permissionList);
                }
            });
        }
        return permissions;
    }

    public static List<String> getPermissionCodeList() {
        List<String> permissionCodes = CollUtil.newArrayList();
        List<Permission> permissionList = getPermissionList();
        if (CollUtil.isNotEmpty(permissionList)) {
            permissionCodes = permissionList.stream().map(Permission::getPermissionCode).collect(Collectors.toList());
        }
        return permissionCodes;
    }

    ;

    public static List<String> getRoleCodeList() {
        List<String> roleCodes = CollUtil.newArrayList();
        List<Role> roleList = getRoleList();
        if (CollUtil.isNotEmpty(roleList)) {
            roleCodes = roleList.stream().map(Role::getRoleCode).collect(Collectors.toList());
        }
        return roleCodes;
    }

    public static List<String> getRoleIdList() {
        List<String> roleIds = CollUtil.newArrayList();
        List<Role> roleList = getRoleList();
        if (CollUtil.isNotEmpty(roleList)) {
            roleIds = roleList.stream().map(Role::getId).collect(Collectors.toList());
        }
        return roleIds;
    }

    /**
     * @description: 校验当前登录用户是否有某个角色
     * <p></p>
     * @param roleCode 角色编码
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/21 17:44
     */
    public static boolean checkRole(String roleCode) {
        return getRoleCodeList().contains(roleCode);
    }

    /**
     * @description: 校验当前用户是否有某个权限
     * <p></p>
     * @param permissionCode 权限编码
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/21 17:45
     */
    public static boolean checkPermission(String permissionCode) {
        return getPermissionCodeList().contains(permissionCode);
    }


    public static List<RouterVo> getRoutes() {
        if (ObjectUtil.isNotNull(getLoginUser())) {
            return CollUtil.newArrayList(getLoginUser().getRouters());
        }
        return CollUtil.newArrayList();
    }

    public static String getLoginId() {
        if(isLogin()){
            return StpUtil.getLoginIdAsString();
        }
        return null;
    }

    public static boolean isLogin() {
        return StpUtil.isLogin();
    }

    public static String getUserId() {
        if(isLogin()){
            if(isWxLogin()){
                return getWxLoginUser().getUserId();
            }
            return getLoginUser().getId();
        }
        return null;
    }

    public static String getUserName() {
        if(isLogin()){
            if(isWxLogin()){
                WxLoginUserVO wxLoginUser = getWxLoginUser();
                return wxLoginUser.getUserName();
            }
            return getLoginUser().getUserName();
        }
        return null;
    }

    public static String getUserType() {
        if(isLogin() && isWxLogin()){
            return getWxLoginUser().getUserType();
        }
        return null;
    }

    public static String getDeptId() {
        StpUtil.checkLogin();
        if(SaUtil.isWxLogin()){
            return getWxLoginUser().getDeptId();
        }
        return getLoginUser().getDeptId();
    }

    public static String getDeptCode() {
        StpUtil.checkLogin();
        if(SaUtil.isWxLogin()){
            return getWxLoginUser().getDeptCode();
        }
        return getLoginUser().getDeptCode();
    }

    public static String getDeptName() {
        StpUtil.checkLogin();
        if(SaUtil.isWxLogin()){
            return getWxLoginUser().getDeptName();
        }
        return getLoginUser().getDeptName();
    }

    public static String getAccount() {
        if(isLogin()){
            if(isWxLogin()){
                return getWxLoginUser().getAccount();
            }
            return getLoginUser().getAccount();
        }
        return null;
    }

    public static String getLoginDevice() {
        if(isLogin()){
            return StpUtil.getLoginDevice();
        }
        return null;
    }

    /**
     * @description: 是否微信用户登录
     * <p></p>
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/22 16:48
     */
    public static boolean isWxLogin() {
        return LoginDeviceEnum.WX.getCode().equals(getLoginDevice());
    }

    /**
     * @description: 是否系统微信用户登录
     * <p></p>
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/22 17:23
     */
    public static boolean isSysWxLogin() {
        if(isWxLogin()){
            WxLoginUserVO wxLoginUser = getWxLoginUser();
            if(wxLoginUser != null){
                return WxUserTypeEnum.SYS.getCode().equals(wxLoginUser.getUserType());
            }
        }
        return false;
    }

    /**
     * @description: 是否企业微信用户登录
     * <p></p>
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/22 17:23
     */
    public static boolean isCustWxLogin() {
        if(isWxLogin()){
            WxLoginUserVO wxLoginUser = getWxLoginUser();
            if(wxLoginUser != null){
                return WxUserTypeEnum.CUST.getCode().equals(wxLoginUser.getUserType());
            }
        }
        return false;
    }

    public static boolean isAdmin(LoginUserVO user) {
        if (user == null) {
            return false;
        }
        return CommonConstant.ADMIN_ACCOUNT.equals(user.getAccount());
    }

    public static boolean isAdmin() {
        return isAdmin(getLoginUser());
    }

    public static boolean isAdmin(String userId) {
        if (userId == null) {
            return false;
        }
        return CommonConstant.ADMIN_ACCOUNT.equals(userId);
    }

    public static SaTokenConfig getConfig() {
        return SaManager.getConfig();
    }

    /**
     * 获取token名称
     *
     * @return
     */
    public static String getTokenName() {
        return getConfig().getTokenName();
    }

    public static String getTokenValue(){
        return StpUtil.getTokenValue();
    }


    /**
     * 获取token前缀
     * 如Access-Token-YK:login:token:24efc409-94c9-4c21-a0a6-58b2752c637e的前缀为Access-Token-YK:login:token:
     *
     * @return
     */
    public static String getTokenKeyPrefix() {
        return getTokenName() + ":login:token:";
    }

    /**
     * 获取token-session前缀
     * 如Access-Token-YK:login:token-session:24efc409-94c9-4c21-a0a6-58b2752c637e的前缀为Access-Token-YK:login:token-session:
     *
     * @return
     */
    public static String getTokenSessionKeyPrefix() {
        return getTokenName() + ":login:token-session:";
    }

    /**
     * 获取session前缀
     * 如Access-Token-YK:login:session:admin的前缀为Access-Token-YK:login:session:
     *
     * @return
     */
    public static String getSessionKeyPrefix() {
        return getTokenName() + ":login:session:";
    }

    /**
     * 获取token最后操作时间前缀
     * 如Access-Token-YK:login:last-activity:5248022a-8680-4e65-b208-431e467a87ca的前缀为Access-Token-YK:login:last-activity:
     *
     * @return
     */
    public static String getLastActivityKeyPrefix() {
        return getTokenName() + ":login:last-activity:";
    }

    public static SaTokenDao getSaTokenDao() {
        SaTokenDao dao = SaManager.getSaTokenDao();
        return dao;
    }

    /**
     * 获取token场景值
     *
     * @param token
     * @return
     */
    public static String getTokenAbnormalValue(String token) {
        String value = getSaTokenDao().get(splicingKeyTokenValue(token));
        return value;
    }

    /**
     * 校验token是否异常
     *
     * @param token
     * @return
     */
    public static boolean checkTokenIsAbnormal(String token) {
        String value = getTokenAbnormalValue(token);
        if (StrUtil.isEmpty(value)) {
            return false;
        }
        return NotLoginException.ABNORMAL_LIST.contains(value);
    }

    // ------------------- 返回相应key -------------------

    /**
     * 拼接token key
     *
     * @param tokenValue
     * @return
     */
    public static String splicingKeyTokenValue(String tokenValue) {
        return getTokenKeyPrefix() + tokenValue;
    }

    /**
     * 拼接session key
     *
     * @param loginId
     * @return
     */
    public static String splicingKeySession(Object loginId) {
        return getSessionKeyPrefix() + loginId;
    }

    /**
     * 拼接token-session key
     *
     * @param tokenValue
     * @return
     */
    public static String splicingKeyTokenSession(String tokenValue) {
        return getTokenSessionKeyPrefix() + tokenValue;
    }

    /**
     * 拼接last-activity key
     *
     * @param tokenValue
     * @return
     */
    public static String splicingKeyLastActivityTime(String tokenValue) {
        return getLastActivityKeyPrefix() + tokenValue;
    }


}
