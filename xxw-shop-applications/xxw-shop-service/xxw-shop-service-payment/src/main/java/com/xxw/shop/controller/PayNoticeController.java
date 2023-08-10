package com.xxw.shop.controller;

import cn.hutool.core.util.StrUtil;
import com.xxw.shop.entity.PayInfo;
import com.xxw.shop.service.PayInfoService;
import com.xxw.shop.vo.PayInfoResultVO;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Hidden
@RestController
@RequestMapping("/notice/pay")
public class PayNoticeController {

    @Resource
    private PayInfoService payInfoService;

    /**
     * 支付异步回调
     */
    @RequestMapping("/order")
    public ResponseEntity<String> submit(Long payId) {
        PayInfo payInfo = payInfoService.getById(payId);
        String[] orderIdStrArr = payInfo.getOrderIds().split(StrUtil.COMMA);
        List<Long> orderIdList = new ArrayList<>();
        for (String s : orderIdStrArr) {
            orderIdList.add(Long.valueOf(s));
        }
        PayInfoResultVO payInfoResult = new PayInfoResultVO();
        payInfoResult.setPayId(payId);
        payInfoResult.setBizPayNo(payInfo.getBizPayNo());
        payInfoResult.setCallbackContent(payInfo.getCallbackContent());
        // 支付成功
        payInfoService.paySuccess(payInfoResult, orderIdList);
        return ResponseEntity.ok("");
    }
}
