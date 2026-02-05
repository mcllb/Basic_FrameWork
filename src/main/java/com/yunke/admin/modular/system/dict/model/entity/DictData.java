package com.yunke.admin.modular.system.dict.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yunke.admin.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @className DictData
 * @description: 系统字典值表_实体
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dict_data")
public class DictData extends BaseEntity {


    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 字典类型id
     */
    @TableField("type_id")
    private String typeId;

    /**
     * 字典类型编码
     */
    @TableField("type_code")
    private String typeCode;

    /**
     * 值
     */
    @TableField("value")
    private String value;

    /**
     * 编码
     */
    @TableField("code")
    private String code;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 状态（字典: 1正常 0停用）
     */
    @TableField("status")
    private String status;


}
