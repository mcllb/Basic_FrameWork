package com.yunke.admin.common.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.extra.spring.SpringUtil;
import com.yunke.admin.framework.config.ProjectConfig;

public class IdUtil extends cn.hutool.core.util.IdUtil {

    private IdUtil(){

    }

    public static long getWorkerId(){
        ProjectConfig projectConfig = SpringUtil.getBean(ProjectConfig.class);
        return projectConfig.getWorkerId();
    }

    public static long getDatacenterId(){
        ProjectConfig projectConfig = SpringUtil.getBean(ProjectConfig.class);
        return projectConfig.getDatacenterId();
    }

    public static Snowflake getSnowflake(){
        return getSnowflake(getWorkerId(), getDatacenterId());
    }

    public static long getSnowflakeNextId() {
        return getSnowflake().nextId();
    }

    public static String getSnowflakeNextIdStr() {
        return getSnowflake().nextIdStr();
    }

}
