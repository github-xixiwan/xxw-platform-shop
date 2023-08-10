package com.xxw.shop.controller.business;

import com.xxw.shop.api.order.constant.OrderStatus;
import com.xxw.shop.dto.DeliveryOrderDTO;
import com.xxw.shop.entity.OrderAddr;
import com.xxw.shop.module.common.constant.SystemErrorEnumError;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.security.AuthUserContext;
import com.xxw.shop.service.OrderAddrService;
import com.xxw.shop.service.OrderInfoService;
import com.xxw.shop.vo.OrderAddrVO;
import com.xxw.shop.vo.OrderInfoVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController("businessOrderController")
@Controller
@RequestMapping("/m/order")
@Tag(name = "business-订单接口")
public class OrderController {

    @Resource
    private OrderInfoService orderInfoService;

    @Resource
    private MapperFacade mapperFacade;

    //TODO
//    @Resource
//    private SearchOrderFeignClient searchOrderFeignClient;

    @Resource
    private OrderAddrService orderAddrService;

//    /**
//     * 分页获取
//     */
//    @GetMapping("/page")
//    @Operation(summary = "分页获取订单详情")
//    public ServerResponseEntity<EsPageVO<EsOrderInfoVO>> page(OrderSearchDTO orderSearchDTO) {
//        Long shopId = AuthUserContext.get().getTenantId();
//        orderSearchDTO.setShopId(shopId);
//        return searchOrderFeignClient.getOrderPage(orderSearchDTO);
//    }

    /**
     * 获取信息
     */
    @GetMapping("/order_info/{orderId}")
    @Operation(summary = "根据id获取订单详情")
    public ServerResponseEntity<OrderInfoVO> info(@PathVariable("orderId") Long orderId) {
        // 订单和订单项
        OrderInfoVO vo = orderInfoService.getOrderAndOrderItemData(orderId, AuthUserContext.get().getTenantId());
        // 详情用户收货地址
        OrderAddr orderAddr = orderAddrService.getById(vo.getOrderAddrId());
        vo.setOrderAddr(mapperFacade.map(orderAddr, OrderAddrVO.class));
        return ServerResponseEntity.success(vo);
    }

    /**
     * 获取订单用户下单地址
     */
    @GetMapping("/order_addr/{orderAddrId}")
    @Operation(summary = "获取订单用户下单地址")
    public ServerResponseEntity<OrderAddrVO> getOrderAddr(@PathVariable("orderAddrId") Long orderAddrId) {
        OrderAddr orderAddr = orderAddrService.getById(orderAddrId);
        return ServerResponseEntity.success(mapperFacade.map(orderAddr, OrderAddrVO.class));
    }

    /**
     * 订单项待发货数量查询
     */
    @GetMapping("/order_item_and_address/{orderId}")
    @Operation(summary = "订单项待发货数量查询")
    public ServerResponseEntity<OrderInfoVO> getOrderItemAndAddress(@PathVariable("orderId") Long orderId) {
        // 订单和订单项
        OrderInfoVO vo = orderInfoService.getOrderAndOrderItemData(orderId, AuthUserContext.get().getTenantId());
        // 详情用户收货地址
        OrderAddr orderAddr = orderAddrService.getById(vo.getOrderAddrId());
        vo.setOrderAddr(mapperFacade.map(orderAddr, OrderAddrVO.class));
        return ServerResponseEntity.success(vo);
    }

    /**
     * 发货
     */
    @PostMapping("/delivery")
    @Operation(summary = "发货")
    public ServerResponseEntity<Void> delivery(@Valid @RequestBody DeliveryOrderDTO deliveryOrderParam) {
        OrderInfoVO order = orderInfoService.getOrderByOrderId(deliveryOrderParam.getOrderId());
        // 订单不在支付状态
        if (!Objects.equals(order.getStatus(), OrderStatus.PADYED.value())) {
            return ServerResponseEntity.fail(SystemErrorEnumError.ORDER_NOT_PAYED);
        }
        orderInfoService.delivery(deliveryOrderParam);
        return ServerResponseEntity.success();
    }

}
