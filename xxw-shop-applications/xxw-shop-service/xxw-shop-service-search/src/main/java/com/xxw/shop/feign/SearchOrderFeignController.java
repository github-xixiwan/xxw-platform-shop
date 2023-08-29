package com.xxw.shop.feign;

import com.xxw.shop.api.search.dto.OrderSearchDTO;
import com.xxw.shop.api.search.feign.SearchOrderFeignClient;
import com.xxw.shop.api.search.vo.EsOrderVO;
import com.xxw.shop.api.search.vo.EsPageVO;
import com.xxw.shop.manager.OrderSearchManager;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品搜索feign连接
 */
@RestController
public class SearchOrderFeignController implements SearchOrderFeignClient {

    @Resource
    private OrderSearchManager orderSearchManager;

    @Override
    public ServerResponseEntity<EsPageVO<EsOrderVO>> getOrderPage(OrderSearchDTO dto) {
        return ServerResponseEntity.success(orderSearchManager.page(dto));
    }
}
