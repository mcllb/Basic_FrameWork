package com.yunke.admin.modular.system.shortcut.model.param;

import com.yunke.admin.common.base.BaseQueryParam;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @className ShortcutQueryParam
 * @description: 快捷方式列表_查询请求参数
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ShortcutQueryParam extends BaseQueryParam {

    /**
     * 名称
     */
    private String name;
    
}
