package com.yunke.admin.modular.system.issue.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yunke.admin.common.base.BaseController;
import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.framework.core.annotion.OpLog;
import com.yunke.admin.framework.core.annotion.OpLogHeader;
import com.yunke.admin.framework.core.enums.OpLogAnnotionOpTypeEnum;
import com.yunke.admin.framework.core.response.ResponseData;
import com.yunke.admin.framework.core.response.SuccessResponseData;
import com.yunke.admin.modular.system.issue.model.param.*;
import com.yunke.admin.modular.system.issue.model.vo.IssueVO;
import com.yunke.admin.modular.system.issue.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;

/**
 * @className IssueController
 * @description: 缺陷管理_前端控制器
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@OpLogHeader("缺陷管理")
@RestController
@RequestMapping("/sys/issue/")
@RequiredArgsConstructor
@Validated
public class IssueController extends BaseController {

    private final IssueService issueService;

    @OpLog(title = "分页查询",opType = OpLogAnnotionOpTypeEnum.QUERY)
    @SaCheckPermission("sys:issue:page")
    @PostMapping(value = "page")
    public ResponseData<PageWrapper<IssueVO>> page(@RequestBody IssuePageQueryParam pageQueryParam){
        return new SuccessResponseData<>(issueService.selectPageVO(pageQueryParam));
    }

    @GetMapping(value = "detail")
    public ResponseData<IssueVO> detail(@NotEmpty(message = "主键不能为空，请检查参数id") String id){
        return new SuccessResponseData<>(issueService.selectVO(id));
    }

    @OpLog(title = "新增",opType = OpLogAnnotionOpTypeEnum.INSERT,isSaveRequestParam = false)
    @SaCheckPermission("sys:issue:add")
    @PostMapping(value = "add")
    public ResponseData add(@Validated @RequestBody IssueAddParam addParam){
        return ResponseData.bool(issueService.add(addParam));
    }

    @OpLog(title = "编辑",opType = OpLogAnnotionOpTypeEnum.UPDATE,isSaveRequestParam = false)
    @SaCheckPermission("sys:issue:edit")
    @PostMapping(value = "edit")
    public ResponseData edit(@Validated @RequestBody IssueEditParam editParam){
        return ResponseData.bool(issueService.edit(editParam));
    }

    @OpLog(title = "删除",opType = OpLogAnnotionOpTypeEnum.DELETE)
    @SaCheckPermission("sys:issue:delete")
    @GetMapping(value = "delete")
    public ResponseData delete(@NotEmpty(message = "主键不能为空，请检查参数id") String id){
        return ResponseData.bool(issueService.delete(id));
    }

    @OpLog(title = "开始修复",opType = OpLogAnnotionOpTypeEnum.OTHER)
    @SaCheckPermission("sys:issue:repair")
    @GetMapping(value = "repairing")
    public ResponseData repairing(@NotEmpty(message = "主键不能为空，请检查参数id") String id){
        return ResponseData.bool(issueService.startRepair(id));
    }

    @OpLog(title = "修复完成",opType = OpLogAnnotionOpTypeEnum.OTHER)
    @SaCheckPermission("sys:issue:repair")
    @PostMapping(value = "repaired")
    public ResponseData repaired(@Validated @RequestBody IssueFinishRepairParam finishRepairParam){
        return ResponseData.bool(issueService.finishRepair(finishRepairParam));
    }

    @OpLog(title = "重新打开",opType = OpLogAnnotionOpTypeEnum.OTHER)
    @SaCheckPermission("sys:issue:close")
    @PostMapping(value = "reopen")
    public ResponseData reopen(@Validated @RequestBody IssueReopenParam reopenParam){
        return ResponseData.bool(issueService.reopen(reopenParam));
    }

    @OpLog(title = "关闭",opType = OpLogAnnotionOpTypeEnum.OTHER)
    @SaCheckPermission("sys:issue:close")
    @PostMapping(value = "close")
    public ResponseData close(@Validated @RequestBody IssueCloseParam closeParam){
        return ResponseData.bool(issueService.close(closeParam));
    }

}
