package com.yunke.admin.framework.mybatisplus.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.github.yulichang.injector.MPJSqlInjector;
import com.yunke.admin.framework.mybatisplus.method.DeleteAll;
import com.yunke.admin.framework.mybatisplus.method.TruncateTable;
import com.yunke.admin.framework.mybatisplus.method.UpdateAllColumnById;

import java.util.List;


/**
 * @className GeneralMybatisPlusSqlInjector
 * @description: 自定义sql注入器，增加通用方法
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
public class GeneralMybatisPlusSqlInjector extends MPJSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        // 根据id更新所有数据
        methodList.add(new UpdateAllColumnById());
        // 清空表
        methodList.add(new TruncateTable());
        // 删除表
        methodList.add(new DeleteAll());
        return methodList;
    }
}
