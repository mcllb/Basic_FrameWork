package com.yunke.admin.modular.business.repair.model.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.util.Date;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.yunke.admin.common.base.BaseAddParam;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author mcllb
 * @date 2026-01-22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "RepairInfoAddParam对象", description = "RepairInfoAddParam对象")
public class RepairInfoAddParam extends BaseAddParam {

        @ApiModelProperty(value = "工单编号")
        private String workOrderNo;

        @ApiModelProperty(value = "工单状态")
        private String workOrderStatus;

        @ApiModelProperty(value = "客户名称")
        private String companyName;

        @ApiModelProperty(value = "外键客户id")
        private String customerId;

        @ApiModelProperty(value = "工单类型")
        private String workOrderType;

        @ApiModelProperty(value = "报修事项")
        private String repairIssues;

        @ApiModelProperty(value = "需求描述")
        private String requireDes;

        @ApiModelProperty(value = "地址")
        private String address;

        @ApiModelProperty(value = "上报时间")
        private LocalDateTime reportTime;

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @ApiModelProperty(value = "期望完成日期")
        private LocalDate expectTime;

        @ApiModelProperty(value = "维修人员姓名")
        private String name;

        @ApiModelProperty(value = "维修人员联系电话")
        private String phone;

        @ApiModelProperty(value = "工单处理结果")
        private String workOrderResult;

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

        @ApiModelProperty(value = "客户现场图片ids")
        List<String> fileIds;

}