package com.yunke.admin.modular.system.shortcut.controller;

import com.yunke.admin.common.base.BaseController;
import com.yunke.admin.framework.core.annotion.OpLog;
import com.yunke.admin.framework.core.annotion.OpLogHeader;
import com.yunke.admin.framework.core.enums.OpLogAnnotionOpTypeEnum;
import com.yunke.admin.framework.core.response.ResponseData;
import com.yunke.admin.framework.core.response.SuccessResponseData;
import com.yunke.admin.modular.system.shortcut.model.param.*;
import com.yunke.admin.modular.system.shortcut.model.vo.ShortcutVO;
import com.yunke.admin.modular.system.shortcut.service.ShortcutService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;

/**
 * @className ShortcutController
 * @description: 快捷方式_前端控制器
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@OpLogHeader("快捷方式管理")
@RestController
@RequestMapping("/sys/shortcut/")
@RequiredArgsConstructor
@Validated
public class ShortcutController extends BaseController {

    private final ShortcutService shortcutService;

    @OpLog(title = "查询列表",opType = OpLogAnnotionOpTypeEnum.QUERY)
    //@SaCheckPermission("sys:shortcut:list")
    @PostMapping(value = "list")
    public ResponseData list(@RequestBody ShortcutQueryParam queryParam){
        return new SuccessResponseData<>(shortcutService.list(queryParam));
    }

    @GetMapping(value = "detail")
    public ResponseData<ShortcutVO> detail(@NotEmpty(message = "主键不能为空，请检查参数id") String id){
        return new SuccessResponseData<>(shortcutService.getVO(id));
    }

    @OpLog(title = "新增",opType = OpLogAnnotionOpTypeEnum.INSERT)
    //@SaCheckPermission("sys:shortcut:add")
    @PostMapping(value = "add")
    public ResponseData add(@Validated @RequestBody ShortcutAddParam addParam){
        return ResponseData.bool(shortcutService.add(addParam));
    }

    @OpLog(title = "编辑",opType = OpLogAnnotionOpTypeEnum.UPDATE)
    //@SaCheckPermission("sys:shortcut:edit")
    @PostMapping(value = "edit")
    public ResponseData edit(@Validated @RequestBody ShortcutEditParam editParam){
        return ResponseData.bool(shortcutService.edit(editParam));
    }

    @OpLog(title = "删除",opType = OpLogAnnotionOpTypeEnum.DELETE)
    //@SaCheckPermission("sys:shortcut:delete")
    @GetMapping(value = "delete")
    public ResponseData delete(@NotEmpty(message = "主键不能为空，请检查参数id") String id){
        return ResponseData.bool(shortcutService.delete(id));
    }

    @GetMapping(value = "permissonOptions")
    public ResponseData permissonOptions(@RequestParam(name = "excludeShoutCutRef",defaultValue = "false") boolean excludeShoutCutRef){
        return new SuccessResponseData<>(shortcutService.getUserPermissions(excludeShoutCutRef));
    }

    @OpLog(title = "修改状态",opType = OpLogAnnotionOpTypeEnum.UPDATE)
    //@SaCheckPermission("sys:shortcut:edit")
    @PostMapping(value = "updateStatus")
    public ResponseData updateStatus(@Validated @RequestBody ShortcutUpdateStatusParam updateStatusParam){
        return ResponseData.bool(shortcutService.updateStatus(updateStatusParam));
    }

    @OpLog(title = "修改排序",opType = OpLogAnnotionOpTypeEnum.UPDATE)
    //@SaCheckPermission("sys:shortcut:edit")
    @PostMapping(value = "updateSort")
    public ResponseData updateSort(@Validated @RequestBody ShortcutUpdateSortParam updateSortParam){
        return ResponseData.bool(shortcutService.updateSort(updateSortParam));
    }

}
