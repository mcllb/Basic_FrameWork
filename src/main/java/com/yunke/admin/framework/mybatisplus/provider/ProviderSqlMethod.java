package com.yunke.admin.framework.mybatisplus.provider;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.reflection.property.PropertyNamer;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;


public class ProviderSqlMethod {

    /**
     * @description: 根据实体类属性删除
     * <p></p>
     * @param field
     * @return java.lang.String
     * @auth: tianlei
     * @date: 2026/1/14 15:38
     */
    public String deleteByField(SFunction<?, ?> field){
        SerializedLambda lambda = LambdaUtils.resolve(field);
        Class<?> aClass = lambda.getInstantiatedType();
        String tableName = TableInfoHelper.getTableInfo(aClass).getTableName();
        String columnName = PropertyNamer.methodToProperty(lambda.getImplMethodName());
        SQL sql = new SQL();
        sql.DELETE_FROM(tableName).WHERE("%s=#{fieldValue}");
        return String.format(sql.toString(),columnName);
    }

    /**
     * @description: 根据id查询实体部分字段
     * <p></p>
     * @param fields
     * @return java.lang.String
     * @auth: tianlei
     * @date: 2026/1/14 15:38
     */
    public String selectFielsById(SFunction<?, ?>... fields){
        Assert.notEmpty(fields);
        SerializedLambda lambda = LambdaUtils.resolve(fields[0]);
        Class<?> aClass = lambda.getInstantiatedType();
        TableInfo tableInfo = TableInfoHelper.getTableInfo(aClass);
        String tableName = tableInfo.getTableName();
        String keyColumn = tableInfo.getKeyColumn();


        Set<String> columnSet = Arrays.stream(fields).map(field -> {
            SerializedLambda fieldLambda = LambdaUtils.resolve(field);
            return PropertyNamer.methodToProperty(fieldLambda.getImplMethodName());
        }).collect(Collectors.toSet());
        String[] columns = ArrayUtil.toArray(columnSet, String.class);
        SQL sql = new SQL();
        sql.SELECT(columns).FROM(tableName).WHERE("%s=#{id}");
        return String.format(sql.toString(),keyColumn);
    }

}
