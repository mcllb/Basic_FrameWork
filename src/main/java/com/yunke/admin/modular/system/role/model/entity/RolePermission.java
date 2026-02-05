package com.yunke.admin.modular.system.role.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @className RolePermission
 * @description: 系统角色与权限关系表_实体
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
@TableName("sys_role_permission")
public class RolePermission {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 角色id
     */
    @TableField("role_id")
    private String roleId;

    /**
     * 权限id
     */
    @TableField("permission_id")
    private String permissionId;


}
