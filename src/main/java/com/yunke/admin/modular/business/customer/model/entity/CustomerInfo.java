package com.yunke.admin.modular.business.customer.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.yunke.admin.common.base.BaseEntity;
import lombok.*;

/**
 *
 * @author mcllb
 * @since 2026-01-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("biz_customer_info")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 公司名称
     */
    @TableField("company_name")
    private String companyName;

    /**
     * 所属区域
     */
    @TableField("area")
    private String area;

    /**
     * 公司地址
     */
    @TableField("company_address")
    private String companyAddress;

    /**
     * 维修组
     */
    @TableField("repair_team")
    private String repairTeam;

    /**
     * 公司电话
     */
    @TableField("company_tel")
    private String companyTel;

    /**
     * 公司简介
     */
    @TableField("company_profile")
    private String companyProfile;

    /**
     * 报修人姓名
     */
    @TableField("name")
    private String name;

    /**
     * 报修人性别
     */
    @TableField("sex")
    private String sex;

    /**
     * 报修人电话
     */
    @TableField("phone")
    private String phone;

    /**
     * 报修人职务
     */
    @TableField("position")
    private String position;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;


}
