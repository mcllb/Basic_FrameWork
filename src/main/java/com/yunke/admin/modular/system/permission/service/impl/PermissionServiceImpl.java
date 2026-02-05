package com.yunke.admin.modular.system.permission.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yunke.admin.common.constant.SymbolConstant;
import com.yunke.admin.common.enums.CommonIfEnum;
import com.yunke.admin.common.enums.CommonStatusEnum;
import com.yunke.admin.common.model.ElementUITreeNode;
import com.yunke.admin.common.model.MenuTreeNode;
import com.yunke.admin.common.model.SingleDeleteParam;
import com.yunke.admin.common.util.BeanUtil;
import com.yunke.admin.framework.core.exception.ServiceException;
import com.yunke.admin.framework.core.util.SysConfigUtil;
import com.yunke.admin.framework.mybatisplus.base.GeneralServiceImpl;
import com.yunke.admin.modular.system.auth.model.vo.LoginUserVO;
import com.yunke.admin.modular.system.permission.constant.PermissionConstant;
import com.yunke.admin.modular.system.permission.enums.PermissionExceptionEnum;
import com.yunke.admin.modular.system.permission.enums.PermissionOpenTypeEnum;
import com.yunke.admin.modular.system.permission.enums.PermissionTypeEnum;
import com.yunke.admin.modular.system.permission.mapper.PermissionMapper;
import com.yunke.admin.modular.system.permission.model.entity.Permission;
import com.yunke.admin.modular.system.permission.model.param.*;
import com.yunke.admin.modular.system.permission.model.vo.MetaVo;
import com.yunke.admin.modular.system.permission.model.vo.PermissionListVO;
import com.yunke.admin.modular.system.permission.model.vo.RoleGrantPermissionTreeVO;
import com.yunke.admin.modular.system.permission.model.vo.RouterVo;
import com.yunke.admin.modular.system.permission.service.PermissionService;
import com.yunke.admin.modular.system.role.model.entity.Role;
import com.yunke.admin.modular.system.role.model.entity.RolePermission;
import com.yunke.admin.modular.system.role.service.RolePermissionService;
import com.yunke.admin.modular.system.role.service.RoleService;
import com.yunke.admin.modular.system.user.model.entity.UserRole;
import com.yunke.admin.modular.system.user.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @className PermissionServiceImpl
 * @description: 系统权限表_服务实现类
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Service
public class PermissionServiceImpl extends GeneralServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleService roleService;

    @Override
    public List<PermissionListVO> list(PermissionListQueryParam permissionListQueryParam) {
        // 查询顶级菜单
        LambdaQueryWrapper<Permission> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 查询条件不为空时按查询条件查询，否则查询全部
        if(StrUtil.isNotEmpty(permissionListQueryParam.getPermissionName())){
            lambdaQueryWrapper.like(Permission::getPermissionName,permissionListQueryParam.getPermissionName());
        }
        if(StrUtil.isNotEmpty(permissionListQueryParam.getStatus())){
            lambdaQueryWrapper.eq(Permission::getStatus, permissionListQueryParam.getStatus());
        }
        lambdaQueryWrapper.orderByAsc(Permission::getSort);
        List<Permission> permissions = this.list(lambdaQueryWrapper);
        List<PermissionListVO> permissionListVOS = BeanUtil.copyListProperties(permissions, PermissionListVO::new);
        return permissionListVOS;
    }

    // 递归填充子菜单
    public void fillChildren(PermissionListVO permissionListVO,String status){
        if(permissionListVO != null){
            // 查询子菜单
            LambdaQueryWrapper<Permission> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Permission::getParentId,permissionListVO.getId());
            if(StrUtil.isNotEmpty(status)){
                lambdaQueryWrapper.eq(Permission::getStatus, status);
            }
            lambdaQueryWrapper.orderByAsc(Permission::getSort);
            List<Permission> permissions = this.list(lambdaQueryWrapper);
            if(permissions != null && permissions.size() > 0){
                List<PermissionListVO> children = BeanUtil.copyListProperties(permissions, PermissionListVO::new);
                // 填充子菜单
                permissionListVO.setChildren(children);
                children.stream().forEach(child -> {
                    fillChildren(child,status);
                });
            }
        }
    }

    @Transactional
    @Override
    public boolean add(PermissionAddParam permissionAddParam) {
        Permission permission = new Permission();
        BeanUtil.copyProperties(permissionAddParam, permission);
        // 校验参数
        checkParam(permission,false);
        // 设置父级ids
        fillParentIds(permission);
        // 设置状态为启用
        permission.setStatus(CommonStatusEnum.ENABLE.getCode());
        return this.save(permission);
    }

    @Transactional
    @Override
    public boolean edit(PermissionEditParam permissionEditParam) {
        Permission permission = new Permission();
        BeanUtil.copyProperties(permissionEditParam, permission);
        // 校验参数
        checkParam(permission,true);
        // 设置父级ids
        fillParentIds(permission);
        String newParentId = permission.getParentId();
        Permission oldPermission = getById(permission.getId());
        String oldParentId = oldPermission.getParentId();
        // 如果修改了父节点，同时更新原子节点的parentIds
        if(!newParentId.equals(oldParentId)){
            updatePermisssionChildren(permission);
        }

        // 设置状态为启用
        permission.setStatus(CommonStatusEnum.ENABLE.getCode());
        // 不修改状态
        permission.setStatus(null);
        return this.updateById(permission);
    }

    @Transactional
    @Override
    public boolean delete(SingleDeleteParam singleDeleteParam) {
        // 判断当前节点及子节点是否有被角色关联
        List<String> deleteIdList = CollUtil.newArrayList();
        deleteIdList.add(singleDeleteParam.getId());
        List<String> chlldIdList = this.getChlldIdListById(singleDeleteParam.getId());
        if(CollUtil.isNotEmpty(chlldIdList)){
            deleteIdList.addAll(chlldIdList);
        }
        LambdaQueryWrapper<RolePermission> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(RolePermission::getPermissionId, deleteIdList);
        int roleCount = rolePermissionService.count(lambdaQueryWrapper);
        if(roleCount > 0){
            throw new ServiceException(PermissionExceptionEnum.USED_BY_ROLE);
        }
        return baseMapper.deleteBatchIds(deleteIdList) > 0;
    }

    @Transactional
    @Override
    public boolean updatePermissionStatus(PermissionUpdateStatusParam permissionUpdateStatusParam) {
        Permission permission = new Permission();
        BeanUtil.copyProperties(permissionUpdateStatusParam, permission);
        return this.updateById(permission);
    }

    @Override
    public boolean updatePermissionSort(PermissionUpdateSortParam permissionUpdateSortParam) {
        Permission permission = new Permission();
        BeanUtil.copyProperties(permissionUpdateSortParam, permission);
        return this.updateById(permission);
    }

    @Override
    public List<MenuTreeNode> userMenuTree(LoginUserVO loginUserVO) {
        boolean enableAdminRole = SysConfigUtil.getProjectConfig().isEnableAdminRole();
        List<MenuTreeNode> treeNodes = CollUtil.newArrayList();
        List<Permission> permissions = null;
        if(loginUserVO.isAdmin() && !enableAdminRole){
            // 系统管理员获取所有菜单
            LambdaQueryWrapper<Permission> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Permission::getStatus, CommonStatusEnum.ENABLE.getCode());
            lambdaQueryWrapper.in(Permission::getPermissionType, PermissionTypeEnum.DIR.getCode(),PermissionTypeEnum.MENU.getCode());
            lambdaQueryWrapper.orderByAsc(Permission::getSort);
            permissions = this.list(lambdaQueryWrapper);
        }else{
            // 非系统管理员获取角色授权的菜单
            // 查询用户拥有的角色
            Set<String> roleIds = CollUtil.newHashSet();
            LambdaQueryWrapper<UserRole> userRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userRoleLambdaQueryWrapper.eq(UserRole::getUserId, loginUserVO.getId());
            List<UserRole> userRoles = userRoleService.list(userRoleLambdaQueryWrapper);
            Set<String> userRoleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toSet());
            roleIds.addAll(userRoleIds);

            LambdaQueryWrapper<Role> roleLambdaQueryWrapper = new LambdaQueryWrapper<Role>();
            roleLambdaQueryWrapper.eq(Role::getStatus, CommonStatusEnum.ENABLE.getCode());
            roleLambdaQueryWrapper.in(Role::getId, roleIds);
            List<Role> roles = roleService.list(roleLambdaQueryWrapper);
            // 清空，存入已启用角色
            roleIds.clear();
            userRoleIds = roles.stream().map(Role::getId).collect(Collectors.toSet());
            roleIds.addAll(userRoleIds);
            // 查询角色与权限关联
            LambdaQueryWrapper<RolePermission> rolePermissionLambdaQueryWrapper = new LambdaQueryWrapper<>();
            rolePermissionLambdaQueryWrapper.in(RolePermission::getRoleId, roleIds);
            Set<String> permissionIds = CollUtil.newHashSet();
            List<RolePermission> rolePermissions = rolePermissionService.list(rolePermissionLambdaQueryWrapper);
            Set<String> userPermissionIds = rolePermissions.stream().map(RolePermission::getPermissionId).collect(Collectors.toSet());
            permissionIds.addAll(userPermissionIds);
            // 根据权限id集合查询
            LambdaQueryWrapper<Permission> permissionLambdaQueryWrapper = new LambdaQueryWrapper<>();
            permissionLambdaQueryWrapper.in(Permission::getId, permissionIds);
            permissionLambdaQueryWrapper.eq(Permission::getStatus, CommonStatusEnum.ENABLE.getCode());
            permissionLambdaQueryWrapper.in(Permission::getPermissionType, PermissionTypeEnum.DIR.getCode(),PermissionTypeEnum.MENU.getCode());
            permissionLambdaQueryWrapper.orderByAsc(Permission::getSort);
            permissions = this.list(permissionLambdaQueryWrapper);
        }
        if(permissions != null && permissions.size() > 0){
            // 获取顶级节点
            List<Permission> collect = permissions.stream().filter(permission -> {
                return permission.getParentId().equals(PermissionConstant.PERMISSION_ROOT_PARENT_ID);
            }).collect(Collectors.toList());
            List<MenuTreeNode> menuTreeNodes = this.convertTreeNode(collect);
            // 设置顶级节点
            treeNodes.addAll(menuTreeNodes);
            permissions.remove(collect);
            this.buildMenuTree(treeNodes,permissions);
        }
        return treeNodes;
    }

    /**
     * @description: 根据菜单集合构建菜单树
     * <p></p>
     * @param treeNodes
     * @param permissions
     * @return java.util.List<com.yunke.admin.common.model.MenuTreeNode>
     * @auth: tianlei
     * @date: 2026/1/15 21:41
     */
    public List<MenuTreeNode> buildMenuTree(List<MenuTreeNode> treeNodes,List<Permission> permissions){
        if(treeNodes != null && treeNodes.size() > 0){
            // 设置子节点
            treeNodes.stream().forEach(node ->{
                List<Permission> collect = permissions.stream().filter(permission -> {
                    return permission.getParentId().equals(node.getId());
                }).collect(Collectors.toList());
                if(collect != null && collect.size() > 0){
                    List<MenuTreeNode> children = this.convertTreeNode(collect);
                    node.setChildren(children);
                    permissions.remove(collect);
                    // 递归遍历子节点
                    this.buildMenuTree(children,permissions);
                }
            });
        }
        return treeNodes;
    }

    @Override
    public List<RouterVo> getUserMenus(LoginUserVO loginUserVO) {
        List<MenuTreeNode> userMenuTree = this.userMenuTree(loginUserVO);
        return buildUserMenus(userMenuTree);
    }


    public List<RouterVo> buildUserMenus(List<MenuTreeNode> userMenuTree){
        List<RouterVo> routers = new LinkedList<RouterVo>();
        for(MenuTreeNode node : userMenuTree){
            RouterVo router = new RouterVo();
            router.setHidden("0".equals(node.getVisible()));
            // 设置路由名称
            router.setName(getRouteName(node));

            // 设置路由地址
            router.setPath(getRouterPath(node));

            // 设置组件路径
            router.setComponent(getComponent(node));

            // 设置其他元素
            MetaVo meta = new MetaVo();
            meta.setTitle(node.getPermissionName());
            meta.setIcon(node.getIcon());
            if(PermissionTypeEnum.DIR.getCode().equals(node.getPermissionType())){
                meta.setPermissionId(node.getId());
                meta.setPermissionParentId(node.getParentId());
                meta.setPermissionName(node.getPermissionName());
            }else if(PermissionTypeEnum.MENU.getCode().equals(node.getPermissionType())){
                meta.setPageTitle(node.getPageTitle());
                meta.setPermissionId(node.getId());
                meta.setPermissionParentId(node.getParentId());
                meta.setPermissionName(node.getPermissionName());
                meta.setPermissionCode(node.getPermissionCode());
            }

            router.setMeta(meta);

            // 设置子路由
            List<MenuTreeNode> cNodes = node.getChildren();
            if(cNodes != null && cNodes.size() > 0 && node.getPermissionType().equals(PermissionTypeEnum.DIR.getCode())){
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildUserMenus(cNodes));
            }else if(node.getOpenType().equals(PermissionOpenTypeEnum.TATGET.getCode())){
                List<RouterVo> childrenList = new ArrayList<RouterVo>();
                RouterVo children = new RouterVo();
                children.setPath(node.getPath());
                children.setComponent(node.getComponent());
                children.setName(node.getPath());
                MetaVo cMeta = new MetaVo();
                cMeta.setTitle(node.getPermissionName());
                cMeta.setIcon(node.getIcon());
                cMeta.setPageTitle(node.getPageTitle());
                cMeta.setPermissionId(node.getId());
                cMeta.setPermissionParentId(node.getParentId());
                cMeta.setPermissionName(node.getPermissionName());
                cMeta.setPermissionCode(node.getPermissionCode());
                children.setMeta(cMeta);
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            routers.add(router);
        }
        return routers;
    }

    /**
     * @description: 获取路由地址
     * <p></p>
     * @param node 菜单信息
     * @return java.lang.String
     * @auth: tianlei
     * @date: 2026/1/15 21:41
     */
    public String getRouterPath(MenuTreeNode node){
        String routerPath = node.getPath();
        if(isTopMenu(node) && node.getPermissionType().equals(PermissionTypeEnum.DIR.getCode())){
            routerPath = "/" + node.getPath();// 一级菜单类型为目录
        }else if(isTopMenu(node) && isMenuFrame(node)){
            routerPath = "/";// 一级菜单类型为菜单
        }
        return routerPath;
    }

    /**
     * @description: 获取组件信息
     * <p></p>
     * @param
     * @param node 菜单信息
     * @return java.lang.String
     * @auth: tianlei
     * @date: 2026/1/15 21:41
     */
    public String getComponent(MenuTreeNode node){
        String component = PermissionConstant.LAYOUT;
        if(StrUtil.isNotEmpty(node.getComponent()) && (!isTopMenu(node) || !isMenuFrame(node))){
            component = node.getComponent();
        }
        return component;
    }

    /**
     * @description: 获取路由名称
     * <p></p>
     * @param node 菜单信息
     * @return java.lang.String
     * @auth: tianlei
     * @date: 2026/1/15 21:42
     */
    public String getRouteName(MenuTreeNode node){
        // 非外链并且是一级目录（类型为目录）
        String routerName = StrUtil.toCamelCase(node.getPath(),'-');
        routerName = routerName.substring(0, 1).toUpperCase() + routerName.substring(1);
        return routerName;
    }

    public String getRouteName2(MenuTreeNode node){
        // 非外链并且是一级目录（类型为目录）
        String routerName = node.getPath();
        routerName = routerName.substring(0, 1).toUpperCase() + routerName.substring(1);
        if(isMenuFrame(node)){
            routerName = StrUtil.EMPTY;
        }
        return routerName;
    }

    /**
     * @description: 是否为顶部菜单
     * <p></p>
     * @param node
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/15 21:42
     */
    public boolean isTopMenu(MenuTreeNode node){
        return node.getParentId().equals(PermissionConstant.PERMISSION_ROOT_PARENT_ID);
    }

    /**
     * @description: 是否为菜单内部跳转
     * <p></p>
     * @param node 菜单信息
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/15 21:42
     */
    public boolean isMenuFrame(MenuTreeNode node){
        return node.getPermissionType().equals(PermissionTypeEnum.MENU.getCode())
                && node.getOpenType().equals(PermissionOpenTypeEnum.IFRAME.getCode());
    }


    @Override
    public RoleGrantPermissionTreeVO getRoleGrantPermissonTreeData(QueryRoleGrantPermissionParam queryRoleGrantPermissionParam) {
        RoleGrantPermissionTreeVO roleGrantPermissionTreeVO = new RoleGrantPermissionTreeVO();
        // 设置树节点
        List<Tree<String>> treeNodes = this.treeSelect();
        roleGrantPermissionTreeVO.setTreeData(treeNodes);

        // 设置默认展开节点，默认展开节点为一级菜单
        Set<String> defaultExpandedKeys = CollUtil.newHashSet();
        // 查询一级菜单
        LambdaQueryWrapper<Permission> permissionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        permissionLambdaQueryWrapper.eq(Permission::getStatus, CommonStatusEnum.ENABLE.getCode());
        permissionLambdaQueryWrapper.eq(Permission::getParentId, PermissionConstant.PERMISSION_ROOT_PARENT_ID);
        List<Permission> permissions = this.list(permissionLambdaQueryWrapper);
        if(CollUtil.isNotEmpty(permissions)){
            defaultExpandedKeys = permissions.stream().map(Permission::getId).collect(Collectors.toSet());
        }
        roleGrantPermissionTreeVO.setDefaultExpandedKeys(defaultExpandedKeys);

        // 设置默认选中节点，默认选择中节点为当前角色已授权节点（排除半选中节点）
        Set<String> defaultCheckedKeys = CollUtil.newHashSet();
        LambdaQueryWrapper<RolePermission> rolePermissionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        rolePermissionLambdaQueryWrapper.eq(RolePermission::getRoleId, queryRoleGrantPermissionParam.getRoleId());
        List<RolePermission> rolePermissionList = rolePermissionService.list(rolePermissionLambdaQueryWrapper);
        if(CollUtil.isNotEmpty(rolePermissionList)){
            Set<String> checkedKeys = rolePermissionList.stream().map(RolePermission::getPermissionId).collect(Collectors.toSet());
            checkedKeys.forEach(permissionId -> {
                permissionLambdaQueryWrapper.clear();
                permissionLambdaQueryWrapper.eq(Permission::getParentId, permissionId);
                List<Permission> permissionList = this.list(permissionLambdaQueryWrapper);
                // 子节点为空或子节点全部选中时才设置为默认选中
                if(CollUtil.isEmpty(permissionList)){
                    defaultCheckedKeys.add(permissionId);
                }else{
                    Set<String> collect = permissionList.stream().map(Permission::getId).collect(Collectors.toSet());
                    if(checkedKeys.containsAll(collect)){
                        defaultCheckedKeys.add(permissionId);
                    }
                }
            });

        }

        // 移除没有子节点菜单与目录节点,防止el-tree会选中半选中节点问题
        Set<String> removeKeys = CollUtil.newHashSet();
        defaultCheckedKeys.stream().forEach(item -> {
            Permission permission = this.getById(item);
            if(permission.getPermissionType().equals(PermissionTypeEnum.DIR.getCode())
                    || permission.getPermissionType().equals(PermissionTypeEnum.MENU.getCode()) ){
                permissionLambdaQueryWrapper.clear();
                permissionLambdaQueryWrapper.eq(Permission::getParentId, item);
                List<Permission> children = this.list(permissionLambdaQueryWrapper);
                if(CollUtil.isNotEmpty(children)){
                    removeKeys.add(item);
                }
            }
        });
        defaultCheckedKeys.removeAll(removeKeys);
        roleGrantPermissionTreeVO.setDefaultCheckedKeys(defaultCheckedKeys);
        return roleGrantPermissionTreeVO;
    }

    @Override
    public List<Tree<String>> treeSelect() {
        List<Permission> permissions = this.lambdaQuery().eq(Permission::getStatus, CommonStatusEnum.ENABLE.getCode())
                .list();

        //配置
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        // 自定义属性名 都要默认值的
        treeNodeConfig.setWeightKey("sort");
        treeNodeConfig.setIdKey("id");
        // 最大递归深度
        treeNodeConfig.setDeep(5);

        //转换器
        List<Tree<String>> treeNodes = TreeUtil.build(permissions, "root", treeNodeConfig,
                (treeNode, tree) -> {
                    tree.setId(treeNode.getId());
                    tree.setParentId(treeNode.getParentId());
                    tree.setWeight(treeNode.getSort());
                    tree.setName(treeNode.getPermissionName());
                    tree.putExtra("type",treeNode.getPermissionType());
                });
        return treeNodes;
    }


    public void buildTree(List<MenuTreeNode> treeNodes) {
        if(treeNodes != null && treeNodes.size() > 0){
            treeNodes.stream().forEach(node -> {
                LambdaQueryWrapper<Permission> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(Permission::getParentId, node.getId());
                lambdaQueryWrapper.eq(Permission::getStatus, CommonStatusEnum.ENABLE.getCode());
                lambdaQueryWrapper.orderByAsc(Permission::getSort);
                // 设置子节点
                List<Permission> permissions = this.list(lambdaQueryWrapper);
                if(permissions != null && permissions.size() > 0){
                    List<MenuTreeNode> children = this.convertTreeNode(permissions);
                    node.setChildren(children);
                    // 递归遍历子节点
                    this.buildTree(children);
                }
            });
        }
    }

    public void buildElTree(List<ElementUITreeNode> treeNodes) {
        if(treeNodes != null && treeNodes.size() > 0){
            treeNodes.stream().forEach(node -> {
                LambdaQueryWrapper<Permission> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(Permission::getParentId, node.getId());
                lambdaQueryWrapper.eq(Permission::getStatus, CommonStatusEnum.ENABLE.getCode());
                lambdaQueryWrapper.orderByAsc(Permission::getSort);
                // 设置子节点
                List<Permission> permissions = this.list(lambdaQueryWrapper);
                if(permissions != null && permissions.size() > 0){
                    List<ElementUITreeNode> children = this.convertElTreeNode(permissions);
                    node.setChildren(children);
                    // 递归遍历子节点
                    this.buildElTree(children);
                }
            });
        }
    }

    /**
     * @description: 权限对象转换为树节点
     * <p></p>
     * @param permission
     * @return com.yunke.admin.common.model.MenuTreeNode
     * @auth: tianlei
     * @date: 2026/1/15 21:42
     */
    public MenuTreeNode convertTreeNode(Permission permission) {
        if(permission != null){
            MenuTreeNode node = new MenuTreeNode();
            BeanUtil.copyProperties(permission,node);
            return node;
        }
        return null;
    }

    public List<MenuTreeNode> convertTreeNode(List<Permission> permissions) {
        if(permissions != null && permissions.size() > 0){
            List<MenuTreeNode> treeNodes = CollUtil.newArrayList();
            permissions.stream().forEach(permission -> {
                MenuTreeNode node = this.convertTreeNode(permission);
                treeNodes.add(node);
            });
            return treeNodes;
        }
        return null;
    }

    public ElementUITreeNode convertElTreeNode(Permission permission){
        if(permission != null){
            ElementUITreeNode node = new ElementUITreeNode();
            node.setId(permission.getId());
            node.setLabel(permission.getPermissionName());
            node.setParentId(permission.getParentId());
            return node;
        }
        return null;
    }

    public List<ElementUITreeNode> convertElTreeNode(List<Permission> permissions) {
        if(permissions != null && permissions.size() > 0){
            List<ElementUITreeNode> treeNodes = CollUtil.newArrayList();
            permissions.stream().forEach(permission -> {
                ElementUITreeNode node = this.convertElTreeNode(permission);
                treeNodes.add(node);
            });
            return treeNodes;
        }
        return null;
    }

    private void checkParam(Permission permission, boolean isExcludeSelf) {
        LambdaQueryWrapper<Permission> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 非一级菜单需要校验父节点是否存在
        if(!permission.getParentId().equals(PermissionConstant.PERMISSION_ROOT_PARENT_ID)){
            lambdaQueryWrapper.eq(Permission::getId, permission.getParentId());
            int countParent = this.count(lambdaQueryWrapper);
            if(countParent == 0){
                throw new ServiceException(PermissionExceptionEnum.PARENT_NOT_EXIST);
            }
        }


        String permissionType = permission.getPermissionType();
        String path = permission.getPath();
        String permissionCode = permission.getPermissionCode();
        String openType = permission.getOpenType();
        String component = permission.getComponent();
        String visible = permission.getVisible();
        String icon = permission.getIcon();

        // 权限类型为目录
        if(PermissionTypeEnum.DIR.getCode().equals(permissionType)){
            // 图标不能为空
            if(StrUtil.isEmpty(icon)){
                throw new ServiceException(PermissionExceptionEnum.ICON_CAN_NOT_EMPTY);
            }
            // 打开方式不能为空
            if(StrUtil.isEmpty(openType)){
                throw new ServiceException(PermissionExceptionEnum.OPEN_TYPE_CAN_NOT_EMPTY);
            }
            // 是否可见不能为空
            if(StrUtil.isEmpty(visible)){
                throw new ServiceException(PermissionExceptionEnum.VISIBLE_CAN_NOT_EMPTY);
            }
            // 路由地址不能为空
            if(StrUtil.isEmpty(path)){
                throw new ServiceException(PermissionExceptionEnum.PERMISSION_PATH_EMPTY);
            }
            // 不是顶级目录的话，组件路径不能为空
            if(!permission.getParentId().equals(PermissionConstant.PERMISSION_ROOT_PARENT_ID)){
                if(StrUtil.isEmpty(component)){
                    throw new ServiceException(PermissionExceptionEnum.PERMISSION_COMPONENT_EMPTY);
                }
            }
        }

        // 权限类型为菜单
        if(PermissionTypeEnum.MENU.getCode().equals(permissionType)){
            // 图标不能为空
            if(StrUtil.isEmpty(icon)){
                throw new ServiceException(PermissionExceptionEnum.ICON_CAN_NOT_EMPTY);
            }
            // 打开方式不能为空
            if(StrUtil.isEmpty(openType)){
                throw new ServiceException(PermissionExceptionEnum.OPEN_TYPE_CAN_NOT_EMPTY);
            }
            // 路由地址不能为空
            if(StrUtil.isEmpty(path)){
                throw new ServiceException(PermissionExceptionEnum.PERMISSION_PATH_EMPTY);
            }
            // 权限编码不能为空
            if(StrUtil.isEmpty(permissionCode)){
                throw new ServiceException(PermissionExceptionEnum.PERMISSION_CODE_EMPTY);
            }
            // 是否可见不能为空
            if(StrUtil.isEmpty(visible)){
                throw new ServiceException(PermissionExceptionEnum.VISIBLE_CAN_NOT_EMPTY);
            }
            // 内链 组件路径不能为空
            if(openType.equals(PermissionOpenTypeEnum.IFRAME.getCode())){
                if(StrUtil.isEmpty(component)){
                    throw new ServiceException(PermissionExceptionEnum.PERMISSION_COMPONENT_EMPTY);
                }
            }
        }

        // 权限类型为按钮
        if(PermissionTypeEnum.BTN.getCode().equals(permissionType)){
            // 权限编码不能为空
            if(StrUtil.isEmpty(permissionCode)){
                throw new ServiceException(PermissionExceptionEnum.PERMISSION_CODE_EMPTY);
            }
        }

        // 如果是编辑，父节点不能与当前节点一致
        if(isExcludeSelf){
            if(permission.getId().equals(permission.getParentId())){
                throw new ServiceException(PermissionExceptionEnum.PARENT_CAN_NOT_EQ_ID);
            }
        }

        // 校验权限名称是否重复
        lambdaQueryWrapper.clear();
        lambdaQueryWrapper.eq(Permission::getPermissionName,permission.getPermissionName());
        lambdaQueryWrapper.eq(Permission::getParentId,permission.getParentId());
        if(isExcludeSelf){
            lambdaQueryWrapper.ne(Permission::getId, permission.getId());
        }
        int countName = baseMapper.selectCount(lambdaQueryWrapper);
        if(countName > 0){
            throw new ServiceException(PermissionExceptionEnum.PERMISSION_NAME_REPEAT);
        }

        // 校验权限编码是否重复
        lambdaQueryWrapper.clear();
        if(PermissionTypeEnum.MENU.getCode().equals(permissionType) || PermissionTypeEnum.BTN.getCode().equals(permissionType)){
            lambdaQueryWrapper.eq(Permission::getPermissionCode,permission.getPermissionCode());
            if(isExcludeSelf){
                lambdaQueryWrapper.ne(Permission::getId, permission.getId());
            }
            int countCode = baseMapper.selectCount(lambdaQueryWrapper);
            if(countCode > 0){
                throw new ServiceException(PermissionExceptionEnum.PERMISSION_CODE_REPEAT);
            }
        }

        //设置是否外链
        if(PermissionTypeEnum.MENU.getCode().equals(permission.getPermissionType()) && PermissionOpenTypeEnum.TATGET.getCode().equals(permission.getOpenType())){
            permission.setIsExternal(CommonIfEnum.YES.getCode());
        }else{
            permission.setIsExternal(null);
        }
    }

    public void fillParentIds(Permission permission) {
        String parentId = permission.getParentId();
        if(PermissionConstant.PERMISSION_ROOT_PARENT_ID.equals(parentId)){
            permission.setParentIds(parentId);
        }else{
            Permission parent = getById(parentId);
            if(parent == null){
                throw new ServiceException(PermissionExceptionEnum.PARENT_NOT_EXIST);
            }
            // 父节点状态异常，不能添加子节点
            if(!parent.getStatus().equals(CommonStatusEnum.ENABLE.getCode())){
                throw new ServiceException(PermissionExceptionEnum.PARENT_STATUS_EXCEPTION);
            }
            permission.setParentIds(parent.getParentIds() + SymbolConstant.HYPHEN + parent.getId());
        }
    }

    public void updatePermisssionChildren(Permission permission) {
        // 查询子节点集合，子节点的parentId 等于当前被修改节点的id
        LambdaQueryWrapper<Permission> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Permission::getParentId, permission.getId());
        List<Permission> children = baseMapper.selectList(lambdaQueryWrapper);
        if(children != null && children.size() > 0){
            for(Permission child : children){
                // 更新实体，只更新parentIds
                Permission updateEntity = new Permission();
                updateEntity.setId(child.getId());
                String newParentIds = permission.getParentIds() + SymbolConstant.HYPHEN + permission.getId();
                updateEntity.setParentIds(newParentIds);
                baseMapper.updateById(updateEntity);
                // 递归更新子部门
                updatePermisssionChildren(child);
            }
        }
    }

    /**
     * @description: 根据节点id获取所有子节点id集合
     * <p></p>
     * @param id
     * @return java.util.List<java.lang.String>
     * @auth: tianlei
     * @date: 2026/1/15 21:43
     */
    public List<String> getChlldIdListById(String id){
        List<String> childIdList = CollUtil.newArrayList();
        LambdaQueryWrapper<Permission> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(Permission::getParentIds,id);
        List<Permission> permissions = baseMapper.selectList(lambdaQueryWrapper);
        List<String> ids = permissions.stream().map(Permission::getId).collect(Collectors.toList());
        childIdList.addAll(ids);
        return childIdList;
    }

    /**
     * @description: 根据节点id判断此节点及子节点是否有被角色使用
     * <p></p>
     * @param id
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/15 21:43
     */
    public boolean hasUsedByRole(String id){
        List<String> permisssionIdList = CollUtil.newArrayList();
        permisssionIdList.add(id);
        List<String> chlldIdList = this.getChlldIdListById(id);
        if(CollUtil.isNotEmpty(chlldIdList)){
            permisssionIdList.addAll(chlldIdList);
        }
        LambdaQueryWrapper<RolePermission> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(RolePermission::getPermissionId, permisssionIdList);
        int roleCount = rolePermissionService.count(lambdaQueryWrapper);
        return roleCount > 0;
    }

}
