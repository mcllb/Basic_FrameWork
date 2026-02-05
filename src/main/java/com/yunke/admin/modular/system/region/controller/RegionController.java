package com.yunke.admin.modular.system.region.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yunke.admin.common.base.BaseController;
import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.framework.core.annotion.OpLog;
import com.yunke.admin.framework.core.annotion.OpLogHeader;
import com.yunke.admin.framework.core.enums.OpLogAnnotionOpTypeEnum;
import com.yunke.admin.framework.core.response.ResponseData;
import com.yunke.admin.framework.core.response.SuccessResponseData;
import com.yunke.admin.modular.system.region.model.param.RegionAddParam;
import com.yunke.admin.modular.system.region.model.param.RegionEditParam;
import com.yunke.admin.modular.system.region.model.param.RegionEnableParam;
import com.yunke.admin.modular.system.region.model.param.RegionPageQueryParam;
import com.yunke.admin.modular.system.region.model.vo.RegionVO;
import com.yunke.admin.modular.system.region.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @className RegionController
 * @description: 行政区划代码表_前端控制器
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@OpLogHeader("行政区划管理")
@RestController
@RequestMapping("/sys/region/")
@RequiredArgsConstructor
@Validated
public class RegionController extends BaseController {

    private final RegionService regionService;

    @OpLog(title = "分页查询",opType = OpLogAnnotionOpTypeEnum.QUERY)
    @SaCheckPermission("sys:region:page")
    @PostMapping(value = "page")
    public ResponseData<PageWrapper<RegionVO>> page(@RequestBody RegionPageQueryParam pageQueryParam){
        return new SuccessResponseData<>(regionService.pageVO(pageQueryParam));
    }

    @OpLog(title = "查询列表",opType = OpLogAnnotionOpTypeEnum.QUERY)
    @SaCheckPermission("sys:region:page")
    @PostMapping(value = "treeTable")
    public ResponseData<List<RegionVO>> treeTable(@RequestBody RegionPageQueryParam pageQueryParam){
        return new SuccessResponseData<>(regionService.selectTreeTable(pageQueryParam));
    }

    @OpLog(title = "查询列表",opType = OpLogAnnotionOpTypeEnum.QUERY)
    @SaCheckPermission("sys:region:page")
    @GetMapping(value = "list")
    public ResponseData<List<RegionVO>> list(@NotEmpty(message = "父节点id不能为空，请检查参数parentId") String parentId){
        return new SuccessResponseData<>(regionService.selectListByParentId(parentId));
    }

    @GetMapping(value = "detail")
    public ResponseData<RegionVO> detail(@NotEmpty(message = "主键不能为空，请检查参数id") String id){
        return new SuccessResponseData<>(regionService.getVO(id));
    }

    @OpLog(title = "新增",opType = OpLogAnnotionOpTypeEnum.INSERT)
    @SaCheckPermission("sys:region:add")
    @PostMapping(value = "add")
    public ResponseData add(@Validated @RequestBody RegionAddParam addParam){
        return ResponseData.bool(regionService.add(addParam));
    }

    @OpLog(title = "编辑",opType = OpLogAnnotionOpTypeEnum.UPDATE)
    @SaCheckPermission("sys:region:edit")
    @PostMapping(value = "edit")
    public ResponseData edit(@Validated @RequestBody RegionEditParam editParam){
        return ResponseData.bool(regionService.edit(editParam));
    }

    @OpLog(title = "删除",opType = OpLogAnnotionOpTypeEnum.DELETE)
    @SaCheckPermission("sys:region:delete")
    @GetMapping(value = "delete")
    public ResponseData delete(@NotEmpty(message = "主键不能为空，请检查参数id") String id){
        return ResponseData.bool(regionService.delete(id));
    }

    @OpLog(title = "修改状态",opType = OpLogAnnotionOpTypeEnum.UPDATE)
    @SaCheckPermission("sys:region:edit")
    @PostMapping(value = "changeStatus")
    public ResponseData changeStatus(@Validated @RequestBody RegionEnableParam enableParam){
        return ResponseData.bool(regionService.updateEnable(enableParam));
    }

}
