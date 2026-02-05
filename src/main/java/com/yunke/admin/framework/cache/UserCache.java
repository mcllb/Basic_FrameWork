package com.yunke.admin.framework.cache;

import cn.hutool.core.collection.CollUtil;
import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CachePenetrationProtect;
import com.alicp.jetcache.anno.CacheUpdate;
import com.alicp.jetcache.anno.Cached;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.yunke.admin.common.util.BeanUtil;
import com.yunke.admin.framework.cache.base.AbstractBaseJetcache;
import com.yunke.admin.framework.cache.vo.UserCacheVO;
import com.yunke.admin.modular.system.dept.model.entity.Dept;
import com.yunke.admin.modular.system.user.model.entity.User;
import com.yunke.admin.modular.system.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class UserCache extends AbstractBaseJetcache<UserCacheVO> {

    public static final String CACHE_NAME = CACHE_NAME_USER;

    private static final String CACHE_TITLE = CACHE_TITLE_USER;

    @Autowired
    private UserService userService;

    private UserCache self;

    @Autowired
    public void setCache(UserCache cache) {
        this.self = cache;
    }

    public UserCache() {
        super(CACHE_NAME,CACHE_TITLE);
    }

    @Override
    public UserCacheVO load(String key) {
        MPJLambdaWrapper<User> mpjLambdaWrapper = new MPJLambdaWrapper<>();
        mpjLambdaWrapper.selectAll(User.class);
        mpjLambdaWrapper.select(Dept::getDeptName);
        mpjLambdaWrapper.leftJoin(Dept.class, Dept::getId, User::getDeptId);
        mpjLambdaWrapper.eq(User::getId, key);
        UserCacheVO user = userService.selectJoinOne(UserCacheVO.class,mpjLambdaWrapper);
        if(user != null){
            BeanUtil.fill(user);
        }
        return user;
    }

    @CachePenetrationProtect
    @Cached(name=CACHE_NAME, key="#key", expire = 3600)
    @Override
    public UserCacheVO get(String key) {
        UserCacheVO user = this.load(key);
        return user;
    }

    @CacheUpdate(name=CACHE_NAME, key="#key", value="#value")
    @Override
    public void put(String key, UserCacheVO value) {

    }

    @CacheInvalidate(name=CACHE_NAME, key="#key")
    @Override
    public void remove(String key) {

    }

    @Override
    public void loadCache() {
        MPJLambdaWrapper<User> mpjLambdaWrapper = new MPJLambdaWrapper<>();
        mpjLambdaWrapper.selectAll(User.class);
        mpjLambdaWrapper.select(Dept::getDeptName);
        mpjLambdaWrapper.leftJoin(Dept.class, Dept::getId, User::getDeptId);
        List<UserCacheVO> list = userService.selectJoinList(UserCacheVO.class, mpjLambdaWrapper);
        if(CollUtil.isNotEmpty(list)){
            list.stream().forEach(user -> {
                BeanUtil.fill(user);
                this.self.put(user.getId(), user);
            });

        }
    }


    @Override
    public void init() {
        log.debug("UserCache init ====>");
        self.get("init");
    }

}
