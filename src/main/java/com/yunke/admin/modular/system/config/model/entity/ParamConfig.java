package com.yunke.admin.modular.system.config.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yunke.admin.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @className ParamConfig
 * @description: 系统参数配置表_实体
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_param_config")
public class ParamConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 参数名称
     */
    @TableField("config_name")
    private String configName;

    /**
     * 参数键名
     */
    @TableField("config_key")
    private String configKey;

    /**
     * 参数键值
     */
    @TableField("config_value")
    private String configValue;

    /**
     * 配置类型（枚举字典：SYS_CONFIG_TYPE）
     */
    @TableField("config_type")
    private String configType;

    /**
     * 数据类型（枚举字典：SYS_CONFIG_DATA_TYPE）
     */
    @TableField("data_type")
    private String dataType;

    /**
     * 状态（字典：1正常，0停用）
     */
    @TableField("status")
    private String status;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;


}
