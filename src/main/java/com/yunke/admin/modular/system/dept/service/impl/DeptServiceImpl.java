package com.yunke.admin.modular.system.dept.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yunke.admin.common.constant.SymbolConstant;
import com.yunke.admin.common.enums.CommonStatusEnum;
import com.yunke.admin.common.model.ElementUITreeNode;
import com.yunke.admin.common.model.SingleDeleteParam;
import com.yunke.admin.common.util.BeanUtil;
import com.yunke.admin.framework.core.exception.ServiceException;
import com.yunke.admin.framework.mybatisplus.base.GeneralServiceImpl;
import com.yunke.admin.modular.system.dept.constant.DeptConstant;
import com.yunke.admin.modular.system.dept.enums.DeptExceptionEnum;
import com.yunke.admin.modular.system.dept.mapper.DeptMapper;
import com.yunke.admin.modular.system.dept.model.entity.Dept;
import com.yunke.admin.modular.system.dept.model.param.DeptAddParam;
import com.yunke.admin.modular.system.dept.model.param.DeptEditParam;
import com.yunke.admin.modular.system.dept.model.param.DeptUpdateStatusParam;
import com.yunke.admin.modular.system.dept.service.DeptService;
import com.yunke.admin.modular.system.user.model.entity.User;
import com.yunke.admin.modular.system.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @className DeptServiceImpl
 * @description: 系统部门表_服务实现类
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Service
public class DeptServiceImpl extends GeneralServiceImpl<DeptMapper, Dept> implements DeptService {

    @Autowired
    @Lazy
    private UserService userService;

    @Override
    public List<Tree<String>> tree() {

        List<Dept> deptList = this.lambdaQuery().eq(Dept::getStatus, CommonStatusEnum.ENABLE.getCode())
                .list();

        //配置
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        // 自定义属性名 都要默认值的
        treeNodeConfig.setWeightKey("sort");
        treeNodeConfig.setIdKey("id");
        // 最大递归深度
        treeNodeConfig.setDeep(5);

        //转换器
        List<Tree<String>> treeNodes = TreeUtil.build(deptList, "root", treeNodeConfig,
                (treeNode, tree) -> {
                    tree.setId(treeNode.getId());
                    tree.setParentId(treeNode.getParentId());
                    tree.setWeight(treeNode.getSort());
                    tree.setName(treeNode.getDeptName());
                });
        return treeNodes;
    }

    @Override
    public List<ElementUITreeNode> treeSelect() {
        List<ElementUITreeNode> treeNodes = CollUtil.newArrayList();
        LambdaQueryWrapper<Dept> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 查询根部门
        lambdaQueryWrapper.eq(Dept::getId, DeptConstant.DEPT_ROOT_ID);
        lambdaQueryWrapper.orderByAsc(Dept::getSort);
        List<Dept> deptList = this.list(lambdaQueryWrapper);
        if (deptList != null && deptList.size() > 0) {
            treeNodes = convertTreeNode(deptList);
            this.buildTree(treeNodes);
        }
        return treeNodes;
    }

    public void buildTree(List<ElementUITreeNode> treeNodes) {
        if (treeNodes != null && treeNodes.size() > 0) {
            treeNodes.stream().forEach(node -> {
                LambdaQueryWrapper<Dept> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(Dept::getParentId, node.getId());
                lambdaQueryWrapper.eq(Dept::getStatus, CommonStatusEnum.ENABLE.getCode());
                lambdaQueryWrapper.orderByAsc(Dept::getSort);
                List<Dept> depts = this.list(lambdaQueryWrapper);
                if (depts != null && depts.size() > 0) {
                    List<ElementUITreeNode> children = this.convertTreeNode(depts);
                    node.setChildren(children);
                    // 递归遍历子节点
                    this.buildTree(children);
                }
            });
        }
    }

    public ElementUITreeNode convertTreeNode(Dept dept) {
        if (dept != null) {
            ElementUITreeNode node = new ElementUITreeNode();
            node.setId(dept.getId());
            node.setLabel(dept.getDeptName());
            node.setParentId(dept.getParentId());
            return node;
        }
        return null;
    }

