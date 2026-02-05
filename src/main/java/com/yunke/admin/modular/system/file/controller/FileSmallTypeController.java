package com.yunke.admin.modular.system.file.controller;

import com.yunke.admin.common.base.BaseController;
import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.framework.core.annotion.OpLog;
import com.yunke.admin.framework.core.annotion.OpLogHeader;
import com.yunke.admin.framework.core.enums.OpLogAnnotionOpTypeEnum;
import com.yunke.admin.framework.core.response.ResponseData;
import com.yunke.admin.framework.core.response.SuccessResponseData;
import com.yunke.admin.modular.system.file.model.param.FileSmallTypeAddParam;
import com.yunke.admin.modular.system.file.model.param.FileSmallTypeEditParam;
import com.yunke.admin.modular.system.file.model.param.FileSmallTypePageQueryParam;
import com.yunke.admin.modular.system.file.model.param.FileSmallTypeUpdateSortParam;
import com.yunke.admin.modular.system.file.model.vo.FileSmallTypeVO;
import com.yunke.admin.modular.system.file.service.FileSmallTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;

/**
 * @className FileSmallTypeController
 * @description: 系统附件子类型表_前端控制器
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@OpLogHeader("附件子类型管理")
@RestController
@RequestMapping("/sys/file/filesmalltype/")
@RequiredArgsConstructor
@Validated
public class FileSmallTypeController extends BaseController {

    private final FileSmallTypeService fileSmallTypeService;

    @OpLog(title = "分页查询",opType = OpLogAnnotionOpTypeEnum.QUERY)
    @PostMapping(value = "page")
    public ResponseData<PageWrapper<FileSmallTypeVO>> page(@Validated @RequestBody FileSmallTypePageQueryParam pageQueryParam){
        return new SuccessResponseData<>(fileSmallTypeService.selectPageVO(pageQueryParam));
    }

    @OpLog(title = "查询列表",opType = OpLogAnnotionOpTypeEnum.QUERY)
    @GetMapping(value = "listByBigtypeId")
    public ResponseData listByBigtypeId(@NotEmpty(message = "大类型id不能为空，请检查参数bigTypeId") String bigTypeId){
        return new SuccessResponseData<>(fileSmallTypeService.listByBigtypeId(bigTypeId));
    }

    @GetMapping(value = "detail")
    public ResponseData<FileSmallTypeVO> detail(@NotEmpty(message = "主键不能为空，请检查参数id") String id){
        return new SuccessResponseData<>(fileSmallTypeService.selectVO(id));
    }

    @OpLog(title = "新增",opType = OpLogAnnotionOpTypeEnum.INSERT)
    @PostMapping(value = "add")
    public ResponseData add(@Validated @RequestBody FileSmallTypeAddParam addParam){
        return ResponseData.bool(fileSmallTypeService.add(addParam));
    }

    @OpLog(title = "编辑",opType = OpLogAnnotionOpTypeEnum.UPDATE)
    @PostMapping(value = "edit")
    public ResponseData edit(@Validated @RequestBody FileSmallTypeEditParam editParam){
        return ResponseData.bool(fileSmallTypeService.edit(editParam));
    }

    @OpLog(title = "删除",opType = OpLogAnnotionOpTypeEnum.DELETE)
    @GetMapping(value = "delete")
    public ResponseData delete(@NotEmpty(message = "删除主键不能为空，请检查参数id") String id){
        return ResponseData.bool(fileSmallTypeService.delete(id));
    }

    @OpLog(title = "修改排序",opType = OpLogAnnotionOpTypeEnum.UPDATE)
    @PostMapping(value = "changeSort")
    public ResponseData changeSort(@Validated @RequestBody FileSmallTypeUpdateSortParam updateSortParam){
        return ResponseData.bool(fileSmallTypeService.changeSort(updateSortParam));
    }

}
