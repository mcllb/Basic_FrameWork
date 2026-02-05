package com.yunke.admin.modular.system.file.service;

import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.framework.mybatisplus.base.GeneralService;
import com.yunke.admin.modular.system.file.model.entity.FileSmallType;
import com.yunke.admin.modular.system.file.model.param.FileSmallTypeAddParam;
import com.yunke.admin.modular.system.file.model.param.FileSmallTypeEditParam;
import com.yunke.admin.modular.system.file.model.param.FileSmallTypePageQueryParam;
import com.yunke.admin.modular.system.file.model.param.FileSmallTypeUpdateSortParam;
import com.yunke.admin.modular.system.file.model.vo.FileSmallTypeVO;

import java.util.List;

/**
 * @className FileSmallTypeService
 * @description: 系统附件子类型表_Service接口
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
public interface FileSmallTypeService extends GeneralService<FileSmallType> {
	
   /**
    * @description: 系统附件子类型表_分页查询
    * <p></p>
    * @param pageQueryParam
    * @return com.yunke.admin.common.model.PageWrapper<com.yunke.admin.modular.system.file.model.vo.FileSmallTypeVO>
    * @auth: tianlei
    * @date: 2026/1/15 20:54
    */
    PageWrapper<FileSmallTypeVO> selectPageVO(FileSmallTypePageQueryParam pageQueryParam);

    /**
     * @description: 根据父类型id查询子类型
     * <p></p>
     * @param bigTypeId
     * @return java.util.List<com.yunke.admin.modular.system.file.model.vo.FileSmallTypeVO>
     * @auth: tianlei
     * @date: 2026/1/15 20:54
     */
    List<FileSmallTypeVO> listByBigtypeId(String bigTypeId);
	
   /**
    * @description: 系统附件子类型表_详情
    * <p></p>
    * @param id
    * @return com.yunke.admin.modular.system.file.model.vo.FileSmallTypeVO
    * @auth: tianlei
    * @date: 2026/1/15 20:54
    */
    FileSmallTypeVO selectVO(String id);
    
    /**
     * @description: 系统附件子类型表_新增
     * <p></p>
     * @param addParam
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/15 20:55
     */
    boolean add(FileSmallTypeAddParam addParam);
	
    /**
     * @description: 系统附件子类型表_编辑
     * <p></p>
     * @param editParam
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/15 20:55
     */
    boolean edit(FileSmallTypeEditParam editParam);
	
   /**
    * @description: 系统附件子类型表_根据id删除
    * <p></p>
    * @param id
    * @return boolean
    * @auth: tianlei
    * @date: 2026/1/15 20:55
    */
    boolean delete(String id);

    /**
     * 修改排序
     * @param updateSortParam
     * @return
     */
    /**
     * @description: 修改排序
     * <p></p>
     * @param updateSortParam
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/15 20:56
     */
    boolean changeSort(FileSmallTypeUpdateSortParam updateSortParam);

}
