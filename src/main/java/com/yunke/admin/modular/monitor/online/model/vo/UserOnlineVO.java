package com.yunke.admin.modular.monitor.online.model.vo;

import com.yunke.admin.framework.core.annotion.EnumDictField;
import com.yunke.admin.modular.system.auth.enums.LoginDeviceEnum;
import lombok.Data;
import java.util.Date;

@Data
public class UserOnlineVO {

    private String id;
    /**
     * 账号
     */
    private String account;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 部门
     */
    private String deptId;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 部门编号
     */
    private String deptCode;
    /**
     * 登陆IP
     */
    private String loginIp;
    /**
     * 登陆地点
     */
    private String loginLocation;
    /**
     * 浏览器
     */
    private String browser;
    /**
     * 操作系统
     */
    private String os;
    /**
     * 登陆时间
     */
    private Date loginTime;
    /**
     *  最新操作时间
     */
    private Date lastOperateTime;
    /**
     * 登录令牌
     */
    private String token;
    /**
     * token-session id
     */
    private String tokenSessionId;
    /**
     * session id
     */
    private String sessionId;

    @EnumDictField(LoginDeviceEnum.class)
    private String device;
    private String deviceText;
}
