package com.yunke.admin.framework.mybatisplus.method;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 清空表
 */
/**
 * @className DeleteAll
 * @description: //TODO
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
public class DeleteAll extends GeneralAbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        GeneralMybatisPlusSqlMethod sqlMethod = GeneralMybatisPlusSqlMethod.DELETE_ALL;
        String sql = String.format(sqlMethod.getSql(),tableInfo.getTableName());
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return addDeleteMappedStatement(mapperClass, sqlMethod.getMethod(), sqlSource);
    }
}
