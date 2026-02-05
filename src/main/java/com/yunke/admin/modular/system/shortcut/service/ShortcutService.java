package com.yunke.admin.modular.system.shortcut.service;

import com.yunke.admin.framework.mybatisplus.base.GeneralService;
import com.yunke.admin.modular.system.permission.model.entity.Permission;
import com.yunke.admin.modular.system.shortcut.model.entity.Shortcut;
import com.yunke.admin.modular.system.shortcut.model.param.*;
import com.yunke.admin.modular.system.shortcut.model.vo.ShortcutVO;

import java.util.List;

/**
 * @className ShortcutService
 * @description: 快捷方式_Service接口
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
public interface ShortcutService extends GeneralService<Shortcut> {
	
   /**
    * @description: 快捷方式列表
    * <p></p>
    * @param queryParam
    * @return java.util.List<com.yunke.admin.modular.system.shortcut.model.vo.ShortcutVO>
    * @auth: tianlei
    * @date: 2026/1/15 21:57
    */
    List<ShortcutVO> list(ShortcutQueryParam queryParam);
	
   /**
    * @description: 快捷方式_详情
    * <p></p>
    * @param id
    * @return com.yunke.admin.modular.system.shortcut.model.vo.ShortcutVO
    * @auth: tianlei
    * @date: 2026/1/15 21:57
    */
    ShortcutVO getVO(String id);
    
    /**
     * @description: 快捷方式_新增
     * <p></p>
     * @param addParam
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/15 21:57
     */
    boolean add(ShortcutAddParam addParam);
	
    /**
     * @description: 快捷方式_编辑
     * <p></p>
     * @param editParam
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/15 21:58
     */
    boolean edit(ShortcutEditParam editParam);
	
   /**
    * @description: 快捷方式_根据id删除
    * <p></p>
    * @param id
    * @return boolean
    * @auth: tianlei
    * @date: 2026/1/15 21:58
    */
    boolean delete(String id);

    /**
     * @description: 获取用户菜单
     * <p></p>
     * @param excludeShoutCutRef 是否排除已经关联的
     * @return java.util.List<com.yunke.admin.modular.system.permission.model.entity.Permission>
     * @auth: tianlei
     * @date: 2026/1/15 21:58
     */
    List<Permission> getUserPermissions(boolean excludeShoutCutRef);

    /**
     * @description: 修改状态
     * <p></p>
     * @param updateStatusParam
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/15 21:58
     */
    boolean updateStatus(ShortcutUpdateStatusParam updateStatusParam);

    /**
     * @description: 修改排序
     * <p></p>
     * @param updateSortParam
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/15 21:58
     */
    boolean updateSort(ShortcutUpdateSortParam updateSortParam);

}
