package com.yunke.admin.modular.monitor.log.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @className LogOper
 * @description: 系统操作日志表
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_log_oper")
public class LogOper extends LogAbstract implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 标题
     */
    @TableField("title")
    private String title;
    /**
     * 操作类型
     */
    @TableField("op_type")
    private String opType;

    /**
     * 是否执行成功（Y-是，N-否）
     */
    @TableField("status")
    private String status;
    /**
     * 类名称
     */
    @TableField("class_name")
    private String className;
    /**
     * 方法名称
     */
    @TableField("method_name")
    private String methodName;

    /**
     * 操作时间
     */
    @TableField("op_time")
    private Date opTime;

    /**
     * 操作账号
     */
    @TableField("account")
    private String account;

    /**
     * 操作用时（毫秒）
     */
    @TableField("use_time")
    private Long useTime;

}
