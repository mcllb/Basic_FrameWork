package com.yunke.admin.modular.business.wx.cust.model.vo;

import com.yunke.admin.common.enums.DataDictTypeEnum;
import com.yunke.admin.framework.core.annotion.DataDictField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author: MCLLB
 * @Date: 2026/1/27 16:05
 * @Version: v1.0.0
 * @Description:
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "CustRepairInfoRspVO对象", description = "CustRepairInfoRspVO对象")
public class CustRepairInfoListRspVO {

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "报修事项")
    private String repairIssues;

    @ApiModelProperty(value = "上报时间")
    private LocalDateTime reportTime;

    @ApiModelProperty(value = "工单状态")
    @DataDictField(DataDictTypeEnum.BIZ_WORKORDER_STATUS)
    private String workOrderStatus;
    private String workOrderStatusText;

    @ApiModelProperty(value = "维修人员姓名")
    private String name;

    @ApiModelProperty(value = "维修人员联系电话")
    private String phone;


}
