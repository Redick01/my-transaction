package org.transaction.tcc.order.controller;

import com.redick.annotation.LogMarker;
import com.redick.util.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.transaction.tcc.order.dto.StockDTO;
import org.transaction.tcc.order.dto.SubmitOrderDTO;
import org.transaction.tcc.order.service.OrderService;

import java.util.UUID;

/**
 * @author liupenghui
 * @date 2021/12/7 2:51 下午
 */
@RestController
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/sc-tcc/submitOrder")
    @LogMarker(businessDescription = "提交订单", interfaceName = "/sc-tcc/submitOrder")
    public String submitOrder(@RequestBody SubmitOrderDTO dto) {
        String orderNo = UUID.randomUUID().toString();
        try {
            orderService.saveOrder(new StockDTO(orderNo, dto.getProductId(), dto.getPayCount(), UUID.randomUUID().toString()));
            return "SUCCESS";
        } catch (Exception e) {
            log.error(LogUtil.exceptionMarker(), "异常", e);
            return "ERROR";
        }
    }
}
