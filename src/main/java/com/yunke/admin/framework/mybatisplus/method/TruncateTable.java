package com.yunke.admin.framework.mybatisplus.method;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

public class TruncateTable extends GeneralAbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        GeneralMybatisPlusSqlMethod sqlMethod = GeneralMybatisPlusSqlMethod.TRUNCATE_TABLE;
        String sql = String.format(sqlMethod.getSql(),tableInfo.getTableName());
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return addUpdateMappedStatement(mapperClass, modelClass, sqlMethod.getMethod(), sqlSource);
    }
}
