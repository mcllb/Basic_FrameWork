package com.yunke.admin.modular.monitor.online.model.param;

import lombok.Data;

@Data
public class UserOnlineQueryParam {

    /**
     * 账号
     */
    private String account;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 登录设备类型
     */
    private String device;


}
