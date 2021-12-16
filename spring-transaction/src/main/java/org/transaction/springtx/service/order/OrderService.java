package org.transaction.springtx.service.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.transaction.springtx.entity.order.Order;
import org.transaction.springtx.exception.TransactionException;
import org.transaction.springtx.mapper.order.OrderMapper;
import org.transaction.springtx.mapper.stock.StockMapper;
import org.transaction.springtx.service.stock.StockService;

import java.util.Date;
import java.util.UUID;

/**
 * @author liupenghui
 * @date 2021/12/1 3:46 下午
 */
@Service
@Slf4j
public class OrderService {

    private final OrderMapper orderMapper;
    private final StockService stockService;
    private final StockMapper stockMapper;


    @Autowired
    public OrderService(OrderMapper orderMapper, StockService stockService, StockMapper stockMapper) {
        this.orderMapper = orderMapper;
        this.stockService = stockService;
        this.stockMapper = stockMapper;
    }

    @Transactional(transactionManager = "orderTransactionManager", rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void submitOrder(Long productId, Integer payCount) {
        Order order = new Order();
        String orderNo = UUID.randomUUID().toString();
        order.setOrderNo(orderNo);
        order.setProductId(productId);
        order.setCreateTime(new Date());
        order.setPayCount(payCount);
        if (orderMapper.saveOrder(order) > 0) {
            log.info("保存订单成功");
            // 这段代码产生了自调用，导致事务失效
            deleteStock(productId, payCount);
            // 正确写法
            stockService.deleteStock(productId, payCount);
        } else {
            log.info("保存订单失败");
            throw new TransactionException("FAILED", "保存订单失败");
        }
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
