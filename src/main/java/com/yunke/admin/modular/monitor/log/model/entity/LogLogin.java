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
 * @className LogLogin
 * @description: 系统访问日志表
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_log_login")
public class LogLogin extends LogAbstract implements Serializable {

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
     * 操作类型（字典 1登入 2登出）
     */
    @TableField("vis_type")
    private String visType;
    /**
     * 访问时间
     */
    @TableField("vis_time")
    private Date visTime;
    /**
     * 访问账号
     */
    @TableField("account")
    private String account;
    /**
     * 登录id
     */
    @TableField("login_id")
    private String loginId;




}
