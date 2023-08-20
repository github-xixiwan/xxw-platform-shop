package com.xxw.shop.api.search.feign;

import com.xxw.shop.api.search.dto.OrderSearchDTO;
import com.xxw.shop.api.search.vo.EsPageVO;
import com.xxw.shop.module.common.bo.EsOrderBO;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.web.feign.FeignInsideAuthConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 订单搜索
 */
@FeignClient(value = "shop-search", contextId = "searchOrderFeign")
public interface SearchOrderFeignClient {

    /**
     * 订单搜索
     *
     * @param dto 订单搜索参数
     * @return 订单列表
     */
    @PutMapping(value = FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX + "/insider/searchOrder/getOrderPage")
    ServerResponseEntity<EsPageVO<EsOrderBO>> getOrderPage(@RequestBody OrderSearchDTO dto);

}
