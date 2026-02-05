package com.yunke.admin.modular.system.user.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.hutool.core.codec.Base64;
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
import com.yunke.admin.modular.system.user.model.entity.User;
import com.yunke.admin.modular.system.user.model.param.*;
import com.yunke.admin.modular.system.user.model.vo.UserPageVO;
import com.yunke.admin.modular.system.user.model.vo.UserVO;
import com.yunke.admin.modular.system.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @className UserController
 * @description: 系统用户表_前端控制器
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@OpLogHeader("用户管理")
@RestController
@Validated
@RequestMapping("/sys/user/")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @OpLog(title = "分页查询",opType = OpLogAnnotionOpTypeEnum.QUERY)
    @SaCheckPermission("sys:user:list")
    @PostMapping(value = "page")
    public ResponseData<PageWrapper<UserPageVO>> page(@RequestBody UserPageQyeryParam userPageQyeryParam){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 根据用户账号模糊查询
        if(StrUtil.isNotEmpty(userPageQyeryParam.getAccount())){
            lambdaQueryWrapper.like(User::getAccount, userPageQyeryParam.getAccount());
        }
        // 根据用户名称模糊查询
        if(StrUtil.isNotEmpty(userPageQyeryParam.getUsername())){
            lambdaQueryWrapper.like(User::getUserName,userPageQyeryParam.getUsername());
        }
        // 根据部门id查询
        if(StrUtil.isNotEmpty(userPageQyeryParam.getDeptId())){
            lambdaQueryWrapper.eq(User::getDeptId, userPageQyeryParam.getDeptId());
        }
        lambdaQueryWrapper.orderByAsc(User::getSort);
        Page<User> pageData = userService.page(userPageQyeryParam.page(), lambdaQueryWrapper);
        List<User> records = pageData.getRecords();
        List<UserPageVO> userPageVOList = BeanUtil.copyListProperties(records, UserPageVO::new);
        // 填充部门名称
        userService.fillDeptName(userPageVOList);
        PageWrapper<UserPageVO> pageWrapper = new PageWrapper<UserPageVO>(userPageVOList,pageData.getTotal(),pageData.getSize(),pageData.getCurrent());
        return new SuccessResponseData(pageWrapper);
    }

    @OpLog(title = "新增",opType = OpLogAnnotionOpTypeEnum.INSERT)
    @SaCheckPermission("sys:user:add")
    @PostMapping(value = "add")
    public ResponseData add(@Validated @RequestBody UserAddParam userAddParam){
        return ResponseData.bool(userService.add(userAddParam));
    }

    @OpLog(title = "编辑",opType = OpLogAnnotionOpTypeEnum.UPDATE)
    @SaCheckPermission("sys:user:edit")
    @PostMapping(value = "edit")
    public ResponseData edit(@Validated @RequestBody UserEditParam userEditParam){
        return ResponseData.bool(userService.edit(userEditParam));
    }

    @OpLog(title = "删除",opType = OpLogAnnotionOpTypeEnum.DELETE)
    @SaCheckPermission("sys:user:delete")
    @PostMapping(value = "delete")
    public ResponseData delete(@Validated @RequestBody SingleDeleteParam singleDeleteParam){
        return ResponseData.bool(userService.delete(singleDeleteParam));
    }

    @OpLog(title = "授权角色",opType = OpLogAnnotionOpTypeEnum.OTHER)
    @SaCheckPermission("sys:user:grantRole")
    @PostMapping(value = "grantRole")
    public ResponseData grantRole(@Validated @RequestBody UserGrantRoleParam userGrantRoleParam){
        return ResponseData.bool(userService.grantRole(userGrantRoleParam));
    }

    @GetMapping(value = "detail")
    public ResponseData<UserVO> detail(DetailQueryParam detailQueryParam){
        User user = userService.getById(detailQueryParam.getId());
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user,userVO);
        return new SuccessResponseData(userVO);
    }

    @OpLog(title = "修改个人信息",opType = OpLogAnnotionOpTypeEnum.UPDATE)
    @PostMapping(value = "updateInfo")
    public ResponseData updateInfo(@Validated @RequestBody UserUpdateInfoParam userUpdateInfoParam){
        return ResponseData.bool(userService.updateInfo(userUpdateInfoParam));
    }

    @OpLog(title = "用户修改密码",opType = OpLogAnnotionOpTypeEnum.UPDATE)
    @PostMapping(value = "updatePassword")
    public ResponseData updatePassword(@Validated @RequestBody UserUpdatePasswordParam userUpdatePasswordParam){
        return ResponseData.bool(userService.updatePassword(userUpdatePasswordParam));
    }

    @OpLog(title = "重置用户密码",opType = OpLogAnnotionOpTypeEnum.UPDATE)
    @SaCheckPermission("sys:user:resetPassword")
    @PostMapping(value = "resetPassword")
    public ResponseData resetPassword(@Validated @RequestBody UserResetPasswordParam userResetPasswordParam){
        return ResponseData.bool(userService.resetPassword(userResetPasswordParam));
    }

    @OpLog(title = "重置全部用户密码",opType = OpLogAnnotionOpTypeEnum.UPDATE)
    @SaCheckPermission("sys:user:resetPasswordAll")
    @GetMapping(value = "resetPasswordAll")
    public ResponseData resetPasswordAll(){
        return ResponseData.bool(userService.resetPasswordAll());
    }

    @OpLog(title = "修改状态",opType = OpLogAnnotionOpTypeEnum.UPDATE)
    @SaCheckPermission("sys:user:edit")
    @PostMapping(value = "changeStatus")
    public ResponseData changeStatus(@Validated @RequestBody UserUpdateStatusParam userUpdateStatusParam){
        return ResponseData.bool(userService.updateUserStatus(userUpdateStatusParam));
    }

    @OpLog(title = "查看密码",opType = OpLogAnnotionOpTypeEnum.OTHER)
    @SaCheckRole("admin")
    @GetMapping(value = "viewPassword")
    public ResponseData viewPassword(@NotEmpty(message = "主键不能为空，请检查参数id") String id){
        String ret = Base64.encode(Base64.encode(userService.getPassword(id)));
        return new SuccessResponseData(ret);
    }

    @GetMapping(value = "userInfo")
    public ResponseData userInfo(){
        return new SuccessResponseData(userService.getCurrentUserInfo());
    }

    @GetMapping(value = "orgTree")
    public ResponseData orgTree(){
        return new SuccessResponseData(userService.getOrgTree());
    }

    @OpLog(title = "用户导入",opType = OpLogAnnotionOpTypeEnum.IMPORT)
    @SaCheckPermission("sys:user:import")
    @PostMapping(value = "import")
    public ResponseData importUser(@Size(min = 1,message = "导入用户不能为空") @RequestBody List<UserImportParam> params){
        userService.importUsers(params);
        return ResponseData.success();
    }





}
