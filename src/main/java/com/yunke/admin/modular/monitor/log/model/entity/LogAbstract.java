package com.yunke.admin.modular.monitor.log.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

@Data
public class LogAbstract implements Serializable {

    protected static final long serialVersionUID = 1L;

    /**
     * 异常信息
     */
    @TableField("error_message")
    private String errorMessage;

    /**
     * 堆栈追踪信息
     */
    @TableField("stack_trace")
    private String stackTrace;

    /**
     * ip
     */
    @TableField("ip")
    private String ip;

    /**
     * 地址
     */
    @TableField("location")
    private String location;

    /**
     * 用户代理
     */
    @TableField("user_agent")
    private String userAgent;

    /**
     * 浏览器
     */
    @TableField("browser")
    private String browser;

    /**
     * 操作系统
     */
    @TableField("os")
    private String os;

    /**
     * 请求方式（GET POST PUT DELETE)
     */
    @TableField("req_method")
    private String reqMethod;
    /**
     * 请求地址
     */
    @TableField("url")
    private String url;
    /**
     * 请求参数
     */
    @TableField("param")
    private String param;

    /**
     * 返回结果
     */
    @TableField("result")
    private String result;

    /**
     * 设备类型
     */
    @TableField("device")
    private String device;

    /**
     * 用户名称
     */
    @TableField("user_name")
    private String userName;

    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 用户类型
     */
    @TableField("user_type")
    private String userType;

}
