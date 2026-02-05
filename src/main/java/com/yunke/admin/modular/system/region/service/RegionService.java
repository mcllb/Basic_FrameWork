package com.yunke.admin.modular.system.region.service;

import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.framework.mybatisplus.base.GeneralService;
import com.yunke.admin.modular.system.region.model.entity.Region;
import com.yunke.admin.modular.system.region.model.param.RegionAddParam;
import com.yunke.admin.modular.system.region.model.param.RegionEditParam;
import com.yunke.admin.modular.system.region.model.param.RegionEnableParam;
import com.yunke.admin.modular.system.region.model.param.RegionPageQueryParam;
import com.yunke.admin.modular.system.region.model.vo.RegionVO;

import java.util.List;

/**
 * @className RegionService
 * @description: 行政区划代码表_Service接口
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
public interface RegionService extends GeneralService<Region> {
	
   /**
    * @description: 行政区划代码表_分页查询
    * <p></p>
    * @param pageQueryParam
    * @return com.yunke.admin.common.model.PageWrapper<com.yunke.admin.modular.system.region.model.vo.RegionVO>
    * @auth: tianlei
    * @date: 2026/1/15 21:45
    */
    PageWrapper<RegionVO> pageVO(RegionPageQueryParam pageQueryParam);

    /**
     * @description: 树形表格
     * <p></p>
     * @param pageQueryParam
     * @return java.util.List<com.yunke.admin.modular.system.region.model.entity.Region>
     * @auth: tianlei
     * @date: 2026/1/15 21:45
     */
    List<Region> selectTreeTable(RegionPageQueryParam pageQueryParam);

    /**
     * @description: 根据父节点id查询子节点（不包含孙节点）
     * <p></p>
     * @param parentId
     * @return java.util.List<com.yunke.admin.modular.system.region.model.vo.RegionVO>
     * @auth: tianlei
     * @date: 2026/1/15 21:45
     */
    List<RegionVO> selectListByParentId(String parentId);

   /**
    * @description: 行政区划代码表_详情
    * <p></p>
    * @param id
    * @return com.yunke.admin.modular.system.region.model.vo.RegionVO
    * @auth: tianlei
    * @date: 2026/1/15 21:46
    */
    RegionVO getVO(String id);
    
    /**
     * @description: 行政区划代码表_新增
     * <p></p>
     * @param addParam
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/15 21:46
     */
    boolean add(RegionAddParam addParam);
	
    /**
     * @description: 行政区划代码表_编辑
     * <p></p>
     * @param editParam
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/15 21:46
     */
    boolean edit(RegionEditParam editParam);
	
   /**
    * @description: 行政区划代码表_根据id删除
    * <p></p>
    * @param id
    * @return boolean
    * @auth: tianlei
    * @date: 2026/1/15 21:46
    */
    boolean delete(String id);

    /**
     * @description: 修改启用状态
     * <p></p>
     * @param enableParam
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/15 21:46
     */
    boolean updateEnable(RegionEnableParam enableParam);

}
