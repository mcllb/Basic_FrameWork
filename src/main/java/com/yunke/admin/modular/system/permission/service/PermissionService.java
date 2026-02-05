package com.yunke.admin.modular.system.permission.service;

import cn.hutool.core.lang.tree.Tree;
import com.yunke.admin.common.model.MenuTreeNode;
import com.yunke.admin.common.model.SingleDeleteParam;
import com.yunke.admin.framework.mybatisplus.base.GeneralService;
import com.yunke.admin.modular.system.auth.model.vo.LoginUserVO;
import com.yunke.admin.modular.system.permission.model.entity.Permission;
import com.yunke.admin.modular.system.permission.model.param.*;
import com.yunke.admin.modular.system.permission.model.vo.PermissionListVO;
import com.yunke.admin.modular.system.permission.model.vo.RoleGrantPermissionTreeVO;
import com.yunke.admin.modular.system.permission.model.vo.RouterVo;

import java.util.List;

/**
 * @className PermissionService
 * @description: 系统权限表_服务类
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
public interface PermissionService extends GeneralService<Permission> {

    List<PermissionListVO> list(PermissionListQueryParam permissionListQueryParam);

    boolean add(PermissionAddParam permissionAddParam);

    boolean edit(PermissionEditParam permissionEditParam);

    boolean delete(SingleDeleteParam singleDeleteParam);

    List<Tree<String>> treeSelect();

    boolean updatePermissionStatus(PermissionUpdateStatusParam permissionUpdateStatusParam);

    boolean updatePermissionSort(PermissionUpdateSortParam permissionUpdateSortParam);

    List<MenuTreeNode> userMenuTree(LoginUserVO loginUserVO);

    List<RouterVo> getUserMenus(LoginUserVO loginUserVO);

    RoleGrantPermissionTreeVO getRoleGrantPermissonTreeData(QueryRoleGrantPermissionParam queryRoleGrantPermissionParam);


}
