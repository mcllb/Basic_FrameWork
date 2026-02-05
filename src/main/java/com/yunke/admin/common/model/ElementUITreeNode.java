package com.yunke.admin.common.model;

import cn.hutool.core.map.MapUtil;
import com.yunke.admin.common.base.BaseTreeNode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Data
public class ElementUITreeNode implements BaseTreeNode {

    /**
     * id
     */
    private String id;

    /**
     * 名称
     */
    private String label;

    /**
     * 父节点
     */
    private String parentId;

    /**
     * 是否禁用
     */
    private boolean disabled = false;
    /**
     * 是否为叶子节点
     */
    private boolean isLeaf = false;
    /**
     * 扩展数据
     */
    private Map<String, Object> extra = MapUtil.newHashMap();

    /**
     * 子节点
     */
    private List<ElementUITreeNode> children;

    public ElementUITreeNode(String id,String label){
        this.id = id;
        this.label = label;
    }

    public ElementUITreeNode(String id,String label,String parentId){
        this.id = id;
        this.label = label;
        this.parentId = parentId;
    }

    public ElementUITreeNode(String id,String label,String parentId,List<ElementUITreeNode> children){
        this.id = id;
        this.label = label;
        this.parentId = parentId;
        this.children = children;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getParentId() {
        return this.parentId;
    }

    @Override
    public void setChildren(List children) {
        this.children = children;
    }

    public void set(String key,Object value){
        this.extra.put(key, value);
    }
}
