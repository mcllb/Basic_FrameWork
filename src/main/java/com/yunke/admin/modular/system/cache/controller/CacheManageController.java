package com.yunke.admin.modular.system.cache.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yunke.admin.common.base.BaseController;
import com.yunke.admin.framework.core.annotion.OpLog;
import com.yunke.admin.framework.core.annotion.OpLogHeader;
import com.yunke.admin.framework.core.enums.OpLogAnnotionOpTypeEnum;
import com.yunke.admin.framework.core.response.ResponseData;
import com.yunke.admin.framework.core.response.SuccessResponseData;
import com.yunke.admin.modular.system.cache.model.vo.CacheCardVO;
import com.yunke.admin.modular.system.cache.service.CacheManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @className CacheManageController
 * @description: 缓存管理_前端控制器
 * <p></p>
 * @version 1.0
 * @author tianlei
 * @date 2026/1/15
 */
@OpLogHeader("缓存管理")
@RestController
@RequestMapping("/sys/cache/")
@RequiredArgsConstructor
@Validated
public class CacheManageController extends BaseController {

    private final CacheManageService cacheManageService;

    @GetMapping(value = "cardList")
    public ResponseData cardList() {
        List<CacheCardVO> cardList = cacheManageService.getCardList();
        return new SuccessResponseData(cardList);
    }

    @OpLog(title = "刷新缓存",opType = OpLogAnnotionOpTypeEnum.OTHER)
    @SaCheckPermission("sys:cache:refresh")
    @GetMapping(value = "refresh")
    public ResponseData refresh(@NotEmpty(message = "缓存类型不能为空，请检查参数cacheType") String cacheType) {
        return ResponseData.bool(cacheManageService.refreshCache(cacheType));
    }

    @OpLog(title = "清除缓存",opType = OpLogAnnotionOpTypeEnum.OTHER)
    @SaCheckPermission("sys:cache:clear")
    @GetMapping(value = "clear")
    public ResponseData clear(@NotEmpty(message = "缓存类型不能为空，请检查参数cacheType") String cacheType) {
        return ResponseData.bool(cacheManageService.clearCache(cacheType));
    }

}
