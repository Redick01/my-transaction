package org.transaction.springtx.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.transaction.springtx.dto.SubmitOrderDTO;
import org.transaction.springtx.service.order.OrderService;

/**
 * @author liupenghui
 * @date 2021/12/1 4:22 下午
 */
@RestController
@Slf4j
public class SubmitOrderController {

    private final OrderService orderService;

    @Autowired
    public SubmitOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/submitOrder")
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
