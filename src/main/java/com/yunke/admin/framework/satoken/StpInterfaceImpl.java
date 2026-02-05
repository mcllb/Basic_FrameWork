package com.yunke.admin.framework.satoken;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.collection.CollUtil;
import com.yunke.admin.common.util.SaUtil;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class StpInterfaceImpl implements StpInterface {

    /**
     * @description: 返回一个账号所拥有的权限码集合
     * <p></p>
     * @param loginId
     * @param loginType
     * @return java.util.List<java.lang.String>
     * @auth: tianlei
     * @date: 2026/1/14 15:22
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return CollUtil.newArrayList(SaUtil.getPermissionCodeList());
    }

    /**
     * @description: 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     * <p></p>
     * @param loginId
     * @param loginType
     * @return java.util.List<java.lang.String>
     * @auth: tianlei
     * @date: 2026/1/14 15:22
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return CollUtil.newArrayList(SaUtil.getRoleCodeList());
    }
}
