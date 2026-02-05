package com.yunke.admin.modular.system.file.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.common.util.BeanUtil;
import com.yunke.admin.framework.mybatisplus.base.GeneralServiceImpl;
import com.yunke.admin.modular.system.file.mapper.FileStoreMapper;
import com.yunke.admin.modular.system.file.model.entity.FileBigType;
import com.yunke.admin.modular.system.file.model.entity.FileSmallType;
import com.yunke.admin.modular.system.file.model.entity.FileStore;
import com.yunke.admin.modular.system.file.model.param.FileStoreAddParam;
import com.yunke.admin.modular.system.file.model.param.FileStoreEditParam;
import com.yunke.admin.modular.system.file.model.param.FileStorePageQueryParam;
import com.yunke.admin.modular.system.file.model.vo.FileStorePageVO;
import com.yunke.admin.modular.system.file.model.vo.FileStoreVO;
import com.yunke.admin.modular.system.file.service.FileBigTypeService;
import com.yunke.admin.modular.system.file.service.FileStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @className FileStoreServiceImpl
 * @description: 系统附件表_Service实现类
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Service
public class FileStoreServiceImpl extends GeneralServiceImpl<FileStoreMapper, FileStore> implements FileStoreService {
    @Autowired
    private FileBigTypeService fileBigTypeService;

    @Override
    public PageWrapper<FileStorePageVO> selectPageVO(FileStorePageQueryParam pageQueryParam) {
        MPJLambdaWrapper<FileStore> mpjLambdaWrapper = new MPJLambdaWrapper();
        mpjLambdaWrapper.selectAll(FileStore.class);
        mpjLambdaWrapper.selectAs(FileBigType::getTypeName, FileStorePageVO::getBigTypeName);
        mpjLambdaWrapper.selectAs(FileSmallType::getTypeName, FileStorePageVO::getSmallTypeName);
        mpjLambdaWrapper.leftJoin(FileBigType.class, FileBigType::getTypeCode, FileStore::getBigTypeCode);
        mpjLambdaWrapper.leftJoin(FileSmallType.class, on -> {
            on.eq(FileSmallType::getTypeCode, FileStore::getSmallTypeCode)
                    .eq(FileSmallType::getBigTypeCode, FileBigType::getTypeCode);
                    return on;
        });

        // 查询条件：附件类型
        if(StrUtil.isNotEmpty(pageQueryParam.getBigTypeCode())){
            mpjLambdaWrapper.eq(FileStore::getBigTypeCode, pageQueryParam.getBigTypeCode());
        }
        
        // 查询条件：附件名称
        if(StrUtil.isNotEmpty(pageQueryParam.getOriginalFileName())){
            mpjLambdaWrapper.like(FileStore::getOriginalFileName, pageQueryParam.getOriginalFileName());
        }
        
        // 排序
        mpjLambdaWrapper.orderByDesc(FileStore::getCreateTime);
        IPage<FileStorePageVO> pageData = this.baseMapper.selectJoinPage(pageQueryParam.page(), FileStorePageVO.class, mpjLambdaWrapper);
        List<FileStorePageVO> records = pageData.getRecords();
        BeanUtil.fillListDataDictField(records);
        BeanUtil.fillListEnumDictField(records);
        BeanUtil.fillListUserField(records);

        PageWrapper<FileStorePageVO> pageWrapper = new PageWrapper<>(records, pageData.getTotal(), pageData.getSize(), pageData.getCurrent());
        return pageWrapper;
    }
    
    @Override
    public FileStoreVO selectVO(String id){
        FileStoreVO fileStoreVO = new FileStoreVO();
        FileStore fileStore = this.getById(id);
        BeanUtil.copyProperties(fileStore,fileStoreVO);
        return fileStoreVO;
    }

    @Transactional
    @Override
    public boolean add(FileStoreAddParam addParam) {
        FileStore fileStore = new FileStore();
        BeanUtil.copyProperties(addParam,fileStore);
        return this.save(fileStore);
    }

    @Transactional
    @Override
    public boolean edit(FileStoreEditParam editParam) {
        FileStore fileStore = new FileStore();
        BeanUtil.copyProperties(editParam,fileStore);
        return this.updateById(fileStore);
    }

    @Transactional
    @Override
    public boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    public boolean deleteWithBusinessIdAndFileType(String businessId, String fileType) {
        LambdaUpdateWrapper<FileStore> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(FileStore::getBusinessId, businessId);
        lambdaUpdateWrapper.eq(FileStore::getBigTypeCode, fileType);
        List<FileStore> list = this.list(lambdaUpdateWrapper);
        if(list != null && list.size() >= 0){
            list.stream().forEach(fileStore -> {
                File file = new File(fileStore.getAbsolutePath());
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
                this.removeById(fileStore.getId());
            });
        }
        return true;
    }

    @Override
    public List<Map<String, Object>> getBigTypeOptions() {
        List<FileBigType> list = fileBigTypeService.lambdaQuery().select(FileBigType::getTypeCode, FileBigType::getTypeName)
                .orderByDesc(FileBigType::getCreateTime).list();
        List<Map<String, Object>> ret = CollUtil.newArrayList();
        if(list != null){
            list.forEach(item -> {
                Map<String, Object> map = MapUtil.newHashMap();
                map.put("value", item.getTypeCode());
                map.put("label", item.getTypeName());
                ret.add(map);
            });
        }
        return ret;
    }
}
