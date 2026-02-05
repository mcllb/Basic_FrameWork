package com.yunke.admin.modular.home.model.vo;

import lombok.Data;

@Data
public class CameraSumVO {
    /**
     * 监控点总数
     */
    private int totalCount;
    /**
     * 在线数
     */
    private int onlineCount;
    /**
     * 在线数摄像头
     */
    private int onlineCount1;
    /**
     * 在线数屏幕监控
     */
    private int onlineCount2;
    /**
     * 离线数
     */
    private int offlineCount;
    /**
     * 离线数摄像头
     */
    private int offlineCount1;
    /**
     * 离线数屏幕监控
     */
    private int offlineCount2;


}
