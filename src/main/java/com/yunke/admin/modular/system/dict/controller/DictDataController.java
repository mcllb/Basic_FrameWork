package com.yunke.admin.modular.system.dict.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yunke.admin.common.base.BaseController;
import com.yunke.admin.common.model.DetailQueryParam;
import com.yunke.admin.common.model.SingleDeleteParam;
import com.yunke.admin.common.util.BeanUtil;
import com.yunke.admin.framework.core.annotion.OpLog;
import com.yunke.admin.framework.core.annotion.OpLogHeader;
import com.yunke.admin.framework.core.enums.OpLogAnnotionOpTypeEnum;
import com.yunke.admin.framework.core.response.ResponseData;
import com.yunke.admin.framework.core.response.SuccessResponseData;
import com.yunke.admin.modular.system.dict.model.entity.DictData;
import com.yunke.admin.modular.system.dict.model.param.*;
import com.yunke.admin.modular.system.dict.model.vo.DictDataPageVO;
import com.yunke.admin.modular.system.dict.model.vo.DictDataVO;
import com.yunke.admin.modular.system.dict.service.DictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @className DictDataController
 * @description: 系统字典值表_前端控制器
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@OpLogHeader("字典值管理")
@RestController
@RequestMapping("/sys/dict-data/")
public class DictDataController extends BaseController {

    @Autowired
    private DictDataService dictDataService;

    @OpLog(title = "查询列表",opType = OpLogAnnotionOpTypeEnum.QUERY)
    @PostMapping(value = "list")
    public ResponseData<List<DictDataPageVO>> list(@RequestBody DictDataPageQueryParam dictDataPageQueryParam){
        LambdaQueryWrapper<DictData> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DictData::getTypeId, dictDataPageQueryParam.getTypeId());
        // 根据字典值模糊查询
        if(StrUtil.isNotEmpty(dictDataPageQueryParam.getValue())){
            lambdaQueryWrapper.like(DictData::getValue, dictDataPageQueryParam.getValue());
        }

        // 根据字典值编码模糊查询
        if(StrUtil.isNotEmpty(dictDataPageQueryParam.getCode())){
            lambdaQueryWrapper.like(DictData::getCode, dictDataPageQueryParam.getCode());
        }
        lambdaQueryWrapper.orderByAsc(DictData::getSort);
        List<DictData> list = dictDataService.list(lambdaQueryWrapper);
        List<DictDataPageVO> pageVOList = CollUtil.newArrayList();
        if(CollUtil.isNotEmpty(list)){
            pageVOList = BeanUtil.copyListProperties(list, DictDataPageVO::new);
        }
        return new SuccessResponseData(pageVOList);
    }

    @OpLog(title = "新增",opType = OpLogAnnotionOpTypeEnum.INSERT)
    @PostMapping(value = "add")
    public ResponseData add(@Validated @RequestBody DictDataAddParam dictDataAddParam){
        return ResponseData.bool(dictDataService.add(dictDataAddParam));
    }

    @OpLog(title = "编辑",opType = OpLogAnnotionOpTypeEnum.UPDATE)
    @PostMapping(value = "edit")
    public ResponseData edit(@Validated @RequestBody DictDataEditParam dictDataEditParam){
        return ResponseData.bool(dictDataService.edit(dictDataEditParam));
    }

    @OpLog(title = "删除",opType = OpLogAnnotionOpTypeEnum.DELETE)
    @PostMapping(value = "delete")
    public ResponseData delete(@Validated @RequestBody SingleDeleteParam singleDeleteParam){
        return ResponseData.bool(dictDataService.delete(singleDeleteParam));
    }

    @OpLog(title = "修改状态",opType = OpLogAnnotionOpTypeEnum.UPDATE)
    @PostMapping(value = "changeStatus")
    public ResponseData changeStatus(@Validated @RequestBody DictDataUpdateStatusParam dictDataUpdateStatusParam){
        return ResponseData.bool(dictDataService.updateDictDataStatus(dictDataUpdateStatusParam));
    }

    @GetMapping(value = "detail")
    public ResponseData<DictDataVO> detail(DetailQueryParam detailQueryParam) {
        DictData dictData = dictDataService.getById(detailQueryParam.getId());
        DictDataVO dictDataVO = new DictDataVO();
        BeanUtil.copyProperties(dictData,dictDataVO);
        return new SuccessResponseData<DictDataVO>(dictDataVO);
    }

    @OpLog(title = "修改排序",opType = OpLogAnnotionOpTypeEnum.UPDATE)
    @PostMapping(value = "changeSort")
    public ResponseData changeSort(@Validated @RequestBody DictUpdateSortParam updateSortParam){
        return ResponseData.bool(dictDataService.updateSort(updateSortParam));
    }

}
