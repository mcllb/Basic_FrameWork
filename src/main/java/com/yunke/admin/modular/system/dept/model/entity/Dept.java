package com.yunke.admin.modular.system.dept.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yunke.admin.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @className Dept
 * @description: 系统部门表_实体
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dept")
public class Dept extends BaseEntity {


    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 父级id
     */
    private String parentId;

    /**
     * 父级ids
     */
    private String parentIds;

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
    private String status;


}
