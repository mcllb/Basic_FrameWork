package com.yunke.admin.modular.system.shortcut.model.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @className ShortcutUpdateSortParam
 * @description: 修改排序请求参数
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
public class ShortcutUpdateSortParam {

    /**
     * 主键
     */
    @NotEmpty(message = "主键不能为空，请检查参数id")
    private String id;
    /**
     * 显示排序
     */
    @NotNull(message = "排序号不能为空，请检查参数sort")
    private Integer sort;
}
