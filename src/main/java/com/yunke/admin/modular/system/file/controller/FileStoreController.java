package com.yunke.admin.modular.system.file.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yunke.admin.common.base.BaseController;
import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.framework.core.annotion.OpLog;
import com.yunke.admin.framework.core.annotion.OpLogHeader;
import com.yunke.admin.framework.core.enums.OpLogAnnotionOpTypeEnum;
import com.yunke.admin.framework.core.response.ResponseData;
import com.yunke.admin.framework.core.response.SuccessResponseData;
import com.yunke.admin.modular.system.file.model.param.FileStoreAddParam;
import com.yunke.admin.modular.system.file.model.param.FileStoreEditParam;
import com.yunke.admin.modular.system.file.model.param.FileStorePageQueryParam;
import com.yunke.admin.modular.system.file.model.vo.FileStorePageVO;
import com.yunke.admin.modular.system.file.model.vo.FileStoreVO;
import com.yunke.admin.modular.system.file.service.FileStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;

/**
 * @className FileStoreController
 * @description: 系统附件表_前端控制器
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@OpLogHeader("附件管理")
@RestController
@RequestMapping("/sys/file/filestore/")
@RequiredArgsConstructor
@Validated
public class FileStoreController extends BaseController {

    private final FileStoreService fileStoreService;

    @OpLog(title = "分页查询",opType = OpLogAnnotionOpTypeEnum.QUERY)
    @SaCheckPermission("file:filestore:list")
    @PostMapping(value = "page")
    public ResponseData<PageWrapper<FileStorePageVO>> page(@RequestBody FileStorePageQueryParam pageQueryParam){
        return new SuccessResponseData<>(fileStoreService.selectPageVO(pageQueryParam));
    }

    @GetMapping(value = "detail")
    public ResponseData<FileStoreVO> detail(@NotEmpty String id){
        return new SuccessResponseData<>(fileStoreService.selectVO(id));
    }

    @OpLog(title = "新增",opType = OpLogAnnotionOpTypeEnum.INSERT)
    @SaCheckPermission("file:filestore:add")
    @PostMapping(value = "add")
    public ResponseData add(@Validated @RequestBody FileStoreAddParam addParam){
        return ResponseData.bool(fileStoreService.add(addParam));
    }

    @OpLog(title = "编辑",opType = OpLogAnnotionOpTypeEnum.UPDATE)
    @SaCheckPermission("file:filestore:edit")
    @PostMapping(value = "edit")
    public ResponseData edit(@Validated @RequestBody FileStoreEditParam editParam){
        return ResponseData.bool(fileStoreService.edit(editParam));
    }

    @OpLog(title = "删除",opType = OpLogAnnotionOpTypeEnum.DELETE)
    @SaCheckPermission("file:filestore:delete")
    @GetMapping(value = "delete")
    public ResponseData delete(@NotEmpty String id){
        return ResponseData.bool(fileStoreService.delete(id));
    }

    @GetMapping(value = "bigTypeOptions")
    public ResponseData bigTypeOptions(){
        return new SuccessResponseData<>(fileStoreService.getBigTypeOptions());
    }

}
