package org.transaction.order.service;

import org.transaction.order.consumer.message.TxMessage;

/**
 * @author Redick
 * @date 2021/11/24 11:53 下午
 */
public interface OrderService {

    /**
     * 提交订单并且保存事务消息
     * @param txMessage 事务消息
     */
    void submitOrderAndSaveTxNo(TxMessage txMessage);

    /**
     * 提交订单
     * @param productId 产品ID
     * @param payCount 购买数量
     */
    void submitOrder(Long productId, Integer payCount);
}
