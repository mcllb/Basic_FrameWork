package com.yunke.admin.framework.mybatisplusjoin;

import com.github.yulichang.wrapper.enums.BaseFuncEnum;

/**
 * 自定义聚合函数
 */
/**
 * @className FuncEnum
 * @description: MPJ自定义聚合函数
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
public enum FuncEnum implements BaseFuncEnum {

    //日期格式化 由于sql会调用String.format, 用%%表示一个% 最终sql DATE_FORMAT(xxx,'%Y-%m-%d')
    DATE_FORMAT("DATE_FORMAT(%s,'%%Y-%%m-%%d')"),         //日期格式化
    IF_SEX("IF(%s=1,'男','女')"),                         //if 性别转换
    CASE_SEX("CASE %s WHEN 1 THEN '男' ELSE '女' END"),   //case 性别转换
    FIRST("FIRST(%s)"),
    LAST("LAST(%s)"),
    UCASE("UCASE(%s)"),
    LCASE("LCASE(%s)");

    private final String sql;

    private FuncEnum(String sql) {
        this.sql = sql;
    }

    @Override
    public String getSql() {
        return this.sql;
    }

}
