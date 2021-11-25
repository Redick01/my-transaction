package org.transaction.stock.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.transaction.stock.consumer.message.TxMessage;
import org.transaction.stock.entity.Stock;
import org.transaction.stock.entity.TxLog;
import org.transaction.stock.mapper.StockMapper;
import org.transaction.stock.mapper.TxLogMapper;
import org.transaction.stock.service.StockService;

import java.util.Date;

/**
 * @author Redick
 * @date 2021/11/25 12:23 上午
 */
@Service
@Slf4j
public class StockServiceImpl implements StockService {

    private final TxLogMapper txLogMapper;
    private final StockMapper stockMapper;

    @Autowired
    public StockServiceImpl(TxLogMapper txLogMapper, StockMapper stockMapper) {
        this.txLogMapper = txLogMapper;
        this.stockMapper = stockMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteStock(TxMessage txMessage) throws Exception {
        // 检查本地事务
        int count = txLogMapper.getCount(txMessage.getTxNo());
        if (count != 0) {
            log.info("存在事务：[{}]", txMessage.getTxNo());
            return;
        }
        // 根据产品ID获取库存
        Stock stock = stockMapper.getStockByProductId(txMessage.getProductId());
        // 检查库存情况是否可以下单
        if (txMessage.getPayCount() > stock.getTotalCount()) {
            log.info("库存不足");
            // TODO 通知order-service回滚
            throw new Exception("库存不足");
        }
        Date date = new Date();
        // 扣减库存
        stockMapper.updateStock(txMessage.getProductId(), txMessage.getPayCount());
        log.info("扣减库存成功");
        // 记录事务日志
        TxLog txLog = new TxLog();
        txLog.setTxNo(txLog.getTxNo());
        txLog.setCreateTime(date);
        txLogMapper.saveTxLog(txLog);
        log.info("记录事务日志成功");
    }
}
