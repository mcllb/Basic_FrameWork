package com.yunke.admin.modular.common.controller;

import cn.hutool.core.util.IdUtil;
import com.yunke.admin.framework.cache.MockLoginTokenCache;
import com.yunke.admin.framework.core.response.ResponseData;
import com.yunke.admin.framework.core.response.SuccessResponseData;
import com.yunke.admin.modular.common.model.vo.MockLoginTokenVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class TokenController {

    private final MockLoginTokenCache mockLoginTokenCache;

    @GetMapping("/mockLoginToken")
    public ResponseData<MockLoginTokenVO> mockLoginToken(@NotEmpty(message = "模拟登陆账号不能为空，请检查参数account") String account){
        String token = IdUtil.fastUUID();
        // 将token存入缓存
        mockLoginTokenCache.put(token,account);
        log.debug(">>> 生成模拟登陆token，account:{},token:{}",account,token);
        MockLoginTokenVO mockLoginTokenVO = new MockLoginTokenVO();
        mockLoginTokenVO.setToken(token);
        mockLoginTokenVO.setAccount(account);
        return new SuccessResponseData<>(mockLoginTokenVO);
    }


}
