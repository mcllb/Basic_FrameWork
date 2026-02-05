package com.yunke.admin.modular.system.file.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.yunke.admin.common.util.BeanUtil;
import com.yunke.admin.common.util.IdUtil;
import com.yunke.admin.common.util.RandomUtil;
import com.yunke.admin.common.util.SaUtil;
import com.yunke.admin.framework.core.exception.ServiceException;
import com.yunke.admin.framework.core.util.SysConfigUtil;
import com.yunke.admin.modular.system.file.model.entity.FileBigType;
import com.yunke.admin.modular.system.file.model.entity.FileSmallType;
import com.yunke.admin.modular.system.file.model.entity.FileStore;
import com.yunke.admin.modular.system.file.model.param.FileUploadParam;
import com.yunke.admin.modular.system.file.model.vo.FileSmallTypeVO;
import com.yunke.admin.modular.system.file.model.vo.FileStoreVO;
import com.yunke.admin.modular.system.file.service.FileBigTypeService;
import com.yunke.admin.modular.system.file.service.FileSmallTypeService;
import com.yunke.admin.modular.system.file.service.FileStoreService;
import com.yunke.admin.modular.system.file.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @className FileUploadServiceImpl
 * @description: 根据附件类型编码获取子类型集合_Service实现类
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private FileBigTypeService fileBigTypeService;
    @Autowired
    private FileSmallTypeService fileSmallTypeService;
    @Autowired
    private FileStoreService fileStoreService;

    @Override
    public List<FileSmallTypeVO> getSmallTypeList(String fileType) {
        LambdaQueryWrapper<FileBigType> fileBigTypeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        fileBigTypeLambdaQueryWrapper.eq(FileBigType::getTypeCode,fileType);
        FileBigType fileBigType = fileBigTypeService.getOne(fileBigTypeLambdaQueryWrapper);
        if(fileBigType == null){
            throw new ServiceException("附件类型不存在："+ fileType);
        }
        LambdaQueryWrapper<FileSmallType> fileSmallTypeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        fileSmallTypeLambdaQueryWrapper.eq(FileSmallType::getBigTypeId, fileBigType.getId());
        fileSmallTypeLambdaQueryWrapper.orderByAsc(FileSmallType::getSort);
        List<FileSmallType> fileSmallTypeList = fileSmallTypeService.list(fileSmallTypeLambdaQueryWrapper);
        List<FileSmallTypeVO> fileSmallTypeVOS = BeanUtil.copyListProperties(fileSmallTypeList, FileSmallTypeVO::new);
        return fileSmallTypeVOS;
    }

    @Transactional
    @Override
    public String upload(FileUploadParam fileUploadParam){
        MultipartFile file = fileUploadParam.getFile();
        String businessId = fileUploadParam.getBusinessId();
        String bigTypeCode = fileUploadParam.getBigTypeCode();
        String smallTypeCode = fileUploadParam.getSmallTypeCode();
        String baseDir = this.getUploadDir();
        String fileOriName = file.getOriginalFilename();
        String fileExtension = FileUtil.extName(fileOriName);
        String fileId = IdUtil.getSnowflakeNextIdStr() + "_" + RandomUtil.randomString(4);
        String fileName = fileId + "." + fileExtension;
        String contentType = file.getContentType();
        String savePath = File.separator + bigTypeCode + File.separator + smallTypeCode
                + File.separator + businessId + File.separator + fileName;
        File targetFile;
        try {
            targetFile = new File(baseDir + savePath);
            if(!targetFile.getParentFile().exists()){
                targetFile.getParentFile().mkdirs();
            }
            if(!targetFile.exists()){
                targetFile.createNewFile();
            }
            FileUtil.writeFromStream(file.getInputStream(), targetFile, true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException("上传文件发送错误");
        }

        FileStore fileStore = new FileStore();
        fileStore.setId(fileId);
        fileStore.setBusinessId(businessId);
        fileStore.setBigTypeCode(bigTypeCode);
        fileStore.setSmallTypeCode(smallTypeCode);
        fileStore.setFileName(fileName);
        fileStore.setOriginalFileName(fileOriName);
        fileStore.setFileExtension(fileExtension);
        fileStore.setContentType(contentType);
        fileStore.setFileSize(file.getSize());
        fileStore.setRelativePath(savePath.replace("\\","/"));
        fileStoreService.save(fileStore);
        return fileId;
    }

    @Override
    public boolean deleteById(String id) {
        FileStore fileStore = fileStoreService.getById(id);
        if(fileStore != null){
            File file = FileUtil.file(getUploadDir(), fileStore.getRelativePath());
            if(file.exists()){
                file.delete();
                // 当前文件目录为空时删除
                File parentFile = file.getParentFile();
                if(parentFile.isDirectory() && parentFile.listFiles().length == 0){
                    parentFile.delete();
                }
                // 附件子类型目录为空时删除
                parentFile = parentFile.getParentFile();
                if(parentFile.isDirectory() && parentFile.listFiles().length == 0){
                    parentFile.delete();
                }
                // 附件大类型目录为空时删除
                parentFile = parentFile.getParentFile();
                if(parentFile.isDirectory() && parentFile.listFiles().length == 0){
                    parentFile.delete();
                }
            }
            fileStoreService.removeById(id);
        }
        return true;
    }

    @Override
    public boolean deleteBySmallType(String smallTypeCode, String bigTypeCode, String businessId) {
        LambdaUpdateWrapper<FileStore> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(FileStore::getBusinessId, businessId);
        lambdaUpdateWrapper.eq(FileStore::getBigTypeCode, bigTypeCode);
        lambdaUpdateWrapper.eq(FileStore::getSmallTypeCode, smallTypeCode);
        lambdaUpdateWrapper.orderByAsc(FileStore::getCreateTime);
        List<FileStore> list = fileStoreService.list(lambdaUpdateWrapper);
        if(list != null && list.size() >= 0){
            list.stream().forEach(fileStore -> {
                File file = FileUtil.file(getUploadDir(), fileStore.getRelativePath());
                if(file.exists()){
                    file.delete();
                }
                // 当前文件目录为空时删除
                File parentFile = file.getParentFile();
                if(parentFile.isDirectory() && parentFile.listFiles().length == 0){
                    parentFile.delete();
                }
                // 附件子类型目录为空时删除
                parentFile = parentFile.getParentFile();
                if(parentFile.isDirectory() && parentFile.listFiles().length == 0){
                    parentFile.delete();
                }
                // 附件大类型目录为空时删除
                parentFile = parentFile.getParentFile();
                if(parentFile.isDirectory() && parentFile.listFiles().length == 0){
                    parentFile.delete();
                }
                fileStoreService.removeById(fileStore.getId());
            });
        }
        return true;
    }

    @Override
    public boolean deleteByBigType(String bigTypeCode, String businessId) {
        LambdaUpdateWrapper<FileStore> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(FileStore::getBusinessId, businessId);
        lambdaUpdateWrapper.eq(FileStore::getBigTypeCode, bigTypeCode);
        lambdaUpdateWrapper.orderByAsc(FileStore::getCreateTime);
        List<FileStore> list = fileStoreService.list(lambdaUpdateWrapper);
        if(list != null && list.size() >= 0){
            list.stream().forEach(fileStore -> {
                File file = FileUtil.file(getUploadDir(), fileStore.getRelativePath());
                if(file.exists()){
                    file.delete();
                }
                // 当前文件目录为空时删除
                File parentFile = file.getParentFile();
                if(parentFile.isDirectory() && parentFile.listFiles().length == 0){
                    parentFile.delete();
                }
                // 附件子类型目录为空时删除
                parentFile = parentFile.getParentFile();
                if(parentFile.isDirectory() && parentFile.listFiles().length == 0){
                    parentFile.delete();
                }
                // 附件大类型目录为空时删除
                parentFile = parentFile.getParentFile();
                if(parentFile.isDirectory() && parentFile.listFiles().length == 0){
                    parentFile.delete();
                }
                fileStoreService.removeById(fileStore.getId());
            });
        }
        return true;
    }

    @Override
    public List<FileStoreVO> getFileListBySmall(String smallTypeCode, String bigTypeCode, String businessId) {
        LambdaUpdateWrapper<FileStore> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(FileStore::getBusinessId, businessId);
        lambdaUpdateWrapper.eq(FileStore::getBigTypeCode, bigTypeCode);
        lambdaUpdateWrapper.eq(FileStore::getSmallTypeCode, smallTypeCode);
        lambdaUpdateWrapper.orderByAsc(FileStore::getCreateTime);
        List<FileStore> list = fileStoreService.list(lambdaUpdateWrapper);
        if(list == null || list.size() == 0){
            return CollUtil.newArrayList();
        }
        List<FileStoreVO> listVO = BeanUtil.copyListProperties(list, FileStoreVO::new);
        return listVO;
    }

    @Override
    public List<String> getFileIdsBySmall(String smallTypeCode, String bigTypeCode, String businessId) {
        List<FileStoreVO> fileStoreVOList = getFileListBySmall(smallTypeCode, bigTypeCode, businessId);
        if(CollUtil.isNotEmpty(fileStoreVOList)){
            return fileStoreVOList.stream().map(FileStoreVO::getId).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public Map<String, List<FileStoreVO>> getFileListByBigType(String bigTypeCode, String businessId) {
        LambdaUpdateWrapper<FileStore> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(FileStore::getBusinessId, businessId);
        lambdaUpdateWrapper.eq(FileStore::getBigTypeCode, bigTypeCode);
        lambdaUpdateWrapper.orderByAsc(FileStore::getCreateTime);
        List<FileStore> list = fileStoreService.list(lambdaUpdateWrapper);
        LambdaUpdateWrapper<FileSmallType> fileSmallTypeLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        fileSmallTypeLambdaUpdateWrapper.eq(FileSmallType::getBigTypeCode, bigTypeCode);
        List<FileSmallType> fileSmallTypeList = fileSmallTypeService.list(fileSmallTypeLambdaUpdateWrapper);
        if(list == null || list.size() == 0){
            // 返回map集合，子类型附件为空列表
            Map<String,List<FileStoreVO>> map = new HashMap<>();
            fileSmallTypeList.stream().forEach(fileSmallType -> {
                map.put(fileSmallType.getTypeCode(),CollUtil.newArrayList());
            });
            return map;
        }
        List<FileStoreVO> listVO = BeanUtil.copyListProperties(list, FileStoreVO::new);
        Map<String, List<FileStoreVO>> collect = listVO.stream().collect(Collectors.groupingBy(FileStoreVO::getSmallTypeCode));
        // 补全没有数据的子类型列表
        fileSmallTypeList.stream().forEach(fileSmallType -> {
            if(!collect.containsKey(fileSmallType.getTypeCode())){
                collect.put(fileSmallType.getTypeCode(), CollUtil.newArrayList());
            }
        });
        return collect;
    }

    @Override
    public Map<String, List<String>> getFileIdsByBigType(String bigTypeCode, String businessId) {
        Map<String, List<String>> resultMap = new HashMap<>();
        Map<String, List<FileStoreVO>> fileListMap = getFileListByBigType(bigTypeCode, businessId);
        if(!fileListMap.isEmpty()){
            fileListMap.entrySet().stream().forEach(entry -> {
               List<String> ids = CollUtil.newArrayList();
               if(CollUtil.isNotEmpty(entry.getValue())){
                   ids = entry.getValue().stream().map(FileStoreVO::getId).collect(Collectors.toList());
               }
                resultMap.put(entry.getKey(), ids);
            });
        }
        return resultMap;
    }

    @Override
    public FileStore getFileStoreById(String id) {
        return fileStoreService.getById(id);
    }

    @Override
    public String getUploadDir() {
        return SysConfigUtil.getProjectConfig().getUploadDir();
    }

    @Override
    public boolean checkMustUpload(String businessId, String bigTypeCode) {
        MPJLambdaWrapper<FileStore> mpjLambdaWrapper = new MPJLambdaWrapper<>();
        mpjLambdaWrapper.selectCount(FileStore::getId);
        mpjLambdaWrapper.leftJoin(FileBigType.class,FileBigType::getTypeCode,FileStore::getBigTypeCode).eq(FileStore::getBusinessId, businessId);
        mpjLambdaWrapper.leftJoin(FileSmallType.class,on -> {
            return on.eq(FileSmallType::getBigTypeCode, FileBigType::getTypeCode)
                    .eq(FileSmallType::getTypeCode,FileStore::getSmallTypeCode)
                    .eq(FileSmallType::getIsMust,"1");
        });
        mpjLambdaWrapper.eq(FileStore::getBusinessId,businessId);
        mpjLambdaWrapper.eq(FileStore::getBigTypeCode,bigTypeCode);
        return fileStoreService.selectJoinCount(mpjLambdaWrapper) > 0;
    }

    @Override
    public void updateFileBusinessId(String businessId, String... fileId) {
        if(ArrayUtil.isNotEmpty(fileId)){
            LambdaUpdateChainWrapper<FileStore> lambdaUpdateChainWrapper = fileStoreService.lambdaUpdate()
                    .set(FileStore::getBusinessId, businessId);
            if(SaUtil.isLogin()){
                lambdaUpdateChainWrapper.set(FileStore::getUpdateBy,SaUtil.getUserId());
            }
            lambdaUpdateChainWrapper.set(FileStore::getUpdateTime,new Date());
            lambdaUpdateChainWrapper.in(FileStore::getId,fileId)
                    .update();

        }
    }

    @Override
    public void updateFileBusinessId(String businessId, List<String> fileIds) {
        if(CollUtil.isNotEmpty(fileIds)){
            String[] ids = fileIds.toArray(new String[fileIds.size()]);
            this.updateFileBusinessId(businessId,ids);
        }
    }

    @Override
    public void updateFileTypeCode(String fileId, String bigTypeCode, String smallTypeCode) {
        LambdaUpdateChainWrapper<FileStore> lambdaUpdateChainWrapper = fileStoreService.lambdaUpdate()
                .set(FileStore::getBigTypeCode, bigTypeCode)
                .set(FileStore::getSmallTypeCode, smallTypeCode);
        if(SaUtil.isLogin()){
            lambdaUpdateChainWrapper.set(FileStore::getUpdateBy,SaUtil.getUserId());
        }
        lambdaUpdateChainWrapper.set(FileStore::getUpdateTime,new Date());
        lambdaUpdateChainWrapper.eq(FileStore::getId,fileId)
                .update();

    }


}