    public List<ElementUITreeNode> convertTreeNode(List<Dept> depts) {
        if (depts != null && depts.size() > 0) {
            List<ElementUITreeNode> treeNodes = CollUtil.newArrayList();
            depts.stream().forEach(dept -> {
                ElementUITreeNode node = this.convertTreeNode(dept);
                treeNodes.add(node);
            });
            return treeNodes;
        }
        return null;
    }

    @Transactional
    @Override
    public boolean add(DeptAddParam deptAddParam) {
        Dept dept = new Dept();
        BeanUtil.copyProperties(deptAddParam, dept);
        // 校验参数
        checkParam(dept, false);
        // 设置父级ids
        fillParentIds(dept);
        // 设置状态为启用
        dept.setStatus(CommonStatusEnum.ENABLE.getCode());
        return this.save(dept);
    }

    @Transactional
    @Override
    public boolean edit(DeptEditParam deptEditParam) {
        Dept dept = new Dept();
        BeanUtil.copyProperties(deptEditParam, dept);
        // 校验参数
        checkParam(dept, true);
        if (DeptConstant.DEPT_ROOT_ID.equals(dept.getId())) {
            // 如果是根部门不能修改父级id
            dept.setParentId(null);
            dept.setParentIds(null);
        } else {
            // 设置父级ids
            fillParentIds(dept);
            String newParentId = dept.getParentId();
            Dept oldDept = this.getById(dept.getId());
            String oldParentId = oldDept.getParentId();
            // 如果修改了父节点，同时更新原子节点的parentIds
            if (!newParentId.equals(oldParentId)) {
                updateDeptChildren(dept);
            }
        }

        // 不修改状态
        dept.setStatus(null);
        return this.updateById(dept);
    }

    @Transactional
    @Override
    public boolean delete(SingleDeleteParam singleDeleteParam) {
        Dept dept = this.getById(singleDeleteParam.getId());
        // 根部门不能删除
        if (DeptConstant.DEPT_ROOT_ID.equals(dept.getId())) {
            throw new ServiceException(DeptExceptionEnum.CANNOT_DELETE_ROOT);
        }

        // 部门下有用户，则不能删除
        if (hasUser(dept.getId())) {
            throw new ServiceException(DeptExceptionEnum.CANNOT_DELETE);
        }

        // 要删除的部门id集合，包含子部门id
        List<String> deleteIds = CollUtil.newArrayList();
        deleteIds.add(dept.getId());
        List<String> childrenIds = getChildren(dept.getId()).stream().map(Dept::getId).collect(Collectors.toList());
        if (childrenIds != null && childrenIds.size() > 0) {
            deleteIds.addAll(childrenIds);
        }
        return baseMapper.deleteBatchIds(deleteIds) > 0;
    }

    @Transactional
    @Override
    public boolean updateDeptStatus(DeptUpdateStatusParam deptUpdateStatusParam) {
        // 不能修改根部门
        if (DeptConstant.DEPT_ROOT_ID.equals(deptUpdateStatusParam.getId())) {
            throw new ServiceException(DeptExceptionEnum.CANNOT_EDIT_ROOT);
        }
        Dept dept = new Dept();
        BeanUtil.copyProperties(deptUpdateStatusParam, dept);
        return this.updateById(dept);
    }


