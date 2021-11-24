package org.transaction.stock.service;

import org.transaction.stock.consumer.message.TxMessage;

/**
 * @author Redick
 * @date 2021/11/25 12:05 上午
 */
public interface StockService {

    /**
     * 减少库存
     * @param txMessage 事务消息
     */
    void deleteStock(TxMessage txMessage);
}
