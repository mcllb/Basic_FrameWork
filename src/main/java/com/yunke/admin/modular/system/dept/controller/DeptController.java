package com.yunke.admin.modular.system.dept.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yunke.admin.common.base.BaseController;
import com.yunke.admin.common.constant.SymbolConstant;
import com.yunke.admin.common.model.DetailQueryParam;
import com.yunke.admin.common.model.ElementUITreeNode;
import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.common.model.SingleDeleteParam;
import com.yunke.admin.common.util.BeanUtil;
import com.yunke.admin.framework.core.annotion.OpLog;
import com.yunke.admin.framework.core.annotion.OpLogHeader;
import com.yunke.admin.framework.core.enums.OpLogAnnotionOpTypeEnum;
import com.yunke.admin.framework.core.response.ResponseData;
import com.yunke.admin.framework.core.response.SuccessResponseData;
import com.yunke.admin.modular.system.dept.model.entity.Dept;
import com.yunke.admin.modular.system.dept.model.param.*;
import com.yunke.admin.modular.system.dept.model.vo.DeptPageVO;
import com.yunke.admin.modular.system.dept.model.vo.DeptVO;
import com.yunke.admin.modular.system.dept.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @className DeptController
 * @description: 系统部门表_前端控制器
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@OpLogHeader("部门管理")
@RestController
@RequestMapping("/sys/dept/")
public class DeptController extends BaseController {

    @Autowired
    private DeptService deptService;

    @OpLog(title = "分页查询",opType = OpLogAnnotionOpTypeEnum.QUERY)
    @SaCheckPermission("sys:dept:list")
    @PostMapping(value = "page")
    public ResponseData<PageWrapper<DeptPageVO>> page(@RequestBody DeptPageQueryParam deptPageQueryParam){
        LambdaQueryWrapper<Dept> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 根据上级部门id查询子部门，包含上级部门
        if(StrUtil.isNotEmpty(deptPageQueryParam.getDeptId())){
            Set<String> deptIds = CollUtil.newHashSet();
            deptIds.add(deptPageQueryParam.getDeptId());
            lambdaQueryWrapper.like(Dept::getParentIds,deptPageQueryParam.getDeptId()+ SymbolConstant.HYPHEN);
            List<Dept> depts = deptService.list(lambdaQueryWrapper);
            if(depts != null && depts.size() > 0){
                Set<String> colls =depts.stream().map(Dept::getId).collect(Collectors.toSet());
                deptIds.addAll(colls);
            }
            lambdaQueryWrapper.clear();
            lambdaQueryWrapper.in(Dept::getId, deptIds);
        }
        // 根据部门名称模糊查询
        if(StrUtil.isNotEmpty(deptPageQueryParam.getDeptName())){
            lambdaQueryWrapper.like(Dept::getDeptName,deptPageQueryParam.getDeptName());
        }
        lambdaQueryWrapper.orderByAsc(Dept::getSort);
        IPage<Dept> pageData = deptService.page(deptPageQueryParam.page(), lambdaQueryWrapper);
        List<Dept> records = pageData.getRecords();
        List<DeptPageVO> deptPageVOList = BeanUtil.copyListProperties(records, DeptPageVO::new);
        PageWrapper<DeptPageVO> pageWrapper = new PageWrapper<>(deptPageVOList, pageData.getTotal(), pageData.getSize(), pageData.getCurrent());
        return new SuccessResponseData(pageWrapper);
    }

    @PostMapping(value = "list")
    public ResponseData<DeptPageVO> list(@RequestBody DeptQueryParam deptQueryParam){
        LambdaQueryWrapper<Dept> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 根据部门名称模糊查询
        if(StrUtil.isNotEmpty(deptQueryParam.getDeptName())){
            lambdaQueryWrapper.like(Dept::getDeptName,deptQueryParam.getDeptName());
        }
        List<Dept> list = deptService.list(lambdaQueryWrapper);
        List<DeptPageVO> pageVOs = BeanUtil.copyListProperties(list, DeptPageVO::new);
        return new SuccessResponseData(pageVOs);
    }

    @OpLog(title = "新增",opType = OpLogAnnotionOpTypeEnum.INSERT)
    @SaCheckPermission("sys:dept:add")
    @PostMapping(value = "add")
    public ResponseData add(@Validated @RequestBody DeptAddParam deptAddParam){
        return ResponseData.bool(deptService.add(deptAddParam));
    }

    @OpLog(title = "编辑",opType = OpLogAnnotionOpTypeEnum.UPDATE)
    @SaCheckPermission("sys:dept:edit")
    @PostMapping(value = "edit")
    public ResponseData edit(@Validated @RequestBody DeptEditParam deptEditParam){
        return ResponseData.bool(deptService.edit(deptEditParam));
    }

    @OpLog(title = "删除",opType = OpLogAnnotionOpTypeEnum.DELETE)
    @SaCheckPermission("sys:dept:delete")
    @PostMapping(value = "delete")
    public ResponseData delete(@Validated @RequestBody SingleDeleteParam singleDeleteParam){
        return ResponseData.bool(deptService.delete(singleDeleteParam));
    }

    @GetMapping(value = "tree")
    public ResponseData tree(){
        return new SuccessResponseData(deptService.tree());
    }

    @GetMapping(value = "treeSelect")
    public ResponseData<List<ElementUITreeNode>> treeSelect(){
        return new SuccessResponseData(deptService.treeSelect());
    }

    @GetMapping(value = "detail")
    public ResponseData<DeptVO> detail(DetailQueryParam detailQueryParam){
        Dept dept = deptService.getById(detailQueryParam.getId());
        DeptVO deptVO = new DeptVO();
        BeanUtil.copyProperties(dept,deptVO);
        return new SuccessResponseData(deptVO);
    }

    @OpLog(title = "修改状态",opType = OpLogAnnotionOpTypeEnum.UPDATE)
    @SaCheckPermission("sys:dept:edit")
    @PostMapping(value = "changeStatus")
    public ResponseData changeStatus(@Validated @RequestBody DeptUpdateStatusParam deptUpdateStatusParam){
        return ResponseData.bool(deptService.updateDeptStatus(deptUpdateStatusParam));
    }


}
