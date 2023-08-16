package com.xxw.shop.controller.consumer;

import com.xxw.shop.api.order.constant.OrderStatus;
import com.xxw.shop.api.order.vo.OrderAddrVO;
import com.xxw.shop.api.order.vo.OrderInfoCompleteVO;
import com.xxw.shop.api.order.vo.OrderItemVO;
import com.xxw.shop.api.search.dto.OrderSearchDTO;
import com.xxw.shop.api.search.feign.SearchOrderFeignClient;
import com.xxw.shop.api.search.vo.EsOrderInfoVO;
import com.xxw.shop.api.search.vo.EsPageVO;
import com.xxw.shop.entity.OrderAddr;
import com.xxw.shop.module.common.constant.SystemErrorEnumError;
import com.xxw.shop.module.common.exception.BusinessException;
import com.xxw.shop.module.common.response.ServerResponseEntity;
import com.xxw.shop.module.security.AuthUserContext;
import com.xxw.shop.service.OrderAddrService;
import com.xxw.shop.service.OrderInfoService;
import com.xxw.shop.service.OrderItemService;
import com.xxw.shop.vo.OrderCountVO;
import com.xxw.shop.vo.OrderShopVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import ma.glasnost.orika.MapperFacade;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 我的订单
 */
@RestController
@RequestMapping("/c/myOrder")
@Tag(name = "consumer-我的订单接口")
public class MyOrderController {

    @Resource
    private OrderInfoService orderInfoService;

    @Resource
    private MapperFacade mapperFacade;

    @Resource
    private OrderItemService orderItemService;

    @Resource
    private SearchOrderFeignClient searchOrderFeignClient;

    @Resource
    private OrderAddrService orderAddrService;


    /**
     * 订单详情信息接口
     */
    @GetMapping("/order_detail")
    @Operation(summary = "订单详情信息", description = "根据订单号获取订单详情信息")
    @Parameter(name = "orderId", description = "订单号", required = true)
    public ServerResponseEntity<OrderShopVO> orderDetail(@RequestParam(value = "orderId") Long orderId) {
        Long userId = AuthUserContext.get().getUserId();
        OrderShopVO orderShopDto = new OrderShopVO();
        OrderInfoCompleteVO order = orderInfoService.getOrderByOrderIdAndUserId(orderId, userId);
        OrderAddr orderAddr = orderAddrService.getById(order.getOrderAddrId());
        List<OrderItemVO> orderItems = orderItemService.listOrderItemsByOrderId(orderId);
        orderShopDto.setShopId(order.getShopId());
        orderShopDto.setDeliveryType(order.getDeliveryType());
        orderShopDto.setShopName(order.getShopName());
        orderShopDto.setCreateTime(order.getCreateTime());
        orderShopDto.setStatus(order.getStatus());
        orderShopDto.setOrderAddr(mapperFacade.map(orderAddr, OrderAddrVO.class));
        // 付款时间
        orderShopDto.setPayTime(order.getPayTime());
        // 发货时间
        orderShopDto.setDeliveryTime(order.getDeliveryTime());
        // 完成时间
        orderShopDto.setFinallyTime(order.getFinallyTime());
        // 取消时间
        orderShopDto.setCancelTime(order.getCancelTime());
        // 更新时间
        orderShopDto.setUpdateTime(order.getUpdateTime());
        orderShopDto.setOrderItems(mapperFacade.mapAsList(orderItems, OrderItemVO.class));
        orderShopDto.setTotal(order.getTotal());
        orderShopDto.setTotalNum(order.getAllCount());

        return ServerResponseEntity.success(orderShopDto);
    }

    @GetMapping("/order_count")
    @Operation(summary = "计算各个订单数量", description = "根据订单状态计算各个订单数量")
    public ServerResponseEntity<OrderCountVO> orderCount() {
        Long userId = AuthUserContext.get().getUserId();
        OrderCountVO orderCount = orderInfoService.countNumberOfStatus(userId);
        return ServerResponseEntity.success(orderCount);
    }

    /**
     * 分页获取
     */
    @GetMapping("/search_order")
    @Operation(summary = "订单列表信息查询", description = "根据订单编号或者订单中商品名称搜索")
    public ServerResponseEntity<EsPageVO<EsOrderInfoVO>> searchOrder(OrderSearchDTO orderSearchDTO) {
        Long userId = AuthUserContext.get().getUserId();
        orderSearchDTO.setUserId(userId);
        return searchOrderFeignClient.getOrderPage(orderSearchDTO);
    }

    /**
     * 取消订单
     */
    @PutMapping("/cancel/{orderId}")
    @Operation(summary = "根据订单号取消订单", description = "根据订单号取消订单")
    @Parameter(name = "orderId", description = "订单号", required = true)
    public ServerResponseEntity<String> cancel(@PathVariable("orderId") Long orderId) {
        Long userId = AuthUserContext.get().getUserId();
        OrderInfoCompleteVO order = orderInfoService.getOrderByOrderIdAndUserId(orderId, userId);
        if (!Objects.equals(order.getStatus(), OrderStatus.UNPAY.value())) {
            // 订单已支付，无法取消订单
            throw new BusinessException(SystemErrorEnumError.ORDER_PAYED);
        }
        // 如果订单未支付的话，将订单设为取消状态
        orderInfoService.cancelOrderAndGetCancelOrderIds(Collections.singletonList(order.getOrderId()));
        return ServerResponseEntity.success();
    }


    /**
     * 确认收货
     */
    @PutMapping("/receipt/{orderId}")
    @Operation(summary = "根据订单号确认收货", description = "根据订单号确认收货")
    public ServerResponseEntity<String> receipt(@PathVariable("orderId") Long orderId) {
        Long userId = AuthUserContext.get().getUserId();
        OrderInfoCompleteVO order = orderInfoService.getOrderByOrderIdAndUserId(orderId, userId);
        if (!Objects.equals(order.getStatus(), OrderStatus.CONSIGNMENT.value())) {
            // 订单未发货，无法确认收货
            throw new BusinessException(SystemErrorEnumError.ORDER_NO_DELIVERY);
        }
        List<OrderItemVO> orderItems = orderItemService.listOrderItemsByOrderId(orderId);
        order.setOrderItems(orderItems);
        // 确认收货
        orderInfoService.receiptOrder(order.getOrderId());
        return ServerResponseEntity.success();
    }

    /**
     * 删除订单
     */
    @DeleteMapping("/{orderId}")
    @Operation(summary = "根据订单号删除订单", description = "根据订单号删除订单")
    @Parameter(name = "orderId", description = "订单号", required = true)
    public ServerResponseEntity<String> delete(@PathVariable("orderId") Long orderId) {
        Long userId = AuthUserContext.get().getUserId();
        OrderInfoCompleteVO order = orderInfoService.getOrderByOrderIdAndUserId(orderId, userId);
        if (!Objects.equals(order.getStatus(), OrderStatus.SUCCESS.value()) && !Objects.equals(order.getStatus(),
                OrderStatus.CLOSE.value())) {
            // 订单未完成或未关闭，无法删除订单
            throw new BusinessException(SystemErrorEnumError.ORDER_NOT_FINISH_OR_CLOSE);
        }
        // 删除订单
        orderInfoService.deleteOrder(order.getOrderId());
        return ServerResponseEntity.success();
    }
}
