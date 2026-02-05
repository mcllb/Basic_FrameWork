package com.yunke.admin.framework.mybatisplus.base;

import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.github.yulichang.base.MPJBaseMapper;
import com.yunke.admin.framework.mybatisplus.provider.ProviderMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @className GeneralMapper
 * @description: MP 基础Mapper
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
public interface GeneralMapper<T> extends MPJBaseMapper<T>, ProviderMapper<T> {

    /**
     * @description: 根据id更新所有数据
     * <p></p>
     * @param entity
     * @return int
     * @auth: tianlei
     * @date: 2026/1/14 15:28
     */
    int updateAllColumnById(@Param(Constants.ENTITY) T entity);

    void truncateTable();

    int deleteAll();



}
