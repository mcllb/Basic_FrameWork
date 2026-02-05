package com.yunke.admin.modular.business.repair.model.param;

import com.yunke.admin.common.base.BasePageQueryParam;
import lombok.*;
import io.swagger.annotations.ApiModel;

import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author mcllb
 * @date 2026-01-22
 */
@Data
@ApiModel(value = "RepairInfoPageQueryParam对象", description = "RepairInfoPageQueryParam对象")
public class RepairInfoPageQueryParam extends BasePageQueryParam {

    private String companyName;

    private String repairIssues;

    private String workOrderStatus;

    private String workOrderType;

    private String repairTeam;

    private String name;

    private String workOrderResult;

    private String reviewResult;

    private LocalDateTime reportTimeStart;

    private LocalDateTime reportTimeEnd;

}