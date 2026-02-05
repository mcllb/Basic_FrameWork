package com.yunke.admin.modular.system.user.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yunke.admin.common.constant.CommonConstant;
import com.yunke.admin.common.constant.SysConfigKeyConstant;
import com.yunke.admin.common.enums.CommonStatusEnum;
import com.yunke.admin.common.model.MenuTreeNode;
import com.yunke.admin.common.model.SingleDeleteParam;
import com.yunke.admin.common.util.*;
import com.yunke.admin.framework.cache.base.CacheConstant;
import com.yunke.admin.framework.core.exception.ServiceException;
import com.yunke.admin.framework.core.util.PasswordHelper;
import com.yunke.admin.framework.core.util.SysConfigUtil;
import com.yunke.admin.framework.eventbus.event.CacheEvent;
import com.yunke.admin.framework.mybatisplus.base.GeneralServiceImpl;
import com.yunke.admin.modular.system.auth.model.vo.LoginUserVO;
import com.yunke.admin.modular.system.dept.model.entity.Dept;
import com.yunke.admin.modular.system.dept.service.DeptService;
import com.yunke.admin.modular.system.permission.model.entity.Permission;
import com.yunke.admin.modular.system.permission.service.PermissionService;
import com.yunke.admin.modular.system.role.model.entity.Role;
import com.yunke.admin.modular.system.role.model.entity.RolePermission;
import com.yunke.admin.modular.system.role.service.RolePermissionService;
import com.yunke.admin.modular.system.role.service.RoleService;
import com.yunke.admin.modular.system.user.enums.UserExceptionEnum;
import com.yunke.admin.modular.system.user.enums.UserSexEnum;
import com.yunke.admin.modular.system.user.mapper.UserMapper;
import com.yunke.admin.modular.system.user.model.entity.User;
import com.yunke.admin.modular.system.user.model.entity.UserRole;
import com.yunke.admin.modular.system.user.model.param.*;
import com.yunke.admin.modular.system.user.model.vo.UserInfoVO;
import com.yunke.admin.modular.system.user.model.vo.UserPageVO;
import com.yunke.admin.modular.system.user.service.UserRoleService;
import com.yunke.admin.modular.system.user.service.UserService;
import com.yunke.admin.modular.system.wx.event.DeleteSysUserEvent;
import com.yunke.admin.modular.system.wx.event.DisableSysUserEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @className UserServiceImpl
 * @description: 系统用户表service接口实现类
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Service
public class UserServiceImpl extends GeneralServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordHelper passwordHelper;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    RolePermissionService rolePermissionService;
    @Autowired
    @Lazy
    private DeptService deptService;

    @Override
    public User getUserByAccount(String account) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(User::getAccount, account);
        return baseMapper.selectOne(lambdaQueryWrapper);
    }

    @Override
    public Set<String> getUserRolesSetByAccount(String account) {
        Set<String> rolesSet = CollUtil.newHashSet();
        return rolesSet;
    }

    @Override
    public Set<String> getUserPermsSetByAccount(String account) {
        Set<String> permsSet = CollUtil.newHashSet();
        return permsSet;
    }

    @Override
    public Set<String> getLoginUserRoleCodesSet(LoginUserVO loginUser) {
        boolean enableAdminRole = SysConfigUtil.getProjectConfig().isEnableAdminRole();

        Set<String> roleCodes = CollUtil.newHashSet();
        if(loginUser.isAdmin() && !enableAdminRole){
            roleCodes.add(CommonConstant.ADMIN_ROLE_CODE);
        }else{
            // 非系统管理员查询用户与角色关联，
            LambdaQueryWrapper<UserRole> userRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userRoleLambdaQueryWrapper.eq(UserRole::getUserId,loginUser.getId());
            List<UserRole> userRoles = userRoleService.list(userRoleLambdaQueryWrapper);
            if(userRoles != null && userRoles.size() > 0){
                Set<String> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toSet());
                // 查询用户所拥有的角色
                LambdaQueryWrapper<Role> roleLambdaQueryWrapper = new LambdaQueryWrapper<>();
                roleLambdaQueryWrapper.eq(Role::getStatus,CommonStatusEnum.ENABLE.getCode());
                roleLambdaQueryWrapper.in(Role::getId,roleIds);
                List<Role> roles = roleService.list(roleLambdaQueryWrapper);
                roles.stream().forEach(role -> {
                    roleCodes.add(role.getRoleCode());
                });
            }
        }
        return roleCodes;
    }

    @Override
    public Set<String> getLoginUserPermissonCodesSet(LoginUserVO loginUser) {
        boolean enableAdminRole = SysConfigUtil.getProjectConfig().isEnableAdminRole();
        Set<String> permissionCodes = CollUtil.newHashSet();
        if(loginUser.isAdmin() && !enableAdminRole){
            // 系统管理员获取所有权限
            LambdaQueryWrapper<Permission> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Permission::getStatus,CommonStatusEnum.ENABLE.getCode());
            List<Permission> permissions = permissionService.list(lambdaQueryWrapper);
            permissions.stream().forEach(permission -> {
                permissionCodes.add(permission.getPermissionCode());
            });
        }else{
            // 查询用户与角色关联
            LambdaQueryWrapper<UserRole> userRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userRoleLambdaQueryWrapper.eq(UserRole::getUserId,loginUser.getId());
            List<UserRole> userRoles = userRoleService.list(userRoleLambdaQueryWrapper);
            if(userRoles != null && userRoles.size() > 0){
                Set<String> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toSet());
                // 查询用户所拥有的角色
                LambdaQueryWrapper<Role> roleLambdaQueryWrapper = new LambdaQueryWrapper<>();
                roleLambdaQueryWrapper.eq(Role::getStatus,CommonStatusEnum.ENABLE.getCode());
                roleLambdaQueryWrapper.in(Role::getId,roleIds);
                List<Role> roles = roleService.list(roleLambdaQueryWrapper);
                roleIds = roles.stream().map(Role::getId).collect(Collectors.toSet());
                if(roleIds != null && roleIds.size() > 0){
                    // 查询角色与权限关联
                    LambdaQueryWrapper<RolePermission> rolePermissionLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    rolePermissionLambdaQueryWrapper.in(RolePermission::getRoleId,roleIds);
                    List<RolePermission> rolePermissions = rolePermissionService.list(rolePermissionLambdaQueryWrapper);
                    if(rolePermissions != null && rolePermissions.size() > 0){
                        // 查询用户所拥有的权限
                        Set<String> permissionIds = rolePermissions.stream().map(RolePermission::getPermissionId).collect(Collectors.toSet());
                        List<Permission> permissions = permissionService.listByIds(permissionIds);
                        permissions.stream().forEach(permission -> {
                            permissionCodes.add(permission.getPermissionCode());
                        });
                    }
                }
            }
        }
        return permissionCodes;
    }

    @Override
    public List<MenuTreeNode> getLoginUserMenuTree(LoginUserVO loginUser) {
        return permissionService.userMenuTree(loginUser);
    }

    @Transactional
    @Override
    public boolean add(UserAddParam userAddParam) {
        User user = new User();
        BeanUtil.copyProperties(userAddParam,user);
        // 校验参数
        checkParam(user,false);
        user.setPassword(SysConfigUtil.getProjectConfig().getDefaultPassword());
        // 加密密码、盐
        passwordHelper.encryptPassword(user);
        // 设置状态启用
        user.setStatus(CommonStatusEnum.ENABLE.getCode());
        return this.save(user);
    }

    @Transactional
    @Override
    public boolean edit(UserEditParam userEditParam) {
        User user = new User();
        BeanUtil.copyProperties(userEditParam,user);
        // 校验参数
        checkParam(user,true);
        // 不修改状态
        user.setStatus(null);
        return this.updateById(user);
    }

    @Transactional
    @Override
    public boolean delete(SingleDeleteParam singleDeleteParam) {
        // 不能删除管理员账号
        User user = this.getById(singleDeleteParam.getId());
        if(user != null && user.getAccount().equals(CommonConstant.ADMIN_ACCOUNT)){
            throw new ServiceException(UserExceptionEnum.CAN_NOT_DELETE_ADMIN);
        }
        // 删除用户与角色关联
        LambdaQueryWrapper<UserRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserRole::getUserId, singleDeleteParam.getId());
        userRoleService.remove(lambdaQueryWrapper);
        boolean ret = this.removeById(singleDeleteParam.getId());
        if(ret){
            refreshUserRoleCache(singleDeleteParam.getId());
            SpringUtil.publishEventAsync(new DeleteSysUserEvent(singleDeleteParam.getId()));
        }
        return ret;
    }

    @Transactional
    @Override
    public boolean grantRole(UserGrantRoleParam userGrantRoleParam) {
        String userId = userGrantRoleParam.getUserId();
        // 删除现有角色
        LambdaQueryWrapper<UserRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserRole::getUserId, userId);
        userRoleService.remove(lambdaQueryWrapper);
        // 重新授权
        List<String> roleIdList = userGrantRoleParam.getRoleIdList();
        if(CollUtil.isNotEmpty(roleIdList)){
            List<UserRole> userRoleList = CollUtil.newArrayList();
            for(String roleId : roleIdList){
                UserRole userRole = new UserRole();
                userRole.setRoleId(roleId);
                userRole.setUserId(userId);
                userRoleList.add(userRole);
            }
            return userRoleService.saveBatch(userRoleList);
        }
        refreshUserRoleCache(userGrantRoleParam.getUserId());
        return true;
    }

    @Transactional
    @Override
    public boolean updateUserStatus(UserUpdateStatusParam userUpdateStatusParam) {
        boolean ret = this.lambdaUpdate()
                .set(User::getStatus,userUpdateStatusParam.getStatus())
                .set(User::getUpdateBy,SaUtil.getUserId())
                .set(User::getUpdateTime,new Date())
                .eq(User::getId,userUpdateStatusParam.getId())
                .update();
        if(ret){
            refreshUserRoleCache(userUpdateStatusParam.getId());
            if(CommonStatusEnum.DISABLE.getCode().equals(userUpdateStatusParam.getStatus())){
                SpringUtil.publishEventAsync(new DisableSysUserEvent(userUpdateStatusParam.getId()));
            }
        }
        return ret;
    }

    @Transactional
    @Override
    public boolean updateInfo(UserUpdateInfoParam userUpdateInfoParam) {
        User user = new User();
        BeanUtil.copyProperties(userUpdateInfoParam,user);
        this.checkPhoneUnique(user,true);
        this.checkEmailUnique(user,true);
        return this.lambdaUpdate()
                .set(User::getUserName,userUpdateInfoParam.getUserName())
                .set(User::getShortName,userUpdateInfoParam.getShortName())
                .set(User::getSex,userUpdateInfoParam.getSex())
                .set(User::getEmail,userUpdateInfoParam.getEmail())
                .set(User::getPhone,userUpdateInfoParam.getPhone())
                .set(User::getTelphone,userUpdateInfoParam.getTelphone())
                .set(User::getBirthday,userUpdateInfoParam.getBirthday())
                .set(User::getUpdateBy,SaUtil.getUserId())
                .set(User::getUpdateTime,new Date())
                .eq(User::getId,SaUtil.getUserId())
                .update();
    }

    @Transactional
    @Override
    public boolean updatePassword(UserUpdatePasswordParam userUpdatePasswordParam) {
        userUpdatePasswordParam.setId(SaUtil.getUserId());
        // 校验新密码与原密码不能相同
        if(userUpdatePasswordParam.getOldPassword().equals(userUpdatePasswordParam.getNewPassword())){
            throw new ServiceException(UserExceptionEnum.USER_OLDPWD_NEWPWD_SAME);
        }
        // 校验原密码是否正确
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(User::getId, User::getAccount,User::getPassword, User::getSalt);
        lambdaQueryWrapper.eq(User::getId, userUpdatePasswordParam.getId());
        User oldUser = this.getOne(lambdaQueryWrapper);
        if(!passwordHelper.verifyPassword(oldUser,userUpdatePasswordParam.getOldPassword())){
            throw new ServiceException(UserExceptionEnum.USER_OLDPWD_ERROR);
        }
        // 加密密码、盐
        User newUser = new User();
        newUser.setId(userUpdatePasswordParam.getId());
        newUser.setAccount(oldUser.getAccount());
        newUser.setPassword(userUpdatePasswordParam.getNewPassword());
        passwordHelper.encryptPassword(newUser);
        return this.updateById(newUser);
    }

    @Transactional
    @Override
    public boolean resetPassword(UserResetPasswordParam userResetPasswordParam) {
        String defaultPwd = SysConfigUtil.getSysConfigValue(SysConfigKeyConstant.SYSTEM_DEFAULT_PASSWORD, String.class);
        if(StrUtil.isBlank(defaultPwd)){
            defaultPwd = SysConfigUtil.getProjectConfig().getDefaultPassword();
        }
        User user = this.getById(userResetPasswordParam.getId());
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setAccount(user.getAccount());
        updateUser.setPassword(defaultPwd);
        // 加密密码、盐
        passwordHelper.encryptPassword(updateUser);
        return this.updateById(updateUser);
    }

    @Transactional
    @Override
    public synchronized boolean resetPasswordAll() {
        List<User> userList = this.lambdaQuery().select(User::getId)
                .ne(User::getAccount, "admin")
                .list();
        if(CollUtil.isNotEmpty(userList)){
            userList.parallelStream().forEach(user -> {
                UserResetPasswordParam userResetPasswordParam = new UserResetPasswordParam();
                userResetPasswordParam.setId(user.getId());
                resetPassword(userResetPasswordParam);
            });
        }
        return true;
    }

    @Override
    public UserInfoVO getUserInfoVOById(String id) {
        LoginUserVO loginUser = SaUtil.getLoginUser();
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtil.copyProperties(loginUser,userInfoVO);
        return userInfoVO;
    }

    @Override
    public void fillDeptName(UserInfoVO userInfoVO) {
        if(userInfoVO != null){
            Dept dept = deptService.getById(userInfoVO.getDeptId());
            userInfoVO.setDeptName(dept.getDeptName());
            userInfoVO.setDeptCode(dept.getDeptCode());
        }
    }

    @Override
    public void fillDeptName(UserPageVO userPageVO) {
        if(userPageVO != null){
            Dept dept = deptService.getById(userPageVO.getDeptId());
            userPageVO.setDeptName(dept.getDeptName());
        }
    }

    @Override
    public void fillDeptName(List<UserPageVO> userPageVOList) {
        if(userPageVOList != null && userPageVOList.size() > 0){
            userPageVOList.stream().forEach(userPageVO -> {
                fillDeptName(userPageVO);
            });
        }
    }

    @Override
    public String getPassword(String id) {
        User user = this.getById(id);
        if(user != null){
            return PasswordHelper.decryptAesPassword(user.getSalt(), user.getPassword());
        }
        return null;
    }

    @Override
    public UserInfoVO getCurrentUserInfo() {
        UserInfoVO userInfoVO = new UserInfoVO();
        User user = this.getById(SaUtil.getUserId());
        BeanUtil.copyProperties(user,userInfoVO);
        BeanUtil.fillDeptField(userInfoVO);
        return userInfoVO;
    }

    @Override
    public Tree<String> getOrgTree() {
        return deptService.tree().get(0);
    }


    public void checkParam(User user,boolean isExcludeSelf){
        // 校验用户账号是否重复
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getAccount, user.getAccount());
        if(isExcludeSelf){
            lambdaQueryWrapper.ne(User::getId, user.getId());
        }
        int countAccount = this.count(lambdaQueryWrapper);
        if(countAccount > 0){
            throw new ServiceException(UserExceptionEnum.USER_ACCOUNT_REPEAT);
        }
        this.checkPhoneUnique(user,isExcludeSelf);
        this.checkEmailUnique(user,isExcludeSelf);
    }

    @Override
    public void checkPhoneUnique(User user,boolean isExcludeSelf){
        if(StrUtil.isEmpty(user.getPhone())){
            return;
        }
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getPhone, user.getPhone());
        if(isExcludeSelf){
            lambdaQueryWrapper.ne(User::getId, user.getId());
        }
        int countPhone = this.count(lambdaQueryWrapper);
        if(countPhone > 0){
            throw new ServiceException(UserExceptionEnum.USER_PHONE_REPEAT);
        }
    }

    @Override
    public void checkEmailUnique(User user,boolean isExcludeSelf){
        if(StrUtil.isEmpty(user.getEmail())){
            return;
        }
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getEmail, user.getEmail());
        if(isExcludeSelf){
            lambdaQueryWrapper.ne(User::getId, user.getId());
        }
        int countPhone = this.count(lambdaQueryWrapper);
        if(countPhone > 0){
            throw new ServiceException(UserExceptionEnum.USER_EMAIL_REPEAT);
        }
    }

    @Override
    public void importUsers(List<UserImportParam> params) {
        if(CollUtil.isEmpty(params)){
            return;
        }

        //校验账号不能重复
        List<String> accountList = params.stream().map(UserImportParam::getAccount).collect(Collectors.toList());
        Map<String, Integer> accountCountMap = CollUtil.countMap(accountList);
        for(Map.Entry<String,Integer> entry : accountCountMap.entrySet()){
            if(entry.getValue() > 1){
                String errorMessage = String.format("登录账号重复：%s",entry.getKey());
                throw new ServiceException(errorMessage);
            }
        }

        //校验手机号码不能重复
        List<String> phoneList = params.stream().filter(item -> StrUtil.isNotEmpty(item.getPhone())).map(UserImportParam::getPhone).collect(Collectors.toList());
        Map<String, Integer> phoneCountMap = CollUtil.countMap(phoneList);
        for(Map.Entry<String,Integer> entry : phoneCountMap.entrySet()){
            if(entry.getValue() > 1){
                String errorMessage = String.format("手机号码重复：%s",entry.getKey());
                throw new ServiceException(errorMessage);
            }
        }

        List<User> users = CollUtil.newArrayList();
        for(int i = 0;i< params.size();i++){
            int rowNum = i + 1;
            UserImportParam param = params.get(i);
            String error = TlValidationUtils.validateFastFail(param);
            if(StrUtil.isNotEmpty(error)){
                String errorMessage = String.format("第%s行：%s",rowNum,error);
                throw new ServiceException(errorMessage);
            }
            //校验所属部门
            Dept dept = deptService.lambdaQuery().eq(Dept::getDeptName, param.getDeptName()).one();
            if(null == dept){
                String errorMessage = String.format("第%s行：%s",rowNum,"所属部门不存在");
                throw new ServiceException(errorMessage);
            }

            User user = new User();
            BeanUtil.copyProperties(param,user);
            //设置部门
            user.setDeptId(dept.getId());
            //设置显示排序
            if(null == user.getSort()){
                user.setSort(1);
            }
            //设置性别
            if(StrUtil.isNotEmpty(param.getSex())){
                Optional<UserSexEnum> sexOpt = Arrays.stream(UserSexEnum.values()).filter(value -> value.getText().equals(param.getSex())).findFirst();
                if(sexOpt.isPresent()){
                    user.setSex(sexOpt.get().getCode());
                }
            }

            // 校验参数
            try {
                checkParam(user,false);
            } catch (Exception e) {
                e.printStackTrace();
                if(e instanceof ServiceException){
                    ServiceException se = (ServiceException)e;
                    String errorMessage = String.format("第%s行：%s",rowNum,se.getErrorMessage());
                    throw  new ServiceException(errorMessage);
                }
                String errorMessage = String.format("第%s行：%s",rowNum,"校验参数发生异常");
                throw new ServiceException(errorMessage);
            }
            // 设置密码
            user.setPassword(SysConfigUtil.getProjectConfig().getDefaultPassword());
            // 加密密码、盐
            passwordHelper.encryptPassword(user);
            // 设置状态启用
            user.setStatus(CommonStatusEnum.ENABLE.getCode());

            users.add(user);
        }
        this.saveBatch(users);
    }

    /**
     * 刷新用户与角色缓存
     * @param roleId
     */
    private void refreshUserRoleCache(String roleId){
        CacheEvent cacheEvent = new CacheEvent();
        cacheEvent.setCacheName(CacheConstant.CACHE_NAME_USER_ROLE);
        cacheEvent.setKey(roleId);
        cacheEvent.setEventType(CacheEvent.EventType.REFRESH);
        SpringUtil.publishEvent(cacheEvent);
    }

}
