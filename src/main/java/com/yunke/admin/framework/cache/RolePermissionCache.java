package com.yunke.admin.framework.cache;

import cn.hutool.core.collection.CollUtil;
import com.alicp.jetcache.anno.*;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.yunke.admin.common.enums.CommonStatusEnum;
import com.yunke.admin.framework.cache.base.AbstractBaseJetcache;
import com.yunke.admin.modular.system.permission.model.entity.Permission;
import com.yunke.admin.modular.system.role.model.entity.Role;
import com.yunke.admin.modular.system.role.model.entity.RolePermission;
import com.yunke.admin.modular.system.role.service.RolePermissionService;
import com.yunke.admin.modular.system.role.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class RolePermissionCache extends AbstractBaseJetcache<List<Permission>> {

    public static final String CACHE_NAME = CACHE_NAME_ROLE_PERMISSION;

    private static final String CACHE_TITLE = CACHE_TITLE_ROLE_PERMISSION;

    @Autowired
    private RoleService roleService;
    @Autowired
    private RolePermissionService rolePermissionService;

    private RolePermissionCache self;

    @Autowired
    public void setCache(RolePermissionCache cache) {
        this.self = cache;
    }

    public RolePermissionCache() {
        super(CACHE_NAME,CACHE_TITLE);
    }

    @CachePenetrationProtect
    @Cached(name=CACHE_NAME, key="#key", expire = 3600)
    @Override
    public List<Permission> get(String key) {
        return load(key);
    }

    @CacheUpdate(name=CACHE_NAME, key="#key", value="#value")
    @Override
    public void put(String key, List<Permission> value) {

    }

    @CacheInvalidate(name=CACHE_NAME, key="#key")
    @Override
    public void remove(String key) {

    }

    @Override
    public void init() {
        log.debug("RolePermissionCache init ====>");
        self.get("init");
    }

    @Override
    public List<Permission> load(String key) {
        MPJLambdaWrapper<RolePermission> mpjLambdaWrapper = new MPJLambdaWrapper<>();
        mpjLambdaWrapper.selectAll(Permission.class);
        mpjLambdaWrapper.leftJoin(Permission.class, Permission::getId, RolePermission::getPermissionId);
        mpjLambdaWrapper.eq(RolePermission::getRoleId,key);
        mpjLambdaWrapper.eq(Permission::getStatus, CommonStatusEnum.ENABLE.getCode());
        mpjLambdaWrapper.isNotNull(Permission::getPermissionCode);
        mpjLambdaWrapper.orderByAsc(Permission::getPermissionCode);
        List<Permission> permissions = rolePermissionService.selectJoinList(Permission.class, mpjLambdaWrapper);
        if(CollUtil.isNotEmpty(permissions)){
            return permissions;
        }
        return null;
    }

    @Override
    public void loadCache() {
        //查询所有已启用的角色
        List<Role> list = roleService.lambdaQuery().eq(Role::getStatus, CommonStatusEnum.ENABLE.getCode())
                .orderByAsc(Role::getCreateTime).list();
        if(CollUtil.isNotEmpty(list)){
            list.forEach(role -> {
                MPJLambdaWrapper<RolePermission> mpjLambdaWrapper = new MPJLambdaWrapper<>();
                mpjLambdaWrapper.selectAll(Permission.class);
                mpjLambdaWrapper.leftJoin(Permission.class, Permission::getId, RolePermission::getPermissionId);
                mpjLambdaWrapper.eq(RolePermission::getRoleId,role.getId());
                mpjLambdaWrapper.eq(Permission::getStatus, CommonStatusEnum.ENABLE.getCode());
                mpjLambdaWrapper.isNotNull(Permission::getPermissionCode);
                mpjLambdaWrapper.orderByAsc(Permission::getPermissionCode);
                List<Permission> permissions = rolePermissionService.selectJoinList(Permission.class, mpjLambdaWrapper);
                this.self.put(role.getId(),permissions);
            });
        }
    }
}
