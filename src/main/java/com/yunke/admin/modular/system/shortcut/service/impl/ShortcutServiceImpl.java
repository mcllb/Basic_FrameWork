package com.yunke.admin.modular.system.shortcut.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.yunke.admin.common.constant.SymbolConstant;
import com.yunke.admin.common.enums.CommonStatusEnum;
import com.yunke.admin.common.util.BeanUtil;
import com.yunke.admin.common.util.SaUtil;
import com.yunke.admin.framework.core.exception.ServiceException;
import com.yunke.admin.framework.mybatisplus.base.GeneralServiceImpl;
import com.yunke.admin.modular.system.permission.enums.PermissionOpenTypeEnum;
import com.yunke.admin.modular.system.permission.enums.PermissionTypeEnum;
import com.yunke.admin.modular.system.permission.model.entity.Permission;
import com.yunke.admin.modular.system.permission.service.PermissionService;
import com.yunke.admin.modular.system.role.model.entity.Role;
import com.yunke.admin.modular.system.role.model.entity.RolePermission;
import com.yunke.admin.modular.system.role.service.RolePermissionService;
import com.yunke.admin.modular.system.shortcut.enums.ShortcutTypeEnum;
import com.yunke.admin.modular.system.shortcut.mapper.ShortcutMapper;
import com.yunke.admin.modular.system.shortcut.model.entity.Shortcut;
import com.yunke.admin.modular.system.shortcut.model.param.*;
import com.yunke.admin.modular.system.shortcut.model.vo.ShortcutVO;
import com.yunke.admin.modular.system.shortcut.service.ShortcutService;
import com.yunke.admin.modular.system.user.model.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @className ShortcutServiceImpl
 * @description: 快捷方式_Service实现类
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Service
public class ShortcutServiceImpl extends GeneralServiceImpl<ShortcutMapper, Shortcut> implements ShortcutService {
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RolePermissionService rolePermissionService;

    @Override
    public List<ShortcutVO> list(ShortcutQueryParam queryParam) {
        MPJLambdaWrapper<Shortcut> mpjLambdaWrapper = new MPJLambdaWrapper<>();
        mpjLambdaWrapper.selectAll(Shortcut.class);
        mpjLambdaWrapper.select(Permission::getPermissionName);
        mpjLambdaWrapper.selectAs(Permission::getIcon, ShortcutVO::getPermissionIcon);
        mpjLambdaWrapper.selectAs(Permission::getPath, ShortcutVO::getPermissionPath);
        mpjLambdaWrapper.leftJoin(Permission.class, Permission::getId, Shortcut::getPermissionId);
        //查询用户自己创建的
        mpjLambdaWrapper.eq(Shortcut::getCreateBy, SaUtil.getUserId());
        // 查询条件：名称
        if(StrUtil.isNotEmpty(queryParam.getName())){
            mpjLambdaWrapper.and(wrapper -> wrapper.like(Shortcut::getName,queryParam.getName())
            .or().like(Permission::getPermissionName,queryParam.getName()));
        }
        
        // 查询条件：xxx
        
        // 排序
        mpjLambdaWrapper.orderByAsc(Shortcut::getSort);

        List<ShortcutVO> list = this.selectJoinList(ShortcutVO.class, mpjLambdaWrapper);
        BeanUtil.fillListDataDictField(list);
        BeanUtil.fillListEnumDictField(list);
        // 设置菜单路径
        if(CollUtil.isNotEmpty(list)){
            list.parallelStream().forEach(item -> {
                fillPermissionPath(item);
            });
        }
        return list;
    }

    private void fillPermissionPath(ShortcutVO shortcutVO){
        if(shortcutVO != null && StrUtil.isNotEmpty(shortcutVO.getPermissionId())){
            Permission permission = permissionService.getById(shortcutVO.getPermissionId());
            if(permission != null){
                if(PermissionOpenTypeEnum.TATGET.getCode().equals(permission.getOpenType())){
                    return;
                }
                String permissionPath = "";
                String parentIds = permission.getParentIds();
                String[] split = parentIds.split(SymbolConstant.HYPHEN);
                for(String id : split){
                    if("root".equals(id)){
                        permissionPath += "/";
                    }else{
                        Permission parent = permissionService.getById(id);
                        if(parent != null){
                            permissionPath += parent.getPath() + "/";
                        }
                    }
                }
                permissionPath += permission.getPath();
                shortcutVO.setPermissionPath(permissionPath);
            }
        }
    }
    
    @Override
    public ShortcutVO getVO(String id){
        ShortcutVO shortcutVO = new ShortcutVO();
        Shortcut shortcut = this.getById(id);
        BeanUtil.copyProperties(shortcut,shortcutVO);
        return shortcutVO;
    }

