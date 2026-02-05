package com.yunke.admin.modular.system.permission.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yunke.admin.common.base.BaseController;
import com.yunke.admin.common.model.DetailQueryParam;
import com.yunke.admin.common.model.ElementUITreeNode;
import com.yunke.admin.common.model.SingleDeleteParam;
import com.yunke.admin.common.util.BeanUtil;
import com.yunke.admin.framework.core.annotion.OpLog;
import com.yunke.admin.framework.core.annotion.OpLogHeader;
import com.yunke.admin.framework.core.enums.OpLogAnnotionOpTypeEnum;
import com.yunke.admin.framework.core.response.ResponseData;
import com.yunke.admin.framework.core.response.SuccessResponseData;
import com.yunke.admin.modular.system.permission.model.entity.Permission;
import com.yunke.admin.modular.system.permission.model.param.*;
import com.yunke.admin.modular.system.permission.model.vo.PermissionListVO;
import com.yunke.admin.modular.system.permission.model.vo.PermissionVO;
import com.yunke.admin.modular.system.permission.model.vo.RoleGrantPermissionTreeVO;
import com.yunke.admin.modular.system.permission.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @className PermissionController
 * @description: 系统权限表_前端控制器
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@OpLogHeader("权限管理")
@RestController
@RequestMapping("/sys/permission/")
public class PermissionController extends BaseController {


    @Autowired
    private PermissionService permissionService;

    @OpLog(title = "权限列表",opType = OpLogAnnotionOpTypeEnum.QUERY)
    @SaCheckPermission("sys:permission:list")
    @PostMapping(value = "list")
    public ResponseData<List<PermissionListVO>> list(@RequestBody PermissionListQueryParam permissionListQueryParam){
        return ResponseData.success(permissionService.list(permissionListQueryParam));
    }

    @OpLog(title = "新增",opType = OpLogAnnotionOpTypeEnum.INSERT)
    @SaCheckPermission("sys:permission:add")
    @PostMapping(value = "add")
    public ResponseData add(@Validated @RequestBody PermissionAddParam permissionAddParam){
        return ResponseData.bool(permissionService.add(permissionAddParam));
    }

    @OpLog(title = "编辑",opType = OpLogAnnotionOpTypeEnum.UPDATE)
    @SaCheckPermission("sys:permission:edit")
    @PostMapping(value = "edit")
    public ResponseData edit(@Validated @RequestBody PermissionEditParam permissionEditParam){
        return ResponseData.bool(permissionService.edit(permissionEditParam));
    }

    @OpLog(title = "删除",opType = OpLogAnnotionOpTypeEnum.DELETE)
    @SaCheckPermission("sys:permission:delete")
    @PostMapping(value = "delete")
    public ResponseData delete(@Validated @RequestBody SingleDeleteParam singleDeleteParam){
        return ResponseData.bool(permissionService.delete(singleDeleteParam));
    }

    @GetMapping(value = "detail")
    public ResponseData<PermissionVO> detail(DetailQueryParam detailQueryParam){
        Permission permission = permissionService.getById(detailQueryParam.getId());
        PermissionVO permissionVO = new PermissionVO();
        BeanUtil.copyProperties(permission,permissionVO);
        return ResponseData.success(permissionVO);
    }

    @GetMapping(value = "treeSelect")
    public ResponseData<List<ElementUITreeNode>> treeSelect(){
        return new SuccessResponseData(permissionService.treeSelect());
    }

    @OpLog(title = "修改状态",opType = OpLogAnnotionOpTypeEnum.UPDATE)
    @SaCheckPermission("sys:permission:edit")
    @PostMapping(value = "changeStatus")
    public ResponseData changeStatus(@Validated @RequestBody PermissionUpdateStatusParam permissionUpdateStatusParam){
        return ResponseData.bool(permissionService.updatePermissionStatus(permissionUpdateStatusParam));
    }

    @OpLog(title = "修改排序",opType = OpLogAnnotionOpTypeEnum.UPDATE)
    @SaCheckPermission("sys:permission:edit")
    @PostMapping(value = "changeSort")
    public ResponseData changeSort(@Validated @RequestBody PermissionUpdateSortParam permissionUpdateSortParam){
        return ResponseData.bool(permissionService.updatePermissionSort(permissionUpdateSortParam));
    }

    @GetMapping(value = "roleGrantPermissionTree")
    public ResponseData<RoleGrantPermissionTreeVO> roleGrantPermissionTree(@Validated QueryRoleGrantPermissionParam queryRoleGrantPermissionParam){
        return new SuccessResponseData<>(permissionService.getRoleGrantPermissonTreeData(queryRoleGrantPermissionParam));
    }

}
