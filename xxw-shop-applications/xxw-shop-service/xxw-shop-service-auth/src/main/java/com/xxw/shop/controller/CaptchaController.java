package com.xxw.shop.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ua/captcha")
@Tag(name = "验证码")
public class CaptchaController {

//    @Resource
//    private CaptchaService captchaService;
//
//    @PostMapping({"/get"})
//    public ServerResponseEntity<ResponseModel> get(@RequestBody CaptchaVO captchaVO) {
//        return ServerResponseEntity.success(captchaService.get(captchaVO));
//    }
//
//    @PostMapping({"/check"})
//    public ServerResponseEntity<ResponseModel> check(@RequestBody CaptchaVO captchaVO) {
//        return ServerResponseEntity.success(captchaService.check(captchaVO));
//    }

}
