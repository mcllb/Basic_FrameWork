package com.yunke.admin.modular.system.permission.model.vo;

import cn.hutool.core.lang.tree.Tree;
import com.yunke.admin.common.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoleGrantPermissionTreeVO extends BaseVO {

    /**
     * 树形结构
     */
    private List<Tree<String>> treeData;

    /**
     * 默认展开节点
     */
    private Set<String> defaultExpandedKeys;

    /**
     * 默认选中节点
     */
    private Set<String> defaultCheckedKeys;

}
