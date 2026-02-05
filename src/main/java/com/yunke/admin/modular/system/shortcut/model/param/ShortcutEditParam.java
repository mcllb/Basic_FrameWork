package com.yunke.admin.modular.system.shortcut.model.param;

import com.yunke.admin.common.base.BaseEditParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * @className ShortcutEditParam
 * @description: 快捷方式_编辑请求参数
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ShortcutEditParam extends BaseEditParam {

    /**
     * 主键
     */
    @NotEmpty(message = "主键不能为空，请检查参数id")
    private String id;
    /**
     * 类型
     */
    @NotEmpty(message = "类型不能为空，请检查参数type")
    private String type;
    /**
     * 名称
     */
    private String name;
    /**
     * 路径
     */
    private String path;
    /**
     * 图标
     */
    private String icon;
    /**
     * 关联菜单id
     */
    private String permissionId;
    /**
     * 背景颜色
     */
    private String bgColor;
    /**
     * 显示排序
     */
    private Integer sort;
    /**
     * 备注
     */
    private String remark;
}