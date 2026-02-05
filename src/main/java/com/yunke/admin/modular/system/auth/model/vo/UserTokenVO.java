package com.yunke.admin.modular.system.auth.model.vo;

import lombok.Data;


/**
 * @className UserTokenVO
 * @description: 登录token模型
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/22
 */
@Data
public class UserTokenVO {

    private String account;

    private String userName;

    private String tokenValue;

    private String tokenName;

    private Object loginId;

    private String loginType;

    private String loginDevice;

    private String tag;
}
