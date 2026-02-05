package com.yunke.admin.modular.system.dept.service;

import cn.hutool.core.lang.tree.Tree;
import com.yunke.admin.common.model.ElementUITreeNode;
import com.yunke.admin.common.model.SingleDeleteParam;
import com.yunke.admin.framework.mybatisplus.base.GeneralService;
import com.yunke.admin.modular.system.dept.model.entity.Dept;
import com.yunke.admin.modular.system.dept.model.param.DeptAddParam;
import com.yunke.admin.modular.system.dept.model.param.DeptEditParam;
import com.yunke.admin.modular.system.dept.model.param.DeptUpdateStatusParam;

import java.util.List;

/**
 * @className DeptService
 * @description: 系统部门表_服务接口
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
public interface DeptService extends GeneralService<Dept> {

    List<Tree<String>> tree();

    List<ElementUITreeNode> treeSelect();

    boolean add(DeptAddParam deptAddParam);

    boolean edit(DeptEditParam deptEditParam);

    boolean delete(SingleDeleteParam singleDeleteParam);

    boolean updateDeptStatus(DeptUpdateStatusParam deptUpdateStatusParam);

}
