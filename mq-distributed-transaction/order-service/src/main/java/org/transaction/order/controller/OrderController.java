package org.transaction.order.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.transaction.order.entity.Order;
import org.transaction.order.entity.OrderExample;
import org.transaction.order.mapper.OrderMapper;

import java.util.List;

/**
 * @author liupenghui
 * @date 2021/11/24 6:38 下午
 */
@RestController
public class OrderController {

    private final OrderMapper orderMapper;

    @Autowired
    public OrderController(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @GetMapping("/getPayCount")
    public String getPayCount(String orderNo) {
        if (StringUtils.isBlank(orderNo)) {
            return "NO TRANS";
        }
        OrderExample example = new OrderExample();
        OrderExample.Criteria criteria = example.or();
        criteria.andOrderNoEqualTo(orderNo);
        List<Order> orders = orderMapper.selectByExample(example);
        if (orders.isEmpty()) {
            return "NO TRANS";
        }
        return orders.get(0).getPayCount() + "";
    }
}