    @Transactional
    @Override
    public boolean add(ShortcutAddParam addParam) {
        Shortcut shortcut = new Shortcut();
        BeanUtil.copyProperties(addParam,shortcut);
        shortcut.setEnable(CommonStatusEnum.ENABLE.getCode());
        checkParam(shortcut,false);
        return this.save(shortcut);
    }

    @Transactional
    @Override
    public boolean edit(ShortcutEditParam editParam) {
        Shortcut shortcut = new Shortcut();
        BeanUtil.copyProperties(editParam,shortcut);
        checkParam(shortcut,true);
        return this.updateById(shortcut);
    }

    @Transactional
    @Override
    public boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    public List<Permission> getUserPermissions(boolean excludeShoutCutRef) {
        List<Permission> permissionList = CollUtil.newArrayList();

        //查询用户授权的菜单
        MPJLambdaWrapper<RolePermission> mpjLambdaWrapper = new MPJLambdaWrapper<>();
        mpjLambdaWrapper.selectAll(RolePermission.class);
        mpjLambdaWrapper.leftJoin(UserRole.class,UserRole::getRoleId, RolePermission::getRoleId);
        mpjLambdaWrapper.leftJoin(Role.class, on -> on.eq(Role::getId,UserRole::getRoleId).eq(Role::getStatus, CommonStatusEnum.ENABLE.getCode()));
        mpjLambdaWrapper.leftJoin(Permission.class, Permission::getId, RolePermission::getPermissionId);
        mpjLambdaWrapper.eq(UserRole::getUserId, SaUtil.getUserId());
        mpjLambdaWrapper.eq(Permission::getPermissionType, PermissionTypeEnum.MENU.getCode());
        List<RolePermission> rolePermissions = rolePermissionService.selectJoinList(RolePermission.class, mpjLambdaWrapper);
        if(CollUtil.isNotEmpty(rolePermissions)){
            List<String> permsIds = rolePermissions.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
            if(excludeShoutCutRef){
                //排除已关联的菜单
                List<Shortcut> shortcutList = this.lambdaQuery().eq(Shortcut::getCreateBy, SaUtil.getUserId()).list();
                if(CollUtil.isNotEmpty(shortcutList)){
                    List<String> shortcutPermissionIds = shortcutList.stream().map(Shortcut::getPermissionId).collect(Collectors.toList());
                    permsIds = permsIds.stream().filter(id -> !shortcutPermissionIds.contains(id)).collect(Collectors.toList());
                }
            }
            if(CollUtil.isNotEmpty(permsIds)){
                LambdaQueryWrapper<Permission> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.in(Permission::getId, permsIds);
                lambdaQueryWrapper.eq(Permission::getPermissionType, PermissionTypeEnum.MENU.getCode());
                lambdaQueryWrapper.eq(Permission::getStatus, CommonStatusEnum.ENABLE.getCode());
                lambdaQueryWrapper.orderByAsc(Permission::getSort);
                permissionList = permissionService.list(lambdaQueryWrapper);
            }
        }
        return permissionList;
    }

    @Override
    public boolean updateStatus(ShortcutUpdateStatusParam updateStatusParam) {
        Shortcut shortcut = new Shortcut();
        BeanUtil.copyProperties(updateStatusParam, shortcut);
        return this.updateById(shortcut);
    }

    @Override
    public boolean updateSort(ShortcutUpdateSortParam updateSortParam) {
        Shortcut shortcut = new Shortcut();
        BeanUtil.copyProperties(updateSortParam, shortcut);
        return this.updateById(shortcut);
    }

    private void checkParam(Shortcut entity,boolean excludeSelf){
        LambdaQueryWrapper<Shortcut> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Shortcut::getCreateBy, SaUtil.getUserId());
        //校验名称不能重复
        if(StrUtil.isNotEmpty(entity.getName())){
            lambdaQueryWrapper.eq(Shortcut::getName, entity.getName());
            if(excludeSelf){
                lambdaQueryWrapper.ne(Shortcut::getId, entity.getId());
            }
            int countName = this.count(lambdaQueryWrapper);
            if(countName > 0){
                throw new ServiceException("名称重复了");
            }
        }

        lambdaQueryWrapper.clear();
        if(ShortcutTypeEnum.SYSTEM.getCode().equals(entity.getType())){
            if(StrUtil.isEmpty(entity.getPermissionId())){
                throw new ServiceException("系统菜单id不能为空，请检查参数permissionId");
            }
        }else if(ShortcutTypeEnum.CUSTOM.getCode().equals(entity.getType())){
            if(StrUtil.isEmpty(entity.getPath())){
                throw new ServiceException("路径不能为空，请检查参数path");
            }
            if(StrUtil.isEmpty(entity.getIcon())){
                throw new ServiceException("图标不能为空，请检查参数icon");
            }
        }



    }
}
