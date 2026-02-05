package com.yunke.admin.modular.system.role.service;


import com.yunke.admin.common.model.SingleDeleteParam;
import com.yunke.admin.framework.mybatisplus.base.GeneralService;
import com.yunke.admin.modular.system.role.model.entity.Role;
import com.yunke.admin.modular.system.role.model.param.*;
import com.yunke.admin.modular.system.role.model.vo.RoleGrantUserTreeVO;
import com.yunke.admin.modular.system.role.model.vo.UserGrantRoleListVO;

import java.util.List;

/**
 * @className RoleService
 * @description: 系统角色表_服务类
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
public interface RoleService extends GeneralService<Role> {

    boolean add(RoleAddParam roleAddParam);

    boolean edit(RoleEditParam roleEditParam);

    boolean delete(SingleDeleteParam singleDeleteParam);

    boolean grantPermission(RoleGrantPermissionParam roleGrantPermissionParam);

    boolean updateRoleStatus(RoleUpdateStatusParam roleUpdateStatusParam);

    List<UserGrantRoleListVO> getUserGrantRoleList(QueryUserGrantRoleListParam queryUserGrantRoleListParam);

    RoleGrantUserTreeVO getGrantUserTreeDataByRoleId(String roleId);

    boolean grantUser(RoleGrantUserParam roleGrantUserParam);


}
