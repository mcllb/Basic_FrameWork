package com.yunke.admin.modular.system.profile.controller;

import com.yunke.admin.common.base.BaseController;
import com.yunke.admin.framework.core.annotion.OpLog;
import com.yunke.admin.framework.core.annotion.OpLogHeader;
import com.yunke.admin.framework.core.enums.OpLogAnnotionOpTypeEnum;
import com.yunke.admin.framework.core.response.ResponseData;
import com.yunke.admin.framework.core.response.SuccessResponseData;
import com.yunke.admin.modular.system.profile.model.param.UpdateUserInfoParam;
import com.yunke.admin.modular.system.profile.service.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@OpLogHeader("用户中心")
@RestController
@Validated
@RequestMapping("/sys/profile/")
@AllArgsConstructor
public class ProfileController extends BaseController {

    private final ProfileService profileService;

    @GetMapping(value = "userInfo")
    public ResponseData userInfo(){
        return new SuccessResponseData(profileService.getCurrentUserInfo());
    }

    @OpLog(title = "修改个人信息",opType = OpLogAnnotionOpTypeEnum.UPDATE)
    @PostMapping(value = "updateUserInfo")
    public ResponseData updateUserInfo(@Validated @RequestBody UpdateUserInfoParam updateUserInfoParam){
        profileService.updateUserInfo(updateUserInfoParam);
        return ResponseData.success();
    }

}
