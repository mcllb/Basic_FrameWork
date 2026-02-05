package com.yunke.admin.modular.home.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CameraApiLogSumVO {
    /**
     * 调用总数
     */
    private int totalCount;
    /**
     * 正常数
     */
    private int onlineCount;
    /**
     * 异常数
     */
    private int offlineCount;

}
