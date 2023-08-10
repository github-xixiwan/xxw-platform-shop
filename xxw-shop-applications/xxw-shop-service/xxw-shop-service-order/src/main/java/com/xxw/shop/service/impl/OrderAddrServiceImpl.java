package com.xxw.shop.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xxw.shop.dto.OrderAddrQueryDTO;
import com.xxw.shop.entity.OrderAddr;
import com.xxw.shop.mapper.OrderAddrMapper;
import com.xxw.shop.service.OrderAddrService;
import org.springframework.stereotype.Service;

/**
 * 服务层实现。
 *
 * @author liaoxiting
 * @since 2023-08-10
 */
@Service
public class OrderAddrServiceImpl extends ServiceImpl<OrderAddrMapper, OrderAddr> implements OrderAddrService {

    @Override
    public Page<OrderAddr> page(OrderAddrQueryDTO dto) {
        return this.page(new Page<>(dto.getPageNumber(), dto.getPageSize()));
    }
}
