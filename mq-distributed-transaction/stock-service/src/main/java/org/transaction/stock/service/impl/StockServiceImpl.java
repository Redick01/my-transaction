package org.transaction.stock.service.impl;

import org.transaction.stock.consumer.message.TxMessage;
import org.transaction.stock.service.StockService;

/**
 * @author Redick
 * @date 2021/11/25 12:23 上午
 */
public class StockServiceImpl implements StockService {

    @Override
    public void deleteStock(TxMessage txMessage) {
        // 检查本地事务

        // 根据产品ID获取库存

        // 检查库存情况是否可以下单

        // 扣减库存

        // 记录事务日志
    }
}
