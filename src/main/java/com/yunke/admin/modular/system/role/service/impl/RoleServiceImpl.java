package com.yunke.admin.modular.system.role.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yunke.admin.common.constant.CommonConstant;
import com.yunke.admin.common.enums.CommonStatusEnum;
import com.yunke.admin.common.model.SingleDeleteParam;
import com.yunke.admin.common.util.BeanUtil;
import com.yunke.admin.common.util.SpringUtil;
import com.yunke.admin.framework.cache.base.CacheConstant;
import com.yunke.admin.framework.core.exception.ServiceException;
import com.yunke.admin.framework.core.util.ThreadUtil;
import com.yunke.admin.framework.eventbus.event.CacheEvent;
import com.yunke.admin.framework.mybatisplus.base.GeneralServiceImpl;
import com.yunke.admin.modular.system.dept.service.DeptService;
import com.yunke.admin.modular.system.role.enums.RoleExceptionEnum;
import com.yunke.admin.modular.system.role.mapper.RoleMapper;
import com.yunke.admin.modular.system.role.model.entity.Role;
import com.yunke.admin.modular.system.role.model.entity.RolePermission;
import com.yunke.admin.modular.system.role.model.param.*;
import com.yunke.admin.modular.system.role.model.vo.RoleGrantUserTreeVO;
import com.yunke.admin.modular.system.role.model.vo.UserGrantRoleListVO;
import com.yunke.admin.modular.system.role.service.RolePermissionService;
import com.yunke.admin.modular.system.role.service.RoleService;
import com.yunke.admin.modular.system.user.model.entity.User;
import com.yunke.admin.modular.system.user.model.entity.UserRole;
import com.yunke.admin.modular.system.user.service.UserRoleService;
import com.yunke.admin.modular.system.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @className RoleServiceImpl
 * @description: 系统角色表_服务实现类
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Service
public class RoleServiceImpl extends GeneralServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private DeptService deptService;
    @Autowired
    private UserService userService;

    @Transactional
    @Override
    public boolean add(RoleAddParam roleAddParam) {
        Role role = new Role();
        BeanUtil.copyProperties(roleAddParam, role);
        // 校验参数
        checkParam(role,false);

        // 设置状态启用
        role.setStatus(CommonStatusEnum.ENABLE.getCode());
        return this.save(role);
    }

    @Transactional
    @Override
    public boolean edit(RoleEditParam roleEditParam) {
        Role role = new Role();
        BeanUtil.copyProperties(roleEditParam, role);
        // 校验参数
        checkParam(role, true);

        if(role.getRoleCode().equals(CommonConstant.ADMIN_ROLE_CODE)){
            throw new ServiceException("禁止修改管理员角色");
        }

        // 不修改状态
        role.setStatus(null);
        return this.updateById(role);
    }

    @Transactional
    @Override
    public boolean delete(SingleDeleteParam singleDeleteParam) {
        Role role = this.getById(singleDeleteParam.getId());
        if(role != null && role.getRoleCode().equals(CommonConstant.ADMIN_ROLE_CODE)){
            throw new ServiceException("禁止删除管理员角色");
        }

        // 校验角色是否有被用户使用
        LambdaQueryWrapper<UserRole> userRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userRoleLambdaQueryWrapper.eq(UserRole::getRoleId, singleDeleteParam.getId());
        int count = userRoleService.count(userRoleLambdaQueryWrapper);
        if(count > 0){
            throw new ServiceException(RoleExceptionEnum.ROLE_HAS_USED);
        }
        // 删除角色与权限关联
        LambdaQueryWrapper<RolePermission> rolePermissionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        rolePermissionLambdaQueryWrapper.eq(RolePermission::getRoleId, singleDeleteParam.getId());
        rolePermissionService.remove(rolePermissionLambdaQueryWrapper);
        boolean ret = this.removeById(singleDeleteParam.getId());
        if(ret){
            refreshRolePermsCache(singleDeleteParam.getId());
        }
        return ret;
    }

    @Transactional
    @Override
    public boolean grantPermission(RoleGrantPermissionParam roleGrantPermissionParam) {
        String roleId = roleGrantPermissionParam.getRoleId();
        // 删除现有权限
        LambdaQueryWrapper<RolePermission> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RolePermission::getRoleId, roleId);
        rolePermissionService.remove(lambdaQueryWrapper);
        // 重新授权
        List<String> permissionIdList = roleGrantPermissionParam.getPermissionIdList();
        if(CollUtil.isNotEmpty(permissionIdList)){
            List<RolePermission> rolePermissionList = CollUtil.newArrayList();
            for(String permissinId : permissionIdList){
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRoleId(roleId);
                rolePermission.setPermissionId(permissinId);
                rolePermissionList.add(rolePermission);
            }
            rolePermissionService.saveBatch(rolePermissionList);
        }
        refreshRolePermsCache(roleId);
        return true;
    }

    @Transactional
    @Override
    public boolean updateRoleStatus(RoleUpdateStatusParam roleUpdateStatusParam) {
        Role updateRole = this.getById(roleUpdateStatusParam.getId());
        if(updateRole != null && updateRole.getRoleCode().equals(CommonConstant.ADMIN_ROLE_CODE)){
            throw new ServiceException("禁止修改管理员角色状态");
        }
        Role role = new Role();
        BeanUtil.copyProperties(roleUpdateStatusParam, role);
        refreshRolePermsCache(roleUpdateStatusParam.getId());
        ThreadUtil.execute(() ->{
            LambdaQueryWrapper<UserRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(UserRole::getRoleId, roleUpdateStatusParam.getId());
            List<UserRole> userRoles = userRoleService.list(lambdaQueryWrapper);
            if(CollUtil.isNotEmpty(userRoles)){
                userRoles.forEach(userRole -> {
                    refreshUserRoleCache(userRole.getUserId());
                });
            }
        });
        return this.updateById(role);
    }

    @Override
    public List<UserGrantRoleListVO> getUserGrantRoleList(QueryUserGrantRoleListParam queryUserGrantRoleListParam) {
        // 查询所有已启用的角色
        LambdaQueryWrapper<Role> roleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roleLambdaQueryWrapper.eq(Role::getStatus, CommonStatusEnum.ENABLE.getCode());
        roleLambdaQueryWrapper.orderByAsc(Role::getSort);
        List<Role> roles = this.list(roleLambdaQueryWrapper);
        List<UserGrantRoleListVO> userGrantRoleListVOList = BeanUtil.copyListProperties(roles, UserGrantRoleListVO::new);
        if(userGrantRoleListVOList != null && userGrantRoleListVOList.size() > 0){
            // 查询用户拥有的角色
            LambdaQueryWrapper<UserRole> userRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userRoleLambdaQueryWrapper.eq(UserRole::getUserId,queryUserGrantRoleListParam.getUserId());
            List<UserRole> userRoles = userRoleService.list(userRoleLambdaQueryWrapper);
            if(userRoles != null && userRoles.size() > 0){
                // 获取用户角色id集合
                Set<String> userRoleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toSet());
                // 设置是否选中
                userGrantRoleListVOList.stream().forEach(role -> {
                    if(userRoleIds.contains(role.getId())){
                        role.setChecked(true);
                    }
                });
            }
        }
        return userGrantRoleListVOList;
    }

    @Override
    public RoleGrantUserTreeVO getGrantUserTreeDataByRoleId(String roleId) {
        RoleGrantUserTreeVO roleGrantUserTreeVO = new RoleGrantUserTreeVO();
        // 默认展开节点
        Set<String> defaultExpandedKeys = CollUtil.newHashSet();
        roleGrantUserTreeVO.setDefaultExpandedKeys(defaultExpandedKeys);
        List<Tree<String>> deptTree = deptService.tree();
        deptTree.stream().forEach(treeNode -> {
            this.fillDeptTreeUser(treeNode);
            defaultExpandedKeys.add(treeNode.getId());
        });
        roleGrantUserTreeVO.setTreeData(deptTree);
        // 设置默认展开节点
        roleGrantUserTreeVO.setDefaultExpandedKeys(defaultExpandedKeys);
        // 设置默认选中节点
        Set<String> defaultCheckedKeys = CollUtil.newHashSet();
        List<UserRole> userRoles = userRoleService.list(new LambdaQueryWrapper<UserRole>().eq(UserRole::getRoleId, roleId));
        if(userRoles != null && userRoles.size() > 0){
            Set<String> userIds = userRoles.stream().map(userRole -> {
                return userRole.getUserId();
            }).collect(Collectors.toSet());
            List<User> users = userService.listByIds(userIds);
            users.stream().forEach(user -> {
                defaultCheckedKeys.add(user.getDeptId() + "_" + user.getId());
            });
        }
        roleGrantUserTreeVO.setDefaultCheckedKeys(defaultCheckedKeys);
        return roleGrantUserTreeVO;
    }

    @Transactional
    @Override
    public boolean grantUser(RoleGrantUserParam roleGrantUserParam) {
        // 删除角色与用户关联
        LambdaQueryWrapper<UserRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserRole::getRoleId, roleGrantUserParam.getRoleId());
        List<UserRole> userRoles = userRoleService.list(lambdaQueryWrapper);
        if(CollUtil.isNotEmpty(userRoles)){
            userRoles.forEach(userRole -> {
                userRoleService.removeById(userRole.getId());
                refreshUserRoleCache(userRole.getUserId());
            });
        }
        // 新增用户与角色关联
        List<String> userIdList = roleGrantUserParam.getUserIdList();
        if(CollUtil.isNotEmpty(userIdList)){
            List<UserRole> userRoleList = CollUtil.newArrayList();
            for(String userId : userIdList){
                UserRole userRole = new UserRole();
                userRole.setRoleId(roleGrantUserParam.getRoleId());
                userRole.setUserId(userId);
                userRoleList.add(userRole);
            }
            return userRoleService.saveBatch(userRoleList);
        }
        refreshUserRoleCache(roleGrantUserParam.getRoleId());
        return true;
    }

    public void fillDeptTreeUser(Tree<String> treeNode){
        if(treeNode != null){
            List<Tree<String>> children = treeNode.getChildren();
            if(children == null){
                children = new ArrayList<>();
            }
            // 添加本级部门用户节点
            String deptId = treeNode.getId();
            LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>();
            lambdaQueryWrapper.eq(User::getDeptId,deptId);
            lambdaQueryWrapper.eq(User::getStatus, CommonStatusEnum.ENABLE.getCode());
            lambdaQueryWrapper.orderByAsc(User::getSort);
            List<User> userList = userService.list(lambdaQueryWrapper);
            if(CollUtil.isNotEmpty(userList)){
                List<Tree<String>> nodes = userList.stream().map(user -> {
                    Tree<String> node = new Tree<>();
                    node.setId(user.getDeptId() + "_" + user.getId());
                    node.setParentId(user.getDeptId());
                    node.setName(user.getUserName());
                    node.setWeight(user.getSort());
                    //标记节点类型为user
                    node.putExtra("type","user");
                    return node;
                }).collect(Collectors.toList());
                children.addAll(0, nodes);
            }
            treeNode.setChildren(children);
            // 获取子部门,递归遍历子部门
            treeNode.getChildren().stream().filter(node -> {
                return !node.getId().startsWith(treeNode.getId() + "_");
            }).forEach(node -> {
                this.fillDeptTreeUser(node);
            });
        }
    }

    public void checkParam(Role role,boolean isExcludeSelf) {
        LambdaQueryWrapper<Role> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        // 检验角色名称是否重复
        lambdaQueryWrapper.eq(Role::getRoleName, role.getRoleName());
        if(isExcludeSelf){
            lambdaQueryWrapper.ne(Role::getId, role.getId());
        }
        int countName = this.count(lambdaQueryWrapper);
        if(countName > 0){
            throw new ServiceException(RoleExceptionEnum.ROLE_NAME_REPEAT);
        }

        // 校验角色编码是否重复
        lambdaQueryWrapper.clear();
        lambdaQueryWrapper.eq(Role::getRoleCode,role.getRoleCode());
        if(isExcludeSelf){
            lambdaQueryWrapper.ne(Role::getId, role.getId());
        }
        int countCode = this.count(lambdaQueryWrapper);
        if(countCode > 0){
            throw new ServiceException(RoleExceptionEnum.ROLE_CODE_REPEAT);
        }
    }

    /**
     * @description: 刷新角色与权限缓存
     * <p></p>
     * @param roleId
     * @return void
     * @auth: tianlei
     * @date: 2026/1/15 21:52
     */
    private void refreshRolePermsCache(String roleId){
        CacheEvent cacheEvent = new CacheEvent();
        cacheEvent.setCacheName(CacheConstant.CACHE_NAME_ROLE_PERMISSION);
        cacheEvent.setKey(roleId);
        cacheEvent.setEventType(CacheEvent.EventType.REFRESH);
        SpringUtil.publishEvent(cacheEvent);
    }

    /**
     * @description: 刷新用户与角色缓存
     * <p></p>
     * @param roleId
     * @return void
     * @auth: tianlei
     * @date: 2026/1/15 21:52
     */
    private void refreshUserRoleCache(String roleId){
        CacheEvent cacheEvent = new CacheEvent();
        cacheEvent.setCacheName(CacheConstant.CACHE_NAME_USER_ROLE);
        cacheEvent.setKey(roleId);
        cacheEvent.setEventType(CacheEvent.EventType.REFRESH);
        SpringUtil.publishEvent(cacheEvent);
    }
}
