package org.transaction.order.service.impl;

import org.transaction.order.consumer.message.TxMessage;
import org.transaction.order.service.OrderService;

/**
 * @author Redick
 * @date 2021/11/25 12:23 上午
 */
public class OrderServiceImpl implements OrderService {

    @Override
    public void submitOrderAndSaveTxNo(TxMessage txMessage) {
        // 根据事务号检查订单是否存在

        // 生成订单

        // 记录事务日志
    }

    @Override
    public void submitOrder(Long productId, Integer payCount) {
        // 发送事务消息
    }
}
