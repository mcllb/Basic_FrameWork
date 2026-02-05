package com.yunke.admin.modular.system.file.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.common.util.BeanUtil;
import com.yunke.admin.framework.core.exception.ServiceException;
import com.yunke.admin.framework.mybatisplus.base.GeneralServiceImpl;
import com.yunke.admin.modular.system.file.mapper.FileSmallTypeMapper;
import com.yunke.admin.modular.system.file.model.entity.FileSmallType;
import com.yunke.admin.modular.system.file.model.entity.FileStore;
import com.yunke.admin.modular.system.file.model.param.FileSmallTypeAddParam;
import com.yunke.admin.modular.system.file.model.param.FileSmallTypeEditParam;
import com.yunke.admin.modular.system.file.model.param.FileSmallTypePageQueryParam;
import com.yunke.admin.modular.system.file.model.param.FileSmallTypeUpdateSortParam;
import com.yunke.admin.modular.system.file.model.vo.FileSmallTypeVO;
import com.yunke.admin.modular.system.file.service.FileSmallTypeService;
import com.yunke.admin.modular.system.file.service.FileStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @className FileSmallTypeServiceImpl
 * @description: 系统附件子类型表_Service实现类
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Service
public class FileSmallTypeServiceImpl extends GeneralServiceImpl<FileSmallTypeMapper, FileSmallType> implements FileSmallTypeService {
    @Autowired
    private FileStoreService fileStoreService;

    @Override
    public PageWrapper<FileSmallTypeVO> selectPageVO(FileSmallTypePageQueryParam pageQueryParam) {
        LambdaQueryWrapper<FileSmallType> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(FileSmallType::getBigTypeId, pageQueryParam.getBigTypeId());
        // 查询条件：类型名称
        if(StrUtil.isNotEmpty(pageQueryParam.getTypeName())){
            lambdaQueryWrapper.like(FileSmallType::getTypeName, pageQueryParam.getTypeName());
        }
        // 查询条件：类型代码
        if(StrUtil.isNotEmpty(pageQueryParam.getTypeCode())){
            lambdaQueryWrapper.like(FileSmallType::getTypeCode,pageQueryParam.getTypeCode());
        }
        // 排序
        lambdaQueryWrapper.orderByAsc(FileSmallType::getSort);

        Page<FileSmallType> pageData = this.page(pageQueryParam.page(), lambdaQueryWrapper);
        List<FileSmallType> records = pageData.getRecords();
        List<FileSmallTypeVO> voList = BeanUtil.copyListProperties(records, FileSmallTypeVO::new);
        BeanUtil.fillListDataDictField(voList);
        BeanUtil.fillListEnumDictField(voList);
        PageWrapper<FileSmallTypeVO> pageWrapper = new PageWrapper<>(voList, pageData.getTotal(), pageData.getSize(), pageData.getCurrent());
        return pageWrapper;
    }

    @Override
    public List<FileSmallTypeVO> listByBigtypeId(String bigTypeId) {
        List<FileSmallTypeVO> ret = CollUtil.newArrayList();
        List<FileSmallType> list = this.lambdaQuery().eq(FileSmallType::getBigTypeId, bigTypeId)
                .orderByAsc(FileSmallType::getSort)
                .list();
        if(CollUtil.isNotEmpty(list)){
            ret = BeanUtil.copyListProperties(list, FileSmallTypeVO::new);
            BeanUtil.fillListDataDictField(ret);
            BeanUtil.fillListEnumDictField(ret);
        }
        return ret;
    }

    @Override
    public FileSmallTypeVO selectVO(String id){
        FileSmallTypeVO fileSmallTypeVO = new FileSmallTypeVO();
        FileSmallType fileSmallType = this.getById(id);
        BeanUtil.copyProperties(fileSmallType,fileSmallTypeVO);
        if(fileSmallTypeVO != null){
            String[] split = fileSmallTypeVO.getAllowFileExtension().split(",");
            fileSmallTypeVO.setAllowFileExtensionList(CollUtil.newArrayList(split));
        }
        return fileSmallTypeVO;
    }

    @Transactional
    @Override
    public boolean add(FileSmallTypeAddParam addParam) {
        FileSmallType fileSmallType = new FileSmallType();
        BeanUtil.copyProperties(addParam,fileSmallType);
        this.checkParam(fileSmallType,false);
        String join = CollUtil.join(addParam.getAllowFileExtensionList(), ",");
        fileSmallType.setAllowFileExtension(join);
        return this.save(fileSmallType);
    }

    @Transactional
    @Override
    public boolean edit(FileSmallTypeEditParam editParam) {
        FileSmallType fileSmallType = new FileSmallType();
        BeanUtil.copyProperties(editParam,fileSmallType);
        this.checkParam(fileSmallType,true);
        String join = CollUtil.join(editParam.getAllowFileExtensionList(), ",");
        fileSmallType.setAllowFileExtension(join);
        // 不更新大类型编码
        fileSmallType.setBigTypeCode(null);
        return this.updateById(fileSmallType);
    }

    @Transactional
    @Override
    public boolean delete(String id) {
        FileSmallType fileSmallType = this.getById(id);
        this.checkCanDelete(fileSmallType);
        return this.removeById(id);
    }

    @Override
    public boolean changeSort(FileSmallTypeUpdateSortParam updateSortParam) {
        FileSmallType fileSmallType = new FileSmallType();
        BeanUtil.copyProperties(updateSortParam,fileSmallType);
        return this.updateById(fileSmallType);
    }

    public void checkParam(FileSmallType fileSmallType, boolean isExcludeSelf){
        LambdaQueryWrapper<FileSmallType> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 校验类型名称是否重复
        lambdaQueryWrapper.eq(FileSmallType::getBigTypeId, fileSmallType.getBigTypeId());
        lambdaQueryWrapper.eq(FileSmallType::getTypeName, fileSmallType.getTypeName());
        if(isExcludeSelf){
            lambdaQueryWrapper.ne(FileSmallType::getId, fileSmallType.getId());
        }
        int countName = this.count(lambdaQueryWrapper);
        if(countName > 0){
            throw new ServiceException("类型名称重复");
        }

        // 校验文件类型编码是否重复
        lambdaQueryWrapper.clear();
        lambdaQueryWrapper.eq(FileSmallType::getBigTypeId, fileSmallType.getBigTypeId());
        lambdaQueryWrapper.eq(FileSmallType::getTypeCode, fileSmallType.getTypeCode());
        if(isExcludeSelf){
            lambdaQueryWrapper.ne(FileSmallType::getId, fileSmallType.getId());
        }
        int countCode = this.count(lambdaQueryWrapper);
        if(countCode > 0){
            throw new ServiceException("类型编码重复");
        }
    }

    /**
     * 校验是否可以删除
     * 当此类型没有被使用时可以删除
     * @param fileSmallType
     */
    public void checkCanDelete(FileSmallType fileSmallType){
        if(ObjectUtil.isNotNull(fileSmallType)){
            int fileCount = fileStoreService.lambdaQuery().eq(FileStore::getBigTypeCode,fileSmallType.getBigTypeId())
                    .eq(FileStore::getSmallTypeCode,fileSmallType.getTypeCode())
                    .count();
            if(fileCount > 0){
                throw new ServiceException("该附件类型已被使用过，禁止删除！");
            }
        }
    }
}
