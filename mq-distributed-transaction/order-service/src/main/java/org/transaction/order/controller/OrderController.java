package org.transaction.order.controller;

import com.ruubypay.log.annotation.LogMarker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.transaction.order.dto.SubmitOrderDTO;
import org.transaction.order.entity.Order;
import org.transaction.order.mapper.OrderMapper;
import org.transaction.order.service.OrderService;


/**
 * @author liupenghui
 * @date 2021/11/24 6:38 下午
 */
@RestController
@Slf4j
public class OrderController {

    private final OrderMapper orderMapper;
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderMapper orderMapper, OrderService orderService) {
        this.orderMapper = orderMapper;
        this.orderService = orderService;
    }

    @GetMapping("/getPayCount")
    @LogMarker(businessDescription = "获取可购买数量", interfaceName = "/getPayCount")
    public String getPayCount(String orderNo) {
        if (StringUtils.isBlank(orderNo)) {
            return "NO TRANS";
        }
        Order orders = orderMapper.selectByOrderNo(orderNo);
        if (null == orders) {
            return "NO TRANS";
        }
        return orders.getPayCount() + "";
    }

    @PostMapping("/submitOrder")
    @LogMarker(businessDescription = "提交订单", interfaceName = "/submitOrder")
    public String submitOrder(@RequestBody SubmitOrderDTO dto) {
        log.info("开始提交订单");
        try {
            orderService.submitOrder(dto.getProductId(), dto.getPayCount());
            return "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "FAILED";
    }
}
