package com.yunke.admin.modular.system.issue.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yunke.admin.common.base.BaseController;
import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.framework.core.annotion.OpLog;
import com.yunke.admin.framework.core.annotion.OpLogHeader;
import com.yunke.admin.framework.core.enums.OpLogAnnotionOpTypeEnum;
import com.yunke.admin.framework.core.response.ResponseData;
import com.yunke.admin.framework.core.response.SuccessResponseData;
import com.yunke.admin.modular.system.issue.model.param.IssueHistoryAddParam;
import com.yunke.admin.modular.system.issue.model.param.IssueHistoryEditParam;
import com.yunke.admin.modular.system.issue.model.param.IssueHistoryPageQueryParam;
import com.yunke.admin.modular.system.issue.model.vo.IssueHistoryVO;
import com.yunke.admin.modular.system.issue.service.IssueHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;

/**
 * @className IssueHistoryController
 * @description: 缺陷管理历史_前端控制器
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@OpLogHeader("缺陷管理历史")
@RestController
@RequestMapping("/sys/issuehistory/")
@RequiredArgsConstructor
@Validated
public class IssueHistoryController extends BaseController {

    private final IssueHistoryService issueHistoryService;

    @OpLog(title = "分页查询",opType = OpLogAnnotionOpTypeEnum.QUERY)
    @SaCheckPermission("sys:issuehistory:page")
    @PostMapping(value = "page")
    public ResponseData<PageWrapper<IssueHistoryVO>> page(@RequestBody IssueHistoryPageQueryParam pageQueryParam){
        return new SuccessResponseData<>(issueHistoryService.selectPageVO(pageQueryParam));
    }

    @GetMapping(value = "list")
    public ResponseData<IssueHistoryVO> list(@NotEmpty(message = "缺陷主键不能为空，请检查参数issueId") String issueId){
        return new SuccessResponseData<>(issueHistoryService.listByIssueId(issueId));
    }

    @GetMapping(value = "detail")
    public ResponseData<IssueHistoryVO> detail(@NotEmpty String id){
        return new SuccessResponseData<>(issueHistoryService.selectVO(id));
    }

    @OpLog(title = "新增",opType = OpLogAnnotionOpTypeEnum.INSERT)
    @SaCheckPermission("sys:issuehistory:add")
    @PostMapping(value = "add")
    public ResponseData add(@Validated @RequestBody IssueHistoryAddParam addParam){
        return ResponseData.bool(issueHistoryService.add(addParam));
    }

    @OpLog(title = "编辑",opType = OpLogAnnotionOpTypeEnum.UPDATE)
    @SaCheckPermission("sys:issuehistory:edit")
    @PostMapping(value = "edit")
    public ResponseData edit(@Validated @RequestBody IssueHistoryEditParam editParam){
        return ResponseData.bool(issueHistoryService.edit(editParam));
    }

    @OpLog(title = "删除",opType = OpLogAnnotionOpTypeEnum.DELETE)
    @SaCheckPermission("sys:issuehistory:delete")
    @GetMapping(value = "delete")
    public ResponseData delete(@NotEmpty String id){
        return ResponseData.bool(issueHistoryService.delete(id));
    }

}
