package com.yunke.admin.modular.business.customer.model.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.yunke.admin.common.base.BaseAddParam;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @version 1.0
 * @author MCLLB
 * @date 2026/1/16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class CustomerInfoAddParam extends BaseAddParam {
    /**
     * 公司名称
     */
    @NotBlank(message = "公司名称不能为空")
    private String companyName;

    /**
     * 所属区域
     */
    @NotBlank(message = "所属区域不能为空")
    private String area;

    /**
     * 公司地址
     */
    private String companyAddress;

    /**
     * 维修组
     */
    @NotBlank(message = "维修组不能为空")
    private String repairTeam;

    /**
     * 公司电话
     */
    private String companyTel;

    /**
     * 公司简介
     */
    private String companyProfile;

    /**
     * 报修人姓名
     */
    @NotBlank(message = "报修人姓名不能为空")
    private String name;

    /**
     * 报修人性别
     */
    @NotBlank(message = "报修人性别不能为空")
    private String sex;

    /**
     * 报修人电话
     */
    @NotBlank(message = "报修人电话不能为空")
    private String phone;

    /**
     * 报修人职务
     */
    private String position;

    /**
     * 备注
     */
    private String remark;
}
