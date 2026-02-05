package com.yunke.admin.modular.system.role.controller;


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
import com.yunke.admin.modular.system.role.model.entity.Role;
import com.yunke.admin.modular.system.role.model.param.*;
import com.yunke.admin.modular.system.role.model.vo.RoleGrantUserTreeVO;
import com.yunke.admin.modular.system.role.model.vo.RolePageVO;
import com.yunke.admin.modular.system.role.model.vo.RoleVO;
import com.yunke.admin.modular.system.role.model.vo.UserGrantRoleListVO;
import com.yunke.admin.modular.system.role.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @className RoleController
 * @description: 系统角色表_前端控制器
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@OpLogHeader("角色管理")
@RestController
@RequestMapping("/sys/role/")
public class RoleController extends BaseController {
    @Autowired
    private RoleService roleService;

    @OpLog(title = "分页查询",opType = OpLogAnnotionOpTypeEnum.QUERY)
    @SaCheckPermission("sys:role:list")
    @PostMapping(value = "page")
    public ResponseData<PageWrapper<RolePageVO>> page(@RequestBody RolePageQueryParam rolePageQueryParam){
        LambdaQueryWrapper<Role> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //lambdaQueryWrapper.select(Role::getId,Role::getRoleName,Role::getRoleCode,Role::getStatus);
        // 根据角色名称模糊查询
        if(StrUtil.isNotEmpty(rolePageQueryParam.getRoleName())){
            lambdaQueryWrapper.like(Role::getRoleName,rolePageQueryParam.getRoleName());
        }
        // 根据角色编码模糊查询
        if(StrUtil.isNotEmpty(rolePageQueryParam.getRoleCode())){
            lambdaQueryWrapper.like(Role::getRoleCode, rolePageQueryParam.getRoleCode());
        }
        // 查询条件：状态
        if(StrUtil.isNotEmpty(rolePageQueryParam.getStatus())){
            lambdaQueryWrapper.eq(Role::getStatus, rolePageQueryParam.getStatus());
        }
        lambdaQueryWrapper.orderByAsc(Role::getSort);
        Page<Role> pageData = roleService.page(rolePageQueryParam.page(), lambdaQueryWrapper);
        List<Role> records = pageData.getRecords();
        List<RolePageVO> pageVOList = BeanUtil.copyListProperties(records, RolePageVO::new);
        PageWrapper<RolePageVO> pageWrapper = new PageWrapper<>(pageVOList, pageData.getTotal(), pageData.getSize(), pageData.getCurrent());
        return new SuccessResponseData(pageWrapper);
    }

    @OpLog(title = "新增",opType = OpLogAnnotionOpTypeEnum.INSERT)
    @SaCheckPermission("sys:role:add")
    @PostMapping(value = "add")
    public ResponseData add(@Validated @RequestBody RoleAddParam roleAddParam){
        return ResponseData.bool(roleService.add(roleAddParam));
    }

    @OpLog(title = "编辑",opType = OpLogAnnotionOpTypeEnum.UPDATE)
    @SaCheckPermission("sys:role:edit")
    @PostMapping(value = "edit")
    public ResponseData edit(@Validated @RequestBody RoleEditParam roleEditParam){
        return ResponseData.bool(roleService.edit(roleEditParam));
    }

    @OpLog(title = "删除",opType = OpLogAnnotionOpTypeEnum.DELETE)
    @SaCheckPermission("sys:role:delete")
    @PostMapping(value = "delete")
    public ResponseData delete(@Validated @RequestBody SingleDeleteParam singleDeleteParam){
        return ResponseData.bool(roleService.delete(singleDeleteParam));
    }

    @OpLog(title = "授权菜单",opType = OpLogAnnotionOpTypeEnum.OTHER)
    @SaCheckPermission("sys:role:grantPermission")
    @PostMapping(value = "grantPermission")
    public ResponseData grantPermission(@Validated @RequestBody RoleGrantPermissionParam roleGrantPermissionParam){
        return ResponseData.bool(roleService.grantPermission(roleGrantPermissionParam));
    }

    @GetMapping(value = "detail")
    public ResponseData<RoleVO> detail(DetailQueryParam detailQueryParam){
        Role role = roleService.getById(detailQueryParam.getId());
        RoleVO roleVO = new RoleVO();
        BeanUtil.copyProperties(role, roleVO);
        return new SuccessResponseData(roleVO);
    }

    @OpLog(title = "修改状态",opType = OpLogAnnotionOpTypeEnum.UPDATE)
    @SaCheckPermission("sys:role:edit")
    @PostMapping(value = "changeStatus")
    public ResponseData changeStatus(@Validated @RequestBody RoleUpdateStatusParam roleUpdateStatusParam){
        return ResponseData.bool(roleService.updateRoleStatus(roleUpdateStatusParam));
    }

    @GetMapping(value = "userGrantRoleList")
    public ResponseData<List<UserGrantRoleListVO>> userGrantRoleList(@Validated QueryUserGrantRoleListParam queryUserGrantRoleListParam){
        return new SuccessResponseData<>(roleService.getUserGrantRoleList(queryUserGrantRoleListParam));
    }

    @GetMapping(value = "roleGrantUserTreeData")
    public ResponseData<RoleGrantUserTreeVO> roleGrantUserTreeData(@NotEmpty(message = "角色id不能为空，请检查参数roleId") String roleId){
        return new SuccessResponseData(roleService.getGrantUserTreeDataByRoleId(roleId));
    }

    @OpLog(title = "授权用户",opType = OpLogAnnotionOpTypeEnum.OTHER)
    @SaCheckPermission("sys:role:grantUser")
    @PostMapping(value = "grantUser")
    public ResponseData grantUser(@Validated @RequestBody RoleGrantUserParam roleGrantUserParam){
        return ResponseData.bool(roleService.grantUser(roleGrantUserParam));
    }

}
