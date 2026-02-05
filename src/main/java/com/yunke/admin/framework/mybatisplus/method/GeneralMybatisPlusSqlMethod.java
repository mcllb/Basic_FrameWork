package com.yunke.admin.framework.mybatisplus.method;

/**
 * @className GeneralMybatisPlusSqlMethod
 * @description: MybatisPlus自定义SQL方法
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
public enum GeneralMybatisPlusSqlMethod {

    /**
     * 修改所有字段
     */
    UPDATE_ALL_COLUMN_BY_ID("updateAllColumnById", "根据ID更新所有数据", "<script>\nUPDATE %s %s WHERE %s=#{%s} %s\n</script>"),

    /**
     * 清空表
     */
    TRUNCATE_TABLE("truncateTable","清空表","<script>\nTRUNCATE TABLE %s \n</script>"),

    /**
     * 删除全部
     */
    DELETE_ALL("deleteAll","删除全部","<script>\nDELETE FROM %s \n</script>"),

    ///**
    // * 根据实体类属性删除
    // */
    //DELETE_BY_FIELD("deleteByField","根据实体类属性删除","<script>\nDELETE FROM %s WHERE %s=#{fieldValue} \n</script>"),

    ;

    private final String method;
    private final String desc;
    private final String sql;

    GeneralMybatisPlusSqlMethod(String method, String desc, String sql) {
        this.method = method;
        this.desc = desc;
        this.sql = sql;
    }

    public String getMethod() {
        return method;
    }

    public String getDesc() {
        return desc;
    }

    public String getSql() {
        return sql;
    }
}
