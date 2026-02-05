package com.yunke.admin.modular.home.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CameraOfflinestatSumVO {

    /**
     * 告警总数
     */
    private int totalCount;
    /**
     * 当日告警设备
     */
    private int todayCountCamera;
    /**
     * 当日告警数
     */
    private int todayCount;
}
