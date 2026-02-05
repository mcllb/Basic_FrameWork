package com.yunke.admin.framework.cache;

import cn.hutool.core.collection.CollUtil;
import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CachePenetrationProtect;
import com.alicp.jetcache.anno.CacheUpdate;
import com.alicp.jetcache.anno.Cached;
import com.yunke.admin.framework.cache.base.AbstractBaseJetcache;
import com.yunke.admin.modular.system.dept.model.entity.Dept;
import com.yunke.admin.modular.system.dept.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class DeptCache extends AbstractBaseJetcache<Dept> {

    public static final String CACHE_NAME = CACHE_NAME_DEPT;

    private static final String CACHE_TITLE = CACHE_TITLE_DEPT;

    @Autowired
    private DeptService deptService;

    @Autowired
    public void setSelf(DeptCache self) {
        this.self = self;
    }

    private DeptCache self;

    public DeptCache() {
        super(CACHE_NAME,CACHE_TITLE);
    }

    @Override
    public Dept load(String key) {
        Dept dept = deptService.getById(key);
        return dept;
    }

    @CachePenetrationProtect
    @Cached(name=CACHE_NAME, key="#key", expire = 3600)
    @Override
    public Dept get(String key) {
        Dept dept = this.load(key);
        return dept;
    }

    @CacheUpdate(name=CACHE_NAME, key="#key", value="#value")
    @Override
    public void put(String key, Dept value) {

    }

    @CacheInvalidate(name=CACHE_NAME, key="#key")
    @Override
    public void remove(String key) {

    }

    @Override
    public void loadCache() {
        List<Dept> list = deptService.list();
        if (CollUtil.isNotEmpty(list)) {
            list.stream().forEach(dept -> {
                this.self.put(dept.getId(), dept);
            });
        }
    }

    @Override
    public void init() {
        log.debug("DeptCache init ====>");
        self.get("init");
    }

}
