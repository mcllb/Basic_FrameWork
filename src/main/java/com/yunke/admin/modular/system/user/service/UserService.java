package com.yunke.admin.modular.system.user.service;


import cn.hutool.core.lang.tree.Tree;
import com.yunke.admin.common.model.MenuTreeNode;
import com.yunke.admin.common.model.SingleDeleteParam;
import com.yunke.admin.framework.mybatisplus.base.GeneralService;
import com.yunke.admin.modular.system.auth.model.vo.LoginUserVO;
import com.yunke.admin.modular.system.user.model.entity.User;
import com.yunke.admin.modular.system.user.model.param.*;
import com.yunke.admin.modular.system.user.model.vo.UserInfoVO;
import com.yunke.admin.modular.system.user.model.vo.UserPageVO;

import java.util.List;
import java.util.Set;

/**
 * @className UserService
 * @description: 系统用户表service接口
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
public interface UserService extends GeneralService<User> {

    /**
     * @description: 通过登陆账号获取用户
     * <p></p>
     * @param account 登陆账号
     * @return com.yunke.admin.modular.system.user.model.entity.User
     * @auth: tianlei
     * @date: 2026/1/15 22:02
     */
    User getUserByAccount(String account);

    /**
     * @description: 通过登陆账号获取用户角色集合
     * <p></p>
     * @param account 登陆账号
     * @return java.util.Set<java.lang.String>
     * @auth: tianlei
     * @date: 2026/1/15 22:03
     */
    Set<String> getUserRolesSetByAccount(String account);

    /**
     * @description: 通过登陆账号获取用户权限集合
     * <p></p>
     * @param account 登陆账号
     * @return java.util.Set<java.lang.String>
     * @auth: tianlei
     * @date: 2026/1/15 22:03
     */
    Set<String> getUserPermsSetByAccount(String account);

    /**
     * @description: 通过登陆账号获取用户角色集合
     * <p></p>
     * @param loginUser 登陆用户
     * @return java.util.Set<java.lang.String>
     * @auth: tianlei
     * @date: 2026/1/15 22:03
     */
    Set<String> getLoginUserRoleCodesSet(LoginUserVO loginUser);

    /**
     * @description: 通过登陆账号获取用户权限集合
     * <p></p>
     * @param loginUser
     * @return java.util.Set<java.lang.String>
     * @auth: tianlei
     * @date: 2026/1/15 22:03
     */
    Set<String> getLoginUserPermissonCodesSet(LoginUserVO loginUser);

    /**
     * @description: 获取当前登陆用户拥有的菜单
     * <p></p>
     * @param loginUser
     * @return java.util.List<com.yunke.admin.common.model.MenuTreeNode>
     * @auth: tianlei
     * @date: 2026/1/15 22:04
     */
    List<MenuTreeNode> getLoginUserMenuTree(LoginUserVO loginUser);

    boolean add(UserAddParam userAddParam);

    boolean edit(UserEditParam userEditParam);

    boolean delete(SingleDeleteParam singleDeleteParam);

    boolean grantRole(UserGrantRoleParam userGrantRoleParam);

    boolean updateUserStatus(UserUpdateStatusParam userUpdateStatusParam);

    boolean updateInfo(UserUpdateInfoParam userUpdateInfoParam);

    boolean updatePassword(UserUpdatePasswordParam userUpdatePasswordParam);

    boolean resetPassword(UserResetPasswordParam userResetPasswordParam);

    /**
     * @description: 重置所有用户密码
     * <p></p>
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/15 22:04
     */
    boolean resetPasswordAll();

    UserInfoVO getUserInfoVOById(String id);

    void fillDeptName(UserInfoVO userInfoVO);

    void fillDeptName(UserPageVO userPageVO);

    void fillDeptName(List<UserPageVO> userPageVOList);

    /**
     * @description: 获取用户明文密码
     * <p></p>
     * @param id
     * @return java.lang.String
     * @auth: tianlei
     * @date: 2026/1/15 22:04
     */
    String getPassword(String id);

    /**
     * @description: 获取当前用户信息
     * <p></p>
     * @return com.yunke.admin.modular.system.user.model.vo.UserInfoVO
     * @auth: tianlei
     * @date: 2026/1/15 22:04
     */
    UserInfoVO  getCurrentUserInfo();

    /**
     * @description: 获取组织机构tree
     * <p></p>
     * @return cn.hutool.core.lang.tree.Tree<java.lang.String>
     * @auth: tianlei
     * @date: 2026/1/15 22:05
     */
    Tree<String> getOrgTree();

    /**
     * @description: 校验手机号是否唯一
     * <p></p>
     * @param user
     * @param isExcludeSelf
     * @return void
     * @auth: tianlei
     * @date: 2026/1/15 22:05
     */
    void checkPhoneUnique(User user,boolean isExcludeSelf);

    /**
     * @description: //TODO
     * <p></p>
     * @param user
     * @param isExcludeSelf
     * @return void
     * @auth: tianlei
     * @date: 2026/1/15 22:05
     */
    void checkEmailUnique(User user,boolean isExcludeSelf);

    /**
     * 用户导入
     * @param params
     */
    /**
     * @description: 用户导入
     * <p></p>
     * @param params
     * @return void
     * @auth: tianlei
     * @date: 2026/1/15 22:05
     */
    void importUsers(List<UserImportParam> params);

}
