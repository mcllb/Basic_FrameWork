package com.yunke.admin.framework.mybatisplus.provider;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

public interface ProviderMapper<T> {

    /**
     * @description: 根据实体类属性删除
     * <p></p>
     * @param field
     * @param fieldValue
     * @return int
     * @auth: tianlei
     * @date: 2026/1/14 15:38
     */
    @DeleteProvider(type = ProviderSqlMethod.class,method = "deleteByField")
    int deleteByField(SFunction<T, ?> field, @Param("fieldValue") Object fieldValue);

    @SelectProvider(type = ProviderSqlMethod.class,method = "selectFielsById")
    T selectFielsById(@Param("id") Object id,SFunction<T, ?>... fields);

}
