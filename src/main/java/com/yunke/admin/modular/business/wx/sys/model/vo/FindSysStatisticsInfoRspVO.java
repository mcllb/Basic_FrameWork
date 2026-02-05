package com.yunke.admin.modular.business.wx.sys.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: MCLLB
 * @Date: 2026/1/27 14:52
 * @Version: v1.0.0
 * @Description:
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "查询小程序客户端统计信息出参 VO")
public class FindSysStatisticsInfoRspVO {

    private Integer allTotalCount;

    private Integer repairTotalCount;

    private Integer completeTotalCount;

    private Integer freezeTotalCount;
}
