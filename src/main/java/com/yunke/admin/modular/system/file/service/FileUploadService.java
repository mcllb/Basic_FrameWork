package com.yunke.admin.modular.system.file.service;

import com.yunke.admin.modular.system.file.model.entity.FileStore;
import com.yunke.admin.modular.system.file.model.param.FileUploadParam;
import com.yunke.admin.modular.system.file.model.vo.FileSmallTypeVO;
import com.yunke.admin.modular.system.file.model.vo.FileStoreVO;

import java.util.List;
import java.util.Map;

public interface FileUploadService {
    
    /**
     * @description: 根据附件大类代码获取子类代码
     * <p></p> 
     * @param fileType 附件大类代码
     * @return List<FileSmallTypeVO>
     * @auth: tianlei
     * @date: 2026/1/26 11:20
     */
    List<FileSmallTypeVO> getSmallTypeList(String fileType);

    /**
     * @description: 上传附件，返回附件id
     * <p></p>
     * @param fileUploadParam 附件上参数
     * @return String
     * @auth: tianlei
     * @date: 2026/1/26 11:21
     */
    String upload(FileUploadParam fileUploadParam);

    /**
     * @description: 根据附件id删除附件
     * <p></p>
     * @param id
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/26 11:22
     */
    boolean deleteById(String id);

    /**
     * @description: 根据附件子类型删除附件
     * <p></p>
     * @param smallTypeCode 子类代码
     * @param bigTypeCode 大类代码
     * @param businessId 业务id
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/26 11:22
     */
    boolean deleteBySmallType(String smallTypeCode, String bigTypeCode, String businessId);
    /**
     * @description: 根据附件大类删除
     * <p></p>
     * @param bigTypeCode 大类代码
     * @param businessId  业务id
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/26 11:22
     */
    boolean deleteByBigType(String bigTypeCode, String businessId);
    /**
     * @description: 根据附件子类获取附件集合
     * <p></p>
     * @param smallTypeCode
     * @param bigTypeCode
     * @param businessId
     * @return List<FileStoreVO>
     * @auth: tianlei
     * @date: 2026/1/26 11:24
     */
    List<FileStoreVO> getFileListBySmall(String smallTypeCode, String bigTypeCode, String businessId);

    /**
     * @description: 根据附件子类获取附件id集合
     * <p></p>
     * @param smallTypeCode
     * @param bigTypeCode
     * @param businessId
     * @return List<String>
     * @auth: tianlei
     * @date: 2026/1/26 11:26
     */
    List<String> getFileIdsBySmall(String smallTypeCode, String bigTypeCode, String businessId);
    /**
     * @description: 根据附件大类获取附件集合
     * <p></p>
     * @param bigTypeCode
     * @param businessId
     * @return Map<String,List<FileStoreVO>>
     * @auth: tianlei
     * @date: 2026/1/26 11:24
     */
    Map<String,List<FileStoreVO>> getFileListByBigType(String bigTypeCode, String businessId);

    /**
     * @description: 根据附件大类获取附件id集合
     * <p></p>
     * @param bigTypeCode
     * @param businessId
     * @return Map<String,List<FileStoreVO>>
     * @auth: tianlei
     * @date: 2026/1/26 11:27
     */
    Map<String,List<String>> getFileIdsByBigType(String bigTypeCode, String businessId);
    /**
     * @description: 根据附件id获取附件信息
     * <p></p>
     * @param id
     * @return FileStore
     * @auth: tianlei
     * @date: 2026/1/26 11:25
     */
    FileStore getFileStoreById(String id);

    String getUploadDir();
    /**
     * @description: 校验附件必传，只能在上传完成后调用
     * <p></p>
     * @param businessId
     * @param bigTypeCode
     * @return boolean
     * @auth: tianlei
     * @date: 2026/1/26 11:23
     */
    boolean checkMustUpload(String businessId,String bigTypeCode);

    /**
     * @description: 修改附件业务id
     * <p></p>
     * @param businessId 业务id
     * @param fileId 文件id
     * @return void
     * @auth: tianlei
     * @date: 2026/2/3 10:07
     */
    void updateFileBusinessId(String businessId,String... fileId);

    /**
     * @description: 修改附件业务id
     * <p></p>
     * @param businessId 业务id
     * @param fileIds 文件id
     * @return void
     * @auth: tianlei
     * @date: 2026/2/3 14:20
     */
    void updateFileBusinessId(String businessId,List<String> fileIds);

    /**
     * @description: 修改附件类型代码
     * <p>修改附件类型代码可能会影响到文件上传属性的校验，请评估后慎重使用</p>
     * @param fileId 文件id
     * @param bigTypeCode 附件大类代码
     * @param smallTypeCode 附件子类代码
     * @return void
     * @auth: tianlei
     * @date: 2026/2/3 10:07
     */
    void updateFileTypeCode(String fileId,String bigTypeCode, String smallTypeCode);


}
