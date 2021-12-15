package org.transaction.springtx.service.stock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.transaction.springtx.exception.TransactionException;
import org.transaction.springtx.mapper.stock.StockMapper;

/**
 * @author liupenghui
 * @date 2021/12/6 4:32 下午
 */
@Service
@Slf4j
public class StockService {

    private final StockMapper stockMapper;

    @Autowired
    public StockService(StockMapper stockMapper) {
        this.stockMapper = stockMapper;
    }

    @Transactional(transactionManager = "stockTransactionManager", rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void deleteStock(Long productId, Integer payCount) {
        if (stockMapper.updateStock(productId, payCount) > 0) {
            log.info("扣减库存成功");
            throw new TransactionException("FAILED", "异常");
        } else {
            log.info("扣减库存失败");
            throw new TransactionException("FAILED", "扣减库存失败");
        }
    }
}
