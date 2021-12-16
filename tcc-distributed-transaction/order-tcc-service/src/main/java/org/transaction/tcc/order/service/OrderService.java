package org.transaction.tcc.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.transaction.tcc.order.mapper.OrderMapper;

/**
 * @author liupenghui
 * @date 2021/12/16 8:07 下午
 */
@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;


}
