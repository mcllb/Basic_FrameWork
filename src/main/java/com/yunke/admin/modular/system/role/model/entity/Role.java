package com.yunke.admin.modular.system.role.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yunke.admin.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @className Role
 * @description: 系统角色表_实体
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role")
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;

    /**
     * 角色编码
     */
    @TableField("role_code")
    private String roleCode;

    /**
     * 显示排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 数据范围类型（字典：1全部数据 2本部门及以下数据 3本部门数据 4仅本人数据 5自定义数据）
     */
    @TableField("data_scope_type")
    private Integer dataScopeType;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 状态（字典：1正常 0停用）
     */
    @TableField("status")
    private String status;

}
