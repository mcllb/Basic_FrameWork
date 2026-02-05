package com.yunke.admin.modular.business.example.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yunke.admin.common.base.BaseController;
import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.framework.core.annotion.OpLog;
import com.yunke.admin.framework.core.annotion.OpLogHeader;
import com.yunke.admin.framework.core.enums.OpLogAnnotionOpTypeEnum;
import com.yunke.admin.framework.core.response.ResponseData;
import com.yunke.admin.framework.core.response.SuccessResponseData;
import com.yunke.admin.modular.business.example.model.param.StudentAddParam;
import com.yunke.admin.modular.business.example.model.param.StudentEditParam;
import com.yunke.admin.modular.business.example.model.param.StudentPageQueryParam;
import com.yunke.admin.modular.business.example.model.vo.StudentVO;
import com.yunke.admin.modular.business.example.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;

/**
 * @className StudentController
 * @description: example演示_前端控制器
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/16
 */
@OpLogHeader("example演示")
@RestController
@RequestMapping("/biz/ex/student/")
@RequiredArgsConstructor
@Validated
public class StudentController extends BaseController {

    private final StudentService studentService;

    @OpLog(title = "分页查询",opType = OpLogAnnotionOpTypeEnum.QUERY)
    @SaCheckPermission("biz:ex-student:list")
    @PostMapping(value = "page")
    public ResponseData<PageWrapper<StudentVO>> page(@RequestBody StudentPageQueryParam pageQueryParam){
        return new SuccessResponseData<>(studentService.pageVO(pageQueryParam));
    }

    @OpLog(title = "详情",opType = OpLogAnnotionOpTypeEnum.DETAIL)
    @PostMapping(value = "detail")
    public ResponseData<StudentVO> detail(@NotEmpty String id){
        return new SuccessResponseData<>(studentService.getVO(id));
    }

    @OpLog(title = "新增",opType = OpLogAnnotionOpTypeEnum.INSERT)
    @SaCheckPermission("biz:ex-student:add")
    @PostMapping(value = "add")
    public ResponseData add(@Validated @RequestBody StudentAddParam addParam){
        return ResponseData.bool(studentService.add(addParam));
    }

    @OpLog(title = "编辑",opType = OpLogAnnotionOpTypeEnum.UPDATE)
    @SaCheckPermission("biz:ex-student:edit")
    @PostMapping(value = "edit")
    public ResponseData edit(@Validated @RequestBody StudentEditParam editParam){
        return ResponseData.bool(studentService.edit(editParam));
    }

    @OpLog(title = "删除",opType = OpLogAnnotionOpTypeEnum.DELETE)
    @SaCheckPermission("biz:ex-student:delete")
    @PostMapping(value = "delete")
    public ResponseData delete(@NotEmpty String id){
        return ResponseData.bool(studentService.delete(id));
    }

}
