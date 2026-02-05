package com.yunke.admin.modular.business.customer.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.yunke.admin.common.base.BaseVO;
import com.yunke.admin.common.enums.DataDictTypeEnum;
import com.yunke.admin.framework.core.annotion.DataDictField;
import com.yunke.admin.framework.core.annotion.DeptField;
import lombok.*;

/**
 * <p></p>
 * @version 1.0
 * @author MCLLB
 * @date 2026/1/16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class CustomerInfoVO extends BaseVO {

    private String id;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 所属区域
     */
    @DataDictField(DataDictTypeEnum.BIZ_AREA)
    private String area;
    private String areaText;

    /**
     * 公司地址
     */
    private String companyAddress;

    /**
     * 维修组
     */
    @DeptField
    private String repairTeam;
    private String repairTeamText;

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
    private String name;

    /**
     * 报修人性别
     */
    private String sex;

    /**
     * 报修人电话
     */
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
