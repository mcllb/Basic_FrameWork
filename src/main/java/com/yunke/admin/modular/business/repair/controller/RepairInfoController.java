package com.yunke.admin.modular.business.repair.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yunke.admin.common.base.BaseController;
import com.yunke.admin.common.model.PageWrapper;
import com.yunke.admin.framework.core.annotion.OpLog;
import com.yunke.admin.framework.core.annotion.OpLogHeader;
import com.yunke.admin.framework.core.enums.OpLogAnnotionOpTypeEnum;
import com.yunke.admin.framework.core.response.ResponseData;
import com.yunke.admin.framework.core.response.SuccessResponseData;
import com.yunke.admin.modular.business.repair.model.param.RepairInfoAddParam;
import com.yunke.admin.modular.business.repair.model.param.RepairInfoEditParam;
import com.yunke.admin.modular.business.repair.model.param.RepairInfoPageQueryParam;
import com.yunke.admin.modular.business.repair.model.vo.RepairInfoVO;
import com.yunke.admin.modular.business.repair.service.RepairInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;

/**
 * @className RepairInfoController
 * @description:  前端控制器
 * <p></p>
 * @version 1.0
 * @author mcllb
 * @date 2026-01-22
 */
@OpLogHeader("报修信息管理")
@RestController
@RequestMapping("/biz/repair-info/")
@RequiredArgsConstructor
@Validated
public class RepairInfoController extends BaseController {

    private final RepairInfoService repairInfoService;

        @OpLog(title = "分页查询", opType = OpLogAnnotionOpTypeEnum.QUERY)
        @SaCheckPermission("biz:repair-info:list")
        @PostMapping(value = "page")
        public ResponseData<PageWrapper<RepairInfoVO>> page(@RequestBody RepairInfoPageQueryParam pageQueryParam) {
            return new SuccessResponseData<>(repairInfoService.pageVO(pageQueryParam));
        }

        @OpLog(title = "详情", opType = OpLogAnnotionOpTypeEnum.DETAIL)
        @GetMapping(value = "detail")
        public ResponseData<RepairInfoVO> detail(@NotEmpty String id) {
            return new SuccessResponseData<>(repairInfoService.getVO(id));
        }

        /*@OpLog(title = "新增", opType = OpLogAnnotionOpTypeEnum.INSERT)
        @SaCheckPermission("biz:repair-info:add")
        @PostMapping(value = "add")
        public ResponseData add(@Validated @RequestBody RepairInfoAddParam addParam) {
            return ResponseData.bool(repairInfoService.add(addParam));
        }*/

        @OpLog(title = "编辑", opType = OpLogAnnotionOpTypeEnum.UPDATE)
        @SaCheckPermission("biz:repair-info:edit")
        @PostMapping(value = "edit")
        public ResponseData edit(@Validated @RequestBody RepairInfoEditParam editParam) {
            return repairInfoService.edit(editParam);
        }

        @OpLog(title = "删除", opType = OpLogAnnotionOpTypeEnum.DELETE)
        @SaCheckPermission("biz:repair-info:delete")
        @GetMapping(value = "delete")
        public ResponseData delete(@NotEmpty String id) {
            return ResponseData.bool(repairInfoService.delete(id));
        }

        @OpLog(title = "冻结", opType = OpLogAnnotionOpTypeEnum.DELETE)
        @SaCheckPermission("biz:repair-info:freeze")
        @GetMapping(value = "freeze")
        public ResponseData freeze(@NotEmpty String id) {
            return ResponseData.bool(repairInfoService.freeze(id));
        }

}