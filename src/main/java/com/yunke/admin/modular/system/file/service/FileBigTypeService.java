package com.yunke.admin.modular.system.file.service;

import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.framework.mybatisplus.base.GeneralService;
import com.yunke.admin.modular.system.file.model.entity.FileBigType;
import com.yunke.admin.modular.system.file.model.param.FileBigTypeAddParam;
import com.yunke.admin.modular.system.file.model.param.FileBigTypeEditParam;
import com.yunke.admin.modular.system.file.model.param.FileBigTypePageQueryParam;
import com.yunke.admin.modular.system.file.model.vo.FileBigTypeVO;

/**
 * @className FileBigTypeService
 * @description: 系统附件类型表_Service接口
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
public interface FileBigTypeService extends GeneralService<FileBigType> {
	
   /**
    * @description: 系统附件类型表_分页查询
    * <p></p>
    * @param pageQueryParam
    * @return com.yunke.admin.common.model.PageWrapper<com.yunke.admin.modular.system.file.model.vo.FileBigTypeVO>
    * @auth: tianlei
    * @date: 2026/1/15 20:56
    */
    PageWrapper<FileBigTypeVO> selectPageVO(FileBigTypePageQueryParam pageQueryParam);
	
   /**
    * @description: 系统附件类型表_详情
    * <p></p>
    * @param id
    * @return com.yunke.admin.modular.system.file.model.vo.FileBigTypeVO
    * @auth: tianlei
    * @date: 2026/1/15 20:56
    */
    FileBigTypeVO selectVO(String id);
    
    /**
     * @description: 系统附件类型表_新增
     * <p></p>
     * @param addParam
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/15 20:56
     */
    boolean add(FileBigTypeAddParam addParam);
	
    /**
     * @description: 系统附件类型表_编辑
     * <p></p>
     * @param editParam
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/15 20:57
     */
    boolean edit(FileBigTypeEditParam editParam);
	
   /**
    * @description: 系统附件类型表_根据id删除
    * <p></p>
    * @param id
    * @return boolean
    * @auth: tianlei
    * @date: 2026/1/15 20:57
    */
    boolean delete(String id);

}
