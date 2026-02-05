package com.yunke.admin.modular.system.user.service.impl;

import com.yunke.admin.framework.mybatisplus.base.GeneralServiceImpl;
import com.yunke.admin.modular.system.user.mapper.UserRoleMapper;
import com.yunke.admin.modular.system.user.model.entity.UserRole;
import com.yunke.admin.modular.system.user.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * @className UserRoleServiceImpl
 * @description: 系统用户与角色关系表_服务实现类
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Service
public class UserRoleServiceImpl extends GeneralServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
