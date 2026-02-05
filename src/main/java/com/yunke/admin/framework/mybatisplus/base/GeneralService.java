package com.yunke.admin.framework.mybatisplus.base;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.github.yulichang.base.MPJBaseService;
import org.apache.poi.ss.formula.functions.T;

import java.util.Collection;

/**
 * @className GeneralService
 * @description: CURD Service基类
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
public interface GeneralService<T> extends MPJBaseService<T> {

    /**
     * @description: 根据ID更新所有数据
     * <p></p>
     * @param entity
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/14 15:29
     */
    boolean updateAllColumnById(T entity);

    /**
     * @description: 根据ID批量更新所有数据
     * <p></p>
     * @param entityList
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/14 15:29
     */
    default boolean updateAllColumnBatchById(Collection<T> entityList) {
        return updateAllColumnBatchById(entityList, 50);
    }

    /**
     * @description: 根据ID批量更新所有数据
     * <p></p>
     * @param entityList
     * @param batchSize
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/14 15:30
     */
    boolean updateAllColumnBatchById(Collection<T> entityList, int batchSize);

    /**
     * @description: 清空表
     * <p>执行TRUNCATE TABLE sql语句，此操作不可逆</p>
     * @return void
     * @auth: tianlei
     * @date: 2026/1/14 15:30
     */
    void truncateTable();

    /**
     * @description: 删除全部数据
     * <p></p>
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/14 15:32
     */
    boolean removeAll();

    /**
     * @description: 根据实体类属性删除
     * <p></p>
     * @param field
     * @param fieldValue
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/14 15:33
     */
    boolean removeByField(SFunction<T, ?> field, Object fieldValue);

    /**
     * @description: 根据id查询实体部分字段
     * <p></p>
     * @param id
     * @param fields
     * @return T
     * @auth: tianlei
     * @date: 2026/1/14 15:35
     */
    T selectFielsById(Object id,SFunction<T, ?>... fields);


}
