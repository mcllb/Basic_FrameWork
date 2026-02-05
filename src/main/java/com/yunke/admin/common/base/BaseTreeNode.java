package com.yunke.admin.common.base;

import java.util.List;

public interface BaseTreeNode {

    String getId();

    String getParentId();

    void setChildren(List children);

}
