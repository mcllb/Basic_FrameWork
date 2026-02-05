package com.yunke.admin.framework.cache;

import cn.hutool.core.collection.CollUtil;
import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CachePenetrationProtect;
import com.alicp.jetcache.anno.CacheUpdate;
import com.alicp.jetcache.anno.Cached;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.yunke.admin.common.enums.CommonStatusEnum;
import com.yunke.admin.framework.cache.base.AbstractBaseJetcache;
import com.yunke.admin.modular.system.role.model.entity.Role;
import com.yunke.admin.modular.system.user.model.entity.User;
import com.yunke.admin.modular.system.user.model.entity.UserRole;
import com.yunke.admin.modular.system.user.service.UserRoleService;
import com.yunke.admin.modular.system.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class UserRoleCache extends AbstractBaseJetcache<List<Role>> {

    public static final String CACHE_NAME = CACHE_NAME_USER_ROLE;

    private static final String CACHE_TITLE = CACHE_TITLE_USER_ROLE;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;

    private UserRoleCache self;

    @Autowired
    public void setCache(UserRoleCache cache) {
        this.self = cache;
    }

    public UserRoleCache() {
        super(CACHE_NAME,CACHE_TITLE);
    }

    @CachePenetrationProtect
    @Cached(name=CACHE_NAME, key="#key", expire = 3600)
    @Override
    public List<Role> get(String key) {
        return load(key);
    }

    @CacheUpdate(name=CACHE_NAME, key="#key", value="#value")
    @Override
    public void put(String key, List<Role> value) {

    }

    @CacheInvalidate(name=CACHE_NAME, key="#key")
    @Override
    public void remove(String key) {

    }


    @Override
    public void init() {
        log.debug("UserRoleCache init ====>");
        self.get("init");
    }

    @Override
    public List<Role> load(String key) {
        MPJLambdaWrapper<UserRole> mpjLambdaWrapper = new MPJLambdaWrapper<>();
        mpjLambdaWrapper.selectAll(Role.class);
        mpjLambdaWrapper.leftJoin(Role.class, Role::getId, UserRole::getRoleId);
        mpjLambdaWrapper.eq(UserRole::getUserId,key);
        mpjLambdaWrapper.eq(Role::getStatus, CommonStatusEnum.ENABLE.getCode());
        mpjLambdaWrapper.isNotNull(Role::getRoleCode);
        mpjLambdaWrapper.orderByAsc(Role::getRoleCode);
        List<Role> roles = userRoleService.selectJoinList(Role.class, mpjLambdaWrapper);
        if(CollUtil.isNotEmpty(roles)){
            return roles;
        }
        return null;
    }


    public void loadCache() {
        //查询所有已启用的用户
        List<User> list = userService.lambdaQuery().eq(User::getStatus, CommonStatusEnum.ENABLE.getCode())
                .orderByAsc(User::getCreateTime).list();
        if(CollUtil.isNotEmpty(list)){
            list.forEach(user -> {
                MPJLambdaWrapper<UserRole> mpjLambdaWrapper = new MPJLambdaWrapper<>();
                mpjLambdaWrapper.selectAll(Role.class);
                mpjLambdaWrapper.leftJoin(Role.class, Role::getId, UserRole::getRoleId);
                mpjLambdaWrapper.eq(UserRole::getUserId,user.getId());
                mpjLambdaWrapper.eq(Role::getStatus, CommonStatusEnum.ENABLE.getCode());
                mpjLambdaWrapper.isNotNull(Role::getRoleCode);
                mpjLambdaWrapper.orderByAsc(Role::getRoleCode);
                List<Role> roles = userRoleService.selectJoinList(Role.class, mpjLambdaWrapper);
                this.self.put(user.getId(),roles);
            });
        }
    }
}
