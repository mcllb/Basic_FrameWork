package com.yunke.admin.framework.mybatisplus.method;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import io.micrometer.core.instrument.util.StringUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import static java.util.stream.Collectors.joining;

public class UpdateAllColumnById extends GeneralAbstractMethod {

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {

        GeneralMybatisPlusSqlMethod sqlMethod = GeneralMybatisPlusSqlMethod.UPDATE_ALL_COLUMN_BY_ID;
        String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(),
                sqlSet(false, false, tableInfo, Constants.ENTITY_DOT),
                tableInfo.getKeyColumn(), Constants.ENTITY_DOT + tableInfo.getKeyProperty(),
                new StringBuilder("<if test=\"et instanceof java.util.Map\">")
                        .append("<if test=\"et.MP_OPTLOCK_VERSION_ORIGINAL!=null\">")
                        .append(" AND ${et.MP_OPTLOCK_VERSION_COLUMN}=#{et.MP_OPTLOCK_VERSION_ORIGINAL}")
                        .append("</if></if>"));

        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return addUpdateMappedStatement(mapperClass, modelClass, sqlMethod.getMethod(), sqlSource);
    }



    protected String sqlSet(boolean logic, boolean ew, TableInfo table, String prefix) {
        String newPrefix = prefix == null ? StringPool.EMPTY : prefix;
        String sqlScript = table.getFieldList().stream()
                .filter(i -> {
                    return true;
                })
                .map(i -> {
                    return this.getSqlSet(i, newPrefix);
                }).collect(joining(StringPool.NEWLINE));

        if (ew) {
            sqlScript += StringPool.NEWLINE;
            sqlScript += SqlScriptUtils.convertIf(SqlScriptUtils.unSafeParam(Constants.U_WRAPPER_SQL_SET),
                    String.format("%s != null and %s != null", Constants.WRAPPER, Constants.U_WRAPPER_SQL_SET), false);
        }
        sqlScript = SqlScriptUtils.convertTrim(sqlScript, "SET", null, null, ",");
        return sqlScript;
    }

    public String getSqlSet(TableFieldInfo i, String prefix) {
        String newPrefix = prefix == null ? StringPool.EMPTY : prefix;
        String column = i.getColumn();
        String update = i.getUpdate();
        FieldFill fieldFill = i.getFieldFill();
        String el = i.getEl();

        // 默认: column=
        String sqlSet = column + StringPool.EQUALS;
        if (StringUtils.isNotEmpty(update)) {
            sqlSet += String.format(update, column);
        } else {
            sqlSet += SqlScriptUtils.safeParam(newPrefix + el);
        }
        sqlSet += StringPool.COMMA;
        if (fieldFill == FieldFill.UPDATE || fieldFill == FieldFill.INSERT_UPDATE) {
            // 不进行 if 包裹
            return sqlSet;
        }
        return sqlSet;
    }
}
