package com.yunke.admin.modular.common.controller;

import cn.hutool.core.collection.CollUtil;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.yunke.admin.common.base.BaseController;
import com.yunke.admin.common.model.CommonDictVO;
import com.yunke.admin.common.model.DictVO;
import com.yunke.admin.framework.cache.DataDictCache;
import com.yunke.admin.framework.cache.EnumDictCache;
import com.yunke.admin.framework.core.response.ResponseData;
import com.yunke.admin.framework.core.response.SuccessResponseData;
import com.yunke.admin.modular.common.model.param.DictQueryParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "通用字典接口")
@ApiSupport(author = "tianlei",order = 3)
@RestController
@RequestMapping(value = "/common/dict/")
public class CommonDictController extends BaseController {
    @Autowired
    private EnumDictCache enumDictCache;
    @Autowired
    private DataDictCache dataDictCache;

    @GetMapping(value = "allEnumDict")
    public ResponseData allEnumDict(){
        return new SuccessResponseData(null);
    }

    @ApiOperation(value = "获取枚举字典",position = 2)
    @GetMapping(value = "enumDict")
    public ResponseData<List<CommonDictVO>> enumDict(@Validated DictQueryParam dictQueryParam){
        DictVO dict = enumDictCache.get(dictQueryParam.getKey());
        List<CommonDictVO> ret = CollUtil.newArrayList();
        if(dict != null){
            ret = dict.entrySet().stream().map(en -> new CommonDictVO((String)en.getValue(), en.getKey())).collect(Collectors.toList());
        }
        return new SuccessResponseData(ret);
    }

    @GetMapping(value = "allDataDict")
    public ResponseData allDataDict(){
        return new SuccessResponseData(null);
    }

    @ApiOperation(value = "获取数据字典",position = 1)
    @GetMapping(value = "dataDict")
    public ResponseData<List<CommonDictVO>> dataDict(@Validated DictQueryParam dictQueryParam){
        DictVO dict = dataDictCache.get(dictQueryParam.getKey());
        List<CommonDictVO> ret = CollUtil.newArrayList();
        if(dict != null){
            ret = dict.entrySet().stream().map(en -> new CommonDictVO((String)en.getValue(), en.getKey())).collect(Collectors.toList());
        }
        return new SuccessResponseData(ret);
    }



}
