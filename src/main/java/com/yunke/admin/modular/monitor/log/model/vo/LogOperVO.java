package com.yunke.admin.modular.monitor.log.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.yunke.admin.common.base.BaseVO;
import com.yunke.admin.framework.core.annotion.EnumDictField;
import com.yunke.admin.framework.core.enums.LogStatusEnum;
import com.yunke.admin.framework.core.enums.OpLogAnnotionOpTypeEnum;
import com.yunke.admin.modular.system.auth.enums.LoginDeviceEnum;
import com.yunke.admin.modular.system.wx.enums.WxUserTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class LogOperVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 操作类型
     */
    @EnumDictField(OpLogAnnotionOpTypeEnum.class)
    private String opType;

    private String opTypeText;

    /**
     * 是否执行成功（Y-是，N-否）
     */
    @EnumDictField(LogStatusEnum.class)
    private String status;

    private String statusText;

    /**
     * 具体消息
     */
    private String errorMessage;

    /**
     * 堆栈追踪信息
     */
    private String stackTrace;

    /**
     * ip
     */
    private String ip;

    /**
     * 地址
     */
    private String location;

    /**
     * 用户代理
     */
    private String userAgent;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 类名称
     */
    private String className;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 请求方式（GET POST PUT DELETE)
     */
    private String reqMethod;

    /**
     * 请求参数
     */
    private String param;

    /**
     * 返回结果
     */
    private String result;

    /**
     * 操作时间
     */
    private Date opTime;

    /**
     * 操作账号
     */
    private String account;

    /**
     * 操作用时（毫秒）
     */
    private Long useTime;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 设备类型
     */
    @EnumDictField(LoginDeviceEnum.class)
    private String device;
    private String deviceText;

    /**
     * 用户类型
     */
    @EnumDictField(WxUserTypeEnum.class)
    private String userType;
    private String userTypeText;
}
