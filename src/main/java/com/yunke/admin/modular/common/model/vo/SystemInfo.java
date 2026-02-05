package com.yunke.admin.modular.common.model.vo;

import lombok.Data;

/**
 * @program: yunke-admin
 * @description:
 * @author: tianlei
 * @date: 2021-1-8
 */
@Data
public class SystemInfo {

    /**
     * 项目名称
     */
    private String systemName;

    /**
     * 版本号
     */
    private String version;

    /**
     * 网站署名
     */
    private String copyRight;

    /**
     * 验证码类型
     */
    private String captchaType;

    /**
     * 验证码开关
     */
    private boolean captchaEnable;

    /**
     * token名称
     */
    private String tokenName;

}
