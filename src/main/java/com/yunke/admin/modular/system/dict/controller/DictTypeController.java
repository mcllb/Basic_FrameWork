package com.yunke.admin.modular.system.dict.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunke.admin.common.base.BaseController;
import com.yunke.admin.common.model.DetailQueryParam;
import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.common.model.SingleDeleteParam;
import com.yunke.admin.common.util.BeanUtil;
import com.yunke.admin.framework.core.annotion.OpLog;
import com.yunke.admin.framework.core.annotion.OpLogHeader;
import com.yunke.admin.framework.core.enums.OpLogAnnotionOpTypeEnum;
import com.yunke.admin.framework.core.response.ResponseData;
import com.yunke.admin.framework.core.response.SuccessResponseData;
import com.yunke.admin.modular.system.dict.model.entity.DictType;
import com.yunke.admin.modular.system.dict.model.param.*;
import com.yunke.admin.modular.system.dict.model.vo.DictTypePageVO;
import com.yunke.admin.modular.system.dict.model.vo.DictTypeVO;
import com.yunke.admin.modular.system.dict.service.DictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @className DictTypeController
 * @description: 系统字典类型表_前端控制器
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@OpLogHeader("字典类型管理")
@RestController
@RequestMapping("/sys/dict-type/")
public class DictTypeController extends BaseController {
    @Autowired
    private DictTypeService dictTypeService;

    @OpLog(title = "分页查询",opType = OpLogAnnotionOpTypeEnum.QUERY)
    @SaCheckPermission("sys:dict:list")
    @PostMapping(value = "page")
    public ResponseData<PageWrapper<DictTypePageVO>> page(@RequestBody DictTypePageQueryParam dictTypePageQueryParam){
        LambdaQueryWrapper<DictType> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 根据字典类型名称模糊查询
        if(StrUtil.isNotEmpty(dictTypePageQueryParam.getName())){
            lambdaQueryWrapper.like(DictType::getName, dictTypePageQueryParam.getName());
        }

        // 根据字典类型编码模糊查询
        if(StrUtil.isNotEmpty(dictTypePageQueryParam.getCode())){
            lambdaQueryWrapper.like(DictType::getCode, dictTypePageQueryParam.getCode());
        }
        lambdaQueryWrapper.orderByAsc(DictType::getSort);
        Page<DictType> pageData = dictTypeService.page(dictTypePageQueryParam.page(), lambdaQueryWrapper);
        List<DictType> records = pageData.getRecords();
        List<DictTypePageVO> pageVOList = BeanUtil.copyListProperties(records, DictTypePageVO::new);
        PageWrapper<DictTypePageVO> pageWrapper = new PageWrapper<>(pageVOList, pageData.getTotal(), pageData.getSize(), pageData.getCurrent());
        return new SuccessResponseData(pageWrapper);
    }

    @OpLog(title = "新增",opType = OpLogAnnotionOpTypeEnum.INSERT)
    @SaCheckPermission("sys:dict:add")
    @PostMapping(value = "add")
    public ResponseData add(@Validated @RequestBody DictTypeAddParam dictTypeAddParam){
        return ResponseData.bool(dictTypeService.add(dictTypeAddParam));
    }

    @OpLog(title = "编辑",opType = OpLogAnnotionOpTypeEnum.UPDATE)
    @SaCheckPermission("sys:dict:edit")
    @PostMapping(value = "edit")
    public ResponseData edit(@Validated @RequestBody DictTypeEditParam dictTypeEditParam){
        return ResponseData.bool(dictTypeService.edit(dictTypeEditParam));
    }

    @OpLog(title = "删除",opType = OpLogAnnotionOpTypeEnum.DELETE)
    @SaCheckPermission("sys:dict:delete")
    @PostMapping(value = "delete")
    public ResponseData delete(@Validated @RequestBody SingleDeleteParam singleDeleteParam){
        return ResponseData.bool(dictTypeService.delete(singleDeleteParam));
    }

    @OpLog(title = "修改状态",opType = OpLogAnnotionOpTypeEnum.UPDATE)
    @SaCheckPermission("sys:dict:edit")
    @PostMapping(value = "changeStatus")
    public ResponseData changeStatus(@Validated @RequestBody DictTypeUpdateStatusParam dictTypeUpdateStatusParam){
        return ResponseData.bool(dictTypeService.updateDictTypeStatus(dictTypeUpdateStatusParam));
    }

    @GetMapping(value = "detail")
    public ResponseData<DictTypeVO> detail(DetailQueryParam detailQueryParam) {
        DictType dictType = dictTypeService.getById(detailQueryParam.getId());
        DictTypeVO dictTypeVO = new DictTypeVO();
        BeanUtil.copyProperties(dictType,dictTypeVO);
        return new SuccessResponseData<DictTypeVO>(dictTypeVO);
    }

    @OpLog(title = "修改排序",opType = OpLogAnnotionOpTypeEnum.UPDATE)
    @PostMapping(value = "changeSort")
    public ResponseData changeSort(@Validated @RequestBody DictUpdateSortParam updateSortParam){
        return ResponseData.bool(dictTypeService.updateSort(updateSortParam));
    }

}
