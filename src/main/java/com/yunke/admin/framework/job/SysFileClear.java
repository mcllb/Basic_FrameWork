package com.yunke.admin.framework.job;

import cn.hutool.core.collection.CollUtil;
import com.yunke.admin.common.util.DateUtil;
import com.yunke.admin.modular.system.file.model.entity.FileStore;
import com.yunke.admin.modular.system.file.service.FileStoreService;
import com.yunke.admin.modular.system.file.service.FileUploadService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
  * @className SysFileClear
  * @description 文件清理
  * <p></p>
  * @version 1.0
  * @author tianlei
  * @date 2026/2/4
  */
public class SysFileClear {

    @Resource
    private FileUploadService fileUploadService;
    @Resource
    private FileStoreService fileStoreService;

    /**
     * @description: 清理垃圾文件
     * <p>垃圾文件businessId以temp_开头，只清理一小时前上传的</p>
     * @param
     * @return void
     * @auth: tianlei
     * @date: 2026/2/4 11:28
     */
    public void clearTempFile(){
        Date endTime = DateUtil.offsetHour(new Date(), -1);
        List<FileStore> fileStoreList = fileStoreService.lambdaQuery()
                .select(FileStore::getId)
                .likeRight(FileStore::getBusinessId, "temp_")
                .le(FileStore::getCreateTime, endTime)
                .list();
        if(CollUtil.isNotEmpty(fileStoreList)){
            for(FileStore fileStore:fileStoreList){
                fileStoreService.removeById(fileStore.getId());
            }
        }
    }

}
