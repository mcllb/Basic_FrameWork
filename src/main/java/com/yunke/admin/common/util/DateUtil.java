package com.yunke.admin.common.util;

import cn.hutool.core.date.DatePattern;

import java.lang.management.ManagementFactory;
import java.util.Date;

public class DateUtil extends cn.hutool.core.date.DateUtil {

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 返回格式 2023-12-01T11:52:45.000+0800
     * @param date
     * @return
     */
    public static String formatISO(Date date){
        return DateUtil.format(date, DatePattern.UTC_MS_WITH_XXX_OFFSET_PATTERN);
    }

    /**
     * 比较两个日期是否相等
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isEquals(Date date1,Date date2){
        if(date1 == null && date2 == null){
            return true;
        }else if(date1 == null){
            return false;
        }else if(date2 == null){
            return false;
        }else{
            return date(date1).millisecond() == date(date2).millisecond();
        }
    }

    /**
     * 比较日期大于，null值始终最小 date1 > date2
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isAfter(Date date1,Date date2){
        if(date1 == null && date2 == null){
            return false;
        }else if(date1 == null){
            return false;
        }else if(date2 == null){
            return true;
        }else{
            return date(date1).isAfter(date2);
        }
    }
    /**
     * 比较日期大于等于，null值始终最小
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isAfterOrEquals(Date date1,Date date2){
        return isEquals(date1,date2) || isAfter(date1,date2);
    }
    /**
     * 比较日期小于等于，null值始终最小 date1 < date2
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isBefore(Date date1,Date date2){
        if(date1 == null && date2 == null){
            return false;
        }else if(date1 == null){
            return true;
        }else if(date2 == null){
            return false;
        }else{
            return date(date1).isBefore(date2);
        }
    }
    /**
     * 比较日期小于等于，null值始终最小
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isBeforeOrEquals(Date date1,Date date2){
        return isEquals(date1,date2) || isBefore(date1,date2);
    }



}
