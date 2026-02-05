package com.yunke.admin.modular.business.repair.model.entity;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.util.Date;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.yunke.admin.common.base.BaseEntity;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 *
 * @author mcllb
 * @date 2026-01-22
 */
@Data
@TableName("biz_repair_info")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "RepairInfo对象", description = "RepairInfo对象")
public class RepairInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;


    @ApiModelProperty(value = "工单编号")
    @TableField("work_order_no")
    private String workOrderNo;

    @ApiModelProperty(value = "工单状态")
    @TableField("work_order_status")
    private String workOrderStatus;

    @ApiModelProperty(value = "客户名称")
    @TableField("company_name")
    private String companyName;

    @ApiModelProperty(value = "外键客户id")
    @TableField("customer_id")
    private String customerId;

    @ApiModelProperty(value = "工单类型")
    @TableField("work_order_type")
    private String workOrderType;

    @ApiModelProperty(value = "报修事项")
    @TableField("repair_issues")
    private String repairIssues;

    @ApiModelProperty(value = "需求描述")
    @TableField("require_des")
    private String requireDes;

    @ApiModelProperty(value = "地址")
    @TableField("address")
    private String address;

    @ApiModelProperty(value = "上报时间")
    @TableField("report_time")
    private LocalDateTime reportTime;

    @ApiModelProperty(value = "期望完成日期")
    @TableField("expect_time")
    private LocalDate expectTime;


    @ApiModelProperty(value = "维修人员姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "维修人员联系电话")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "工单处理结果")
    @TableField("work_order_result")
    private String workOrderResult;

    @ApiModelProperty(value = "处理描述")
    @TableField("handle_desc")
    private String handleDesc;

    @ApiModelProperty(value = "处理时间")
    @TableField("handle_time")
    private LocalDateTime handleTime;


    @ApiModelProperty(value = "客户评价结果（满意 不满意）")
    @TableField("review_result")
    private String reviewResult;

    @ApiModelProperty(value = "原因（不满意使用）")
    @TableField("reason")
    private String reason;

    @ApiModelProperty(value = "客户评价时间")
    @TableField("review_time")
    private LocalDateTime reviewTime;





    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;
}