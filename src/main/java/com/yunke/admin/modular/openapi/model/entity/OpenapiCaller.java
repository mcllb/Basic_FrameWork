package com.yunke.admin.modular.openapi.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yunke.admin.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @className OpenapiCaller
 * @description: 开放接口调用方管理
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Data
@TableName("sys_openapi_caller")
@EqualsAndHashCode(callSuper = true)
public class OpenapiCaller extends BaseEntity {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @TableField(value = "id")
    private String id;
    /**
     * 调用者名称
     */
    @TableField(value = "caller_name")
    private String callerName;
    /**
     * 调用者标识
     */
    @TableField(value = "caller_key")
    private String callerKey;
    /**
     * 安全密钥
     */
    @TableField(value = "secret_key")
    private String secretKey;
    /**
     * 备注
     */
    @TableField(value = "reamrk")
    private String reamrk;
    /**
     * 状态（1正常0停用）
     */
    @TableField(value = "status")
    private String status;
}
