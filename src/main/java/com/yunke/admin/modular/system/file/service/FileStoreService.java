package com.yunke.admin.modular.system.file.service;

import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.framework.mybatisplus.base.GeneralService;
import com.yunke.admin.modular.system.file.model.entity.FileStore;
import com.yunke.admin.modular.system.file.model.param.FileStoreAddParam;
import com.yunke.admin.modular.system.file.model.param.FileStoreEditParam;
import com.yunke.admin.modular.system.file.model.param.FileStorePageQueryParam;
import com.yunke.admin.modular.system.file.model.vo.FileStorePageVO;
import com.yunke.admin.modular.system.file.model.vo.FileStoreVO;

import java.util.List;
import java.util.Map;

/**
 * @className FileStoreService
 * @description: 系统附件表_Service接口
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
public interface FileStoreService extends GeneralService<FileStore> {
	
   /**
    * @description: 系统附件表_分页查询
    * <p></p>
    * @param pageQueryParam
    * @return com.yunke.admin.common.model.PageWrapper<com.yunke.admin.modular.system.file.model.vo.FileStorePageVO>
    * @auth: tianlei
    * @date: 2026/1/15 20:52
    */
    PageWrapper<FileStorePageVO> selectPageVO(FileStorePageQueryParam pageQueryParam);
	
   /**
    * @description: 系统附件表_详情
    * <p></p>
    * @param id
    * @return com.yunke.admin.modular.system.file.model.vo.FileStoreVO
    * @auth: tianlei
    * @date: 2026/1/15 20:53
    */
    FileStoreVO selectVO(String id);
    
    /**
     * @description: 系统附件表_新增
     * <p></p>
     * @param addParam
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/15 20:53
     */
    boolean add(FileStoreAddParam addParam);
	
    /**
     * @description: 系统附件表_编辑
     * <p></p>
     * @param editParam
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/15 20:53
     */
    boolean edit(FileStoreEditParam editParam);
	
   /**
    * @description: 系统附件表_根据id删除
    * <p></p>
    * @param id
    * @return boolean
    * @auth: tianlei
    * @date: 2026/1/15 20:54
    */
    boolean delete(String id);

    /**
     * @description: 根据业务id与附件类型删除
     * <p></p>
     * @param businessId 业务id
     * @param fileType 附件大类编码
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/15 20:54
     */
    boolean deleteWithBusinessIdAndFileType(String businessId, String fileType);


    List<Map<String,Object>> getBigTypeOptions();

}
