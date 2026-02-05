package com.yunke.admin.modular.business.repair.model.vo;

import com.yunke.admin.common.base.BaseVO;
import com.yunke.admin.common.enums.DataDictTypeEnum;
import com.yunke.admin.framework.core.annotion.DataDictField;
import com.yunke.admin.framework.core.annotion.DeptField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author mcllb
 * @date 2026-01-22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "RepairInfo对象", description = "RepairInfo对象")
public class RepairInfoVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "工单编号")
    private String workOrderNo;

    @ApiModelProperty(value = "工单状态")
    @DataDictField(DataDictTypeEnum.BIZ_WORKORDER_STATUS)
    private String workOrderStatus;
    private String workOrderStatusText;

    @ApiModelProperty(value = "客户名称")
    private String companyName;

    @ApiModelProperty(value = "外键客户id")
    private String customerId;

    @ApiModelProperty(value = "工单类型")
    @DataDictField(DataDictTypeEnum.BIZ_WORKORDER_TYPE)
    private String workOrderType;
    private String workOrderTypeText;

    @ApiModelProperty(value = "报修事项")
    private String repairIssues;

    @ApiModelProperty(value = "需求描述")
    private String requireDes;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "上报时间")
    private LocalDateTime reportTime;

    @ApiModelProperty(value = "期望完成日期")
    private LocalDate expectTime;


    @ApiModelProperty(value = "维修人员姓名")
    private String name;

    @ApiModelProperty(value = "维修人员联系电话")
    private String phone;

    @ApiModelProperty(value = "工单处理结果")
    @DataDictField(DataDictTypeEnum.BIZ_WORKORDER_RESULT)
    private String workOrderResult;
    private String workOrderResultText;

    @ApiModelProperty(value = "处理描述")
    private String handleDesc;

    @ApiModelProperty(value = "处理时间")
    private LocalDateTime handleTime;

    @ApiModelProperty(value = "客户评价结果（满意 不满意）")
    private String reviewResult;

    @ApiModelProperty(value = "原因（不满意使用）")
    private String reason;

    @ApiModelProperty(value = "客户评价时间")
    private LocalDateTime reviewTime;

    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 维修组
     */
    /**
     * 维修组
     */
    @DeptField
    private String repairTeam;
    private String repairTeamText;

    @ApiModelProperty(value = "客户上报附件的ids")
    private List<String> sbfjList;

    @ApiModelProperty(value = "维修人员上报附件的ids")
    private List<String> wxfjList;
}