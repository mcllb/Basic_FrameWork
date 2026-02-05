package com.yunke.admin.common.base;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @className BaseParam
 * @description: 参数封装基类
 * @version 1.0
 * @author tianlei
 * @date 2026/1/14
 */
@Data
public class BaseParam implements Serializable {


    private static final long serialVersionUID = 5544007252865235154L;

    /**
     * 扩展数据
     */
    private HashMap<String, Object> params = new HashMap<>();


}
