package com.yunke.admin.modular.home.controller;

import com.yunke.admin.common.base.BaseController;
import com.yunke.admin.modular.home.service.BizHomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bizHome/")
@RequiredArgsConstructor
public class BizHomeController extends BaseController {

    private final BizHomeService homeService;

}
