package com.yunke.admin.modular.system.auth.model.vo;

import com.yunke.admin.common.base.BaseVO;
import com.yunke.admin.common.constant.CommonConstant;
import com.yunke.admin.modular.system.permission.model.vo.RouterVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @className WxLoginUserVO
 * @description: 微信登录用户信息模型
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class WxLoginUserVO extends BaseVO {

    /**
     * 登录id（实际为微信openid）
     */
    private String id;
    /**
     * 账号（实际为微信手机号）
     */
    private String account;
    /**
     * 用户Id
     */
    private String userId;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 用户类型（sys系统用户 cust企业用户）
     */
    private String userType;

    /**
     * 手机
     */
    private String phone;

    /**
     * 部门id
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
     * 拥有的角色
     */
    private List<String> roles;

    /**
     * 拥有的权限
     */
    private List<String> permissions;

    /**
     * 用户拥有的路由
     */
    private List<RouterVo> routers;

    /**
     * 用户token
     */
    private String token;

    public boolean isAdmin(){
        if(this == null){
            return false;
        }
        return CommonConstant.ADMIN_ID.equals(this.userId);
    }

}
