package com.yunke.admin.modular.common.model.vo;

import lombok.Data;

/**
 * @program:
 * @description: 模拟登陆token返回数据
 * @author: tianlei
 * @date: 2021-3-24
 */
@Data
public class MockLoginTokenVO {

    /**
     * 令牌
     */
    private String token;

    /**
     * 登陆账号
     */
    private String account;

}
