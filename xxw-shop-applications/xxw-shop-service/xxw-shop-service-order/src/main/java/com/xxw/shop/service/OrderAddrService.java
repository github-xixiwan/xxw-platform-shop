package com.xxw.shop.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.xxw.shop.dto.OrderAddrQueryDTO;
import com.xxw.shop.entity.OrderAddr;

/**
 * 服务层。
 *
 * @author liaoxiting
 * @since 2023-08-10
 */
public interface OrderAddrService extends IService<OrderAddr> {

    Page<OrderAddr> page(OrderAddrQueryDTO dto);
}
