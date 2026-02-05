package com.yunke.admin.framework.mybatisplus;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.yunke.admin.framework.components.RedisIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomIdGenerator implements IdentifierGenerator {

    @Autowired
    private RedisIdWorker redisIdWorker;

    @Override
    public Number nextId(Object entity) {
        log.debug("CustomIdGenerator ======>>>entity={}",entity);
        TableInfo tableInfo = TableInfoHelper.getTableInfo(entity.getClass());
        String tableName = tableInfo.getTableName();
        log.debug("CustomIdGenerator.nextId======>>>class={},tableName={}",entity.getClass().getName(),tableName);
        return redisIdWorker.nextId(tableName);
    }
}
