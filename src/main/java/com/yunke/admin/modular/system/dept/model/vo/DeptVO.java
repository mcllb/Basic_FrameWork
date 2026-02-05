package com.yunke.admin.modular.system.dept.model.vo;

import com.yunke.admin.common.base.BaseVO;
import com.yunke.admin.common.enums.CommonStatusEnum;
import com.yunke.admin.framework.core.annotion.EnumDictField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class DeptVO extends BaseVO {

    /**
     * 部门id
     */
    private String id;

    /**
     * 父级部门id
     */
    private String parentId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 部门编号
     */
    private String deptCode;

    /**
     * 部门领导
     */
    private String leader;

    /**
     * 显示排序
     */
    private Integer sort;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态（字典：1正常 0停用）
     */
    @EnumDictField(CommonStatusEnum.class)
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
