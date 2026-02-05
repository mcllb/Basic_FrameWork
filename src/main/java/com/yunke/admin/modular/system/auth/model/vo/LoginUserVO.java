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
 * @className LoginUserVO
 * @description: 登录用户信息模型
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class LoginUserVO extends BaseVO {

    private String id;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 加密密码的盐
     */
    private String salt;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 用户简称
     */
    private String shortName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别(字典: 0未知 1男 2女)
     */
    private String sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机
     */
    private String phone;

    /**
     * 电话
     */
    private String telphone;

    /**
     * 备注
     */
    private String remark;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 部门
     */
    private String deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

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
        return CommonConstant.ADMIN_ACCOUNT.equals(this.account);
    }

}
