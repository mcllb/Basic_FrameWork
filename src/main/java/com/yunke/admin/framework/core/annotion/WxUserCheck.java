package com.yunke.admin.framework.core.annotion;

import cn.dev33.satoken.annotation.SaMode;
import com.yunke.admin.modular.system.wx.enums.WxUserTypeEnum;

import java.lang.annotation.*;

/**
  * @className WxUserCheckLogin
  * @description 微信用户登陆鉴权注解
  * <p></p>
  * @version 1.0
  * @author tianlei
  * @date 2026/1/22
  */
@Documented
@Target({ ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface WxUserCheck {


    boolean checkLogin() default true;

    /**
     * 用户类型
     */
    WxUserTypeEnum userType() default WxUserTypeEnum.DFAULT;

    /**
     * 用户角色
     */
    String[] roles() default {};

    /**
     * 校验角色是并列 还是 或
     */
    SaMode mode() default SaMode.OR;

}
