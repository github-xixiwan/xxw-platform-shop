package com.xxw.shop.controller;

import com.xingyuv.captcha.model.common.ResponseModel;
import com.xingyuv.captcha.model.vo.CaptchaVO;
import com.xingyuv.captcha.service.CaptchaService;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.web.util.IpHelper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ua/captcha")
public class CaptchaController {

    @Resource
    private CaptchaService captchaService;

    @PostMapping("/get")
    public ServerResponseEntity<ResponseModel> get(@RequestBody CaptchaVO data) {
        data.setBrowserInfo(IpHelper.getIpAddr());
        return ServerResponseEntity.success(captchaService.get(data));
    }

    @PostMapping("/check")
    public ServerResponseEntity<ResponseModel> check(@RequestBody CaptchaVO data) {
        data.setBrowserInfo(IpHelper.getIpAddr());
        return ServerResponseEntity.success(captchaService.check(data));
    }
}
