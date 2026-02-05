package com.yunke.admin.framework.mybatisplus.base;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.yunke.admin.framework.mybatisplus.method.GeneralMybatisPlusSqlMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * @className GeneralServiceImpl
 * @description: CURD Service实现类基类
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Slf4j
public class GeneralServiceImpl<M extends GeneralMapper<T>, T> extends MPJBaseServiceImpl<M, T> implements GeneralService<T> {
    /**
     * 获取SqlStatement
     *
     * @param sqlMethod
     * @return
     */
    protected String sqlStatement(GeneralMybatisPlusSqlMethod sqlMethod) {
        return SqlHelper.table(currentModelClass()).getSqlStatement(sqlMethod.getMethod());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateAllColumnById(T entity) {
        return SqlHelper.retBool(baseMapper.updateAllColumnById(entity));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateAllColumnBatchById(Collection<T> entityList, int batchSize) {
        if (CollUtil.isEmpty(entityList)) {
            throw new IllegalArgumentException("Error: entityList must not be empty");
        }
        //int i = 0;
        //String sqlStatement = sqlStatement(GeneralMybatisPlusSqlMethod.UPDATE_ALL_COLUMN_BY_ID);
        //try (SqlSession batchSqlSession = sqlSessionBatch()) {
        //    for (T anEntityList : entityList) {
        //        MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
        //        param.put(Constants.ENTITY, anEntityList);
        //        batchSqlSession.update(sqlStatement, param);
        //        if (i >= 1 && i % batchSize == 0) {
        //            batchSqlSession.flushStatements();
        //        }
        //        i++;
        //    }
        //    batchSqlSession.flushStatements();
        //}

        String sqlStatement = sqlStatement(GeneralMybatisPlusSqlMethod.UPDATE_ALL_COLUMN_BY_ID);
        return this.executeBatch(entityList, batchSize, (sqlSession, entity) -> {
            MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap();
            param.put(Constants.ENTITY, entity);
            sqlSession.update(sqlStatement, param);
        });
    }

    @Override
    public void truncateTable() {
        baseMapper.truncateTable();
    }


    @Transactional(rollbackFor = {Exception.class})
    @Override
    public boolean removeAll() {
        return SqlHelper.retBool(baseMapper.deleteAll());
    }


    @Transactional(rollbackFor = {Exception.class})
    @Override
    public boolean removeByField(SFunction<T, ?> field, Object fieldValue) {
        return SqlHelper.retBool(baseMapper.deleteByField(field, fieldValue));
    }

    @Override
    public T selectFielsById(Object id, SFunction<T, ?>... fields) {
        return this.baseMapper.selectFielsById(id,fields);
    }


}
