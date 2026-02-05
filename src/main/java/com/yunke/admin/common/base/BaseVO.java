package com.yunke.admin.common.base;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @className BaseVO
 * @description: 视图返回参数基类
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Data
public class BaseVO implements Serializable {

    private static final long serialVersionUID = -5638832217257855719L;

    /**
     * 扩展数据
     */
    private Map<String,Object> extend = new HashMap<>();

}
