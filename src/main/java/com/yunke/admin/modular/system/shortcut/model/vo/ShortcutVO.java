package com.yunke.admin.modular.system.shortcut.model.vo;

import com.yunke.admin.common.base.BaseVO;
import com.yunke.admin.framework.core.annotion.EnumDictField;
import com.yunke.admin.modular.system.shortcut.enums.ShortcutTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


/**
 * @className ShortcutVO
 * @description: 快捷方式_详情返回数据
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ShortcutVO extends BaseVO {

    /**
     * 主键
     */
    private String id;
    /**
     * 类型
     */
    @EnumDictField(ShortcutTypeEnum.class)
    private String type;
    private String typeText;
    /**
     * 名称
     */
    private String name;
    /**
     * 路径
     */
    private String path;
    /**
     * 关联菜单id
     */
    private String permissionId;
    /**
     * 菜单名称
     */
    private String permissionName;
    /**
     * 菜单路径
     */
    private String permissionPath;
    /**
     * 图标
     */
    private String icon;
    /**
     * 菜单图标
     */
    private String permissionIcon;
    /**
     * 背景颜色
     */
    private String bgColor;
    /**
     * 显示排序
     */
    private Integer sort;
    /**
     * 状态（字典： 1启用 0停用）
     */
    private String enable;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private Date createTime;

}