    public void checkParam(Dept dept, boolean isExcludeSelf) {
        String id = dept.getId();
        String parentId = dept.getParentId();
        String deptName = dept.getDeptName();
        boolean isRootDpet = isExcludeSelf == true && id.equals(DeptConstant.DEPT_ROOT_ID);
        // 不是根部门都需要检验参数
        if (!isRootDpet) {
            Dept parent = this.getById(parentId);
            // 父节点不存在
            if (parent == null) {
                throw new ServiceException(DeptExceptionEnum.DEPT_NOT_EXIST);
            }
            // 父节点状态异常，不能添加添加子节点
            if (!parent.getStatus().equals(CommonStatusEnum.ENABLE.getCode())) {
                throw new ServiceException(DeptExceptionEnum.PARENT_STATUS_EXCEPTION);
            }

            // 校验同级部门名称不能重复
            LambdaQueryWrapper<Dept> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Dept::getDeptName, deptName).eq(Dept::getParentId, parentId);
            if (isExcludeSelf) {
                lambdaQueryWrapper.ne(Dept::getId, id);
            }
            int countDeptName = this.count(lambdaQueryWrapper);
            if (countDeptName > 0) {
                throw new ServiceException(DeptExceptionEnum.DEPT_CODE_REPEAT);
            }

            // 校验部门编号不能重重复
            lambdaQueryWrapper.clear();
            if (StrUtil.isNotEmpty(dept.getDeptCode())) {
                lambdaQueryWrapper.eq(Dept::getDeptCode, dept.getDeptCode());
                if (isExcludeSelf) {
                    lambdaQueryWrapper.ne(Dept::getId, id);
                }
                int countDeptCode = this.count(lambdaQueryWrapper);
                if (countDeptCode > 0) {
                    throw new ServiceException(DeptExceptionEnum.SAME_DEPT_NAME_REPEAT);
                }
            }

        }

    }

    public void fillParentIds(Dept dept) {
        String parentId = dept.getParentId();
        // 设置父级部门ids,等于父级parentIds + 父级id +连接符
        Dept parent = this.getById(parentId);
        String parentIds = parent.getParentIds() + parent.getId() + SymbolConstant.HYPHEN;
        dept.setParentIds(parentIds);

    }

    /**
     * @description: 递归修改子部门parentIds
     * <p></p>
     * @param
     * @param dept
     * @return void
     * @auth: tianlei
     * @date: 2026/1/15 20:35
     */
    public void updateDeptChildren(Dept dept) {
        // 查询子部门集合，子部门的parentId 等于当前被修改部门的id
        LambdaQueryWrapper<Dept> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Dept::getParentId, dept.getId());
        List<Dept> children = baseMapper.selectList(lambdaQueryWrapper);
        if (children != null && children.size() > 0) {
            for (Dept child : children) {
                // 更新实体，只更新parentIds
                Dept udapteEntity = new Dept();
                udapteEntity.setId(child.getId());
                String newParentIds = dept.getParentIds() + dept.getId() + SymbolConstant.HYPHEN;
                udapteEntity.setParentIds(newParentIds);
                baseMapper.updateById(udapteEntity);
                // 递归更新子部门
                updateDeptChildren(child);
            }
        }

    }

    /**
     * @description: 根据部门id获取子部门集合
     * <p></p>
     * @param
     * @param id
     * @return java.util.List<com.yunke.admin.modular.system.dept.model.entity.Dept>
     * @auth: tianlei
     * @date: 2026/1/15 20:34
     */
    public List<Dept> getChildren(String id) {
        LambdaQueryWrapper<Dept> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(Dept::getParentIds, id);
        return baseMapper.selectList(lambdaQueryWrapper);
    }

    /**
     * @description: 根据部门id查询部门及子部门是否有用户关联
     * <p></p>
     * @param
     * @param id
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/15 20:34
     */
    public boolean hasUser(String id) {
        // 查询本级部门所关联的用户数量
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getDeptId, id);
        int userCount = userService.count(lambdaQueryWrapper);

        // 查询子部门所关联的用户数量
        int childrenUserCount = 0;
        List<Dept> children = getChildren(id);
        if (children != null && children.size() > 0) {
            List<String> ids = children.stream().map(Dept::getId).collect(Collectors.toList());
            if (ids != null && ids.size() > 0) {
                lambdaQueryWrapper.clear();
                lambdaQueryWrapper.in(User::getDeptId, ids);
                childrenUserCount = userService.count(lambdaQueryWrapper);
            }
        }
        return userCount > 0 || childrenUserCount > 0;
    }

}
