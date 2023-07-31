package com.xxw.shop.api.platform.user.api;

import com.xxw.shop.api.platform.user.dto.OrderDTO;
import com.xxw.shop.module.util.rest.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order", path = "/order")
public interface OrderApi {

    @PostMapping("/buyOrder")
    Result<String> buyOrder(@RequestBody OrderDTO dto);

}
