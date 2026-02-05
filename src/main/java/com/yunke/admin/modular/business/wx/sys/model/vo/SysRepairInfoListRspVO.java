package com.yunke.admin.modular.business.wx.sys.model.vo;

import com.yunke.admin.common.enums.DataDictTypeEnum;
import com.yunke.admin.framework.core.annotion.DataDictField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
@ApiModel(value = "SysRepairInfoRspVO对象", description = "SysRepairInfoRspVO对象")
public class SysRepairInfoListRspVO {

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "报修事项")
    private String repairIssues;

    @ApiModelProperty(value = "上报时间")
    private LocalDateTime reportTime;

    @ApiModelProperty(value = "期望完成时间")
    private LocalDate expectTime;

    @ApiModelProperty(value = "工单状态")
    @DataDictField(DataDictTypeEnum.BIZ_WORKORDER_STATUS)
    private String workOrderStatus;
    private String workOrderStatusText;

    @ApiModelProperty(value = "报修人员姓名")
    private String customerName;

    @ApiModelProperty(value = "报修人员联系电话")
    private String customerPhone;

    @ApiModelProperty(value = "用户单位")
    private String companyName;


}
