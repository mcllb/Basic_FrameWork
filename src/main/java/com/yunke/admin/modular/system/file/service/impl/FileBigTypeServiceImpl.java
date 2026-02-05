package com.yunke.admin.modular.system.file.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.common.util.BeanUtil;
import com.yunke.admin.framework.core.exception.ServiceException;
import com.yunke.admin.framework.mybatisplus.base.GeneralServiceImpl;
import com.yunke.admin.modular.system.file.mapper.FileBigTypeMapper;
import com.yunke.admin.modular.system.file.mapper.FileSmallTypeMapper;
import com.yunke.admin.modular.system.file.model.entity.FileBigType;
import com.yunke.admin.modular.system.file.model.entity.FileSmallType;
import com.yunke.admin.modular.system.file.model.entity.FileStore;
import com.yunke.admin.modular.system.file.model.param.FileBigTypeAddParam;
import com.yunke.admin.modular.system.file.model.param.FileBigTypeEditParam;
import com.yunke.admin.modular.system.file.model.param.FileBigTypePageQueryParam;
import com.yunke.admin.modular.system.file.model.vo.FileBigTypeVO;
import com.yunke.admin.modular.system.file.service.FileBigTypeService;
import com.yunke.admin.modular.system.file.service.FileStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @className FileBigTypeServiceImpl
 * @description: 系统附件类型表_Service实现类
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@Service
public class FileBigTypeServiceImpl extends GeneralServiceImpl<FileBigTypeMapper, FileBigType> implements FileBigTypeService {

    @Autowired
    private FileSmallTypeMapper fileSmallTypeMapper;
    @Autowired
    private FileStoreService fileStoreService;

    @Override
    public PageWrapper<FileBigTypeVO> selectPageVO(FileBigTypePageQueryParam pageQueryParam) {
        LambdaQueryWrapper<FileBigType> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 查询条件：类型名称
        if(StrUtil.isNotEmpty(pageQueryParam.getTypeName())){
            lambdaQueryWrapper.like(FileBigType::getTypeName, pageQueryParam.getTypeName());
        }
        // 查询条件：类型代码
        if(StrUtil.isNotEmpty(pageQueryParam.getTypeCode())){
            lambdaQueryWrapper.like(FileBigType::getTypeCode, pageQueryParam.getTypeCode());
        }
        // 排序
        lambdaQueryWrapper.orderByDesc(FileBigType::getCreateTime);

        Page<FileBigType> pageData = this.page(pageQueryParam.page(), lambdaQueryWrapper);
        List<FileBigType> records = pageData.getRecords();
        List<FileBigTypeVO> voList = BeanUtil.copyListProperties(records, FileBigTypeVO::new);
        BeanUtil.fillListDataDictField(voList);
        BeanUtil.fillListEnumDictField(voList);
        PageWrapper<FileBigTypeVO> pageWrapper = new PageWrapper<>(voList, pageData.getTotal(), pageData.getSize(), pageData.getCurrent());
        return pageWrapper;
    }
    
    @Override
    public FileBigTypeVO selectVO(String id){
        FileBigTypeVO fileBigTypeVO = new FileBigTypeVO();
        FileBigType fileBigType = this.getById(id);
        BeanUtil.copyProperties(fileBigType,fileBigTypeVO);
        return fileBigTypeVO;
    }

    @Transactional
    @Override
    public boolean add(FileBigTypeAddParam addParam) {
        FileBigType fileBigType = new FileBigType();
        BeanUtil.copyProperties(addParam,fileBigType);
        // 校验参数
        checkParam(fileBigType,false);
        return this.save(fileBigType);
    }

    @Transactional
    @Override
    public boolean edit(FileBigTypeEditParam editParam) {
        FileBigType fileBigType = new FileBigType();
        BeanUtil.copyProperties(editParam,fileBigType);
        // 校验参数
        checkParam(fileBigType,true);
        FileBigType oldBigType = this.getById(fileBigType.getId());
        boolean ret = this.updateById(fileBigType);
        if(ret){
            // 如果有修改代码，需要再修改子类型表中的big_type_code
            if(!fileBigType.getTypeCode().equals(oldBigType.getTypeCode())){
                LambdaUpdateWrapper<FileSmallType> fileSmallTypeUpdateWrapper = new LambdaUpdateWrapper();
                fileSmallTypeUpdateWrapper.set(FileSmallType::getBigTypeCode, fileBigType.getTypeCode());
                fileSmallTypeUpdateWrapper.eq(FileSmallType::getBigTypeId, fileBigType.getId());
                fileSmallTypeMapper.update(new FileSmallType(),fileSmallTypeUpdateWrapper);
            }
        }
        return ret;
    }

    @Transactional
    @Override
    public boolean delete(String id) {
        // 校验是否可以删除
        FileBigType fileBigType = this.getById(id);
        this.checkCanDelete(fileBigType);
        boolean ret = this.baseMapper.deleteById(id) > 0;
        if(ret){
            LambdaQueryWrapper<FileSmallType> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(FileSmallType::getBigTypeId, id);
            fileSmallTypeMapper.delete(lambdaQueryWrapper);
        }
        return ret;
    }

    public void checkParam(FileBigType fileBigType, boolean isExcludeSelf){
        LambdaQueryWrapper<FileBigType> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 校验文件类型名称是否重复
        lambdaQueryWrapper.eq(FileBigType::getTypeName, fileBigType.getTypeName());
        if(isExcludeSelf){
            lambdaQueryWrapper.ne(FileBigType::getId, fileBigType.getId());
        }
        int countName = this.count(lambdaQueryWrapper);
        if(countName > 0){
            throw new ServiceException("附件类型名称重复");
        }

        // 校验文件类型编码是否重复
        lambdaQueryWrapper.clear();
        lambdaQueryWrapper.eq(FileBigType::getTypeCode, fileBigType.getTypeCode());
        if(isExcludeSelf){
            lambdaQueryWrapper.ne(FileBigType::getId, fileBigType.getId());
        }
        int countCode = this.count(lambdaQueryWrapper);
        if(countCode > 0){
            throw new ServiceException("附件类型编码重复");
        }
    }

    /**
     * 校验是否可以删除
     * 此附件类型及子类型没有被使用时可以删除
     * @param fileBigType
     */
    public void checkCanDelete(FileBigType fileBigType){
        if(ObjectUtil.isNotNull(fileBigType)){
            int fileCount = fileStoreService.lambdaQuery().eq(FileStore::getBigTypeCode,fileBigType.getTypeCode())
                    .count();
            if(fileCount > 0){
                throw new ServiceException("该附件类型已被使用过，禁止删除！");
            }
        }
    }

}
