package org.transaction.tcc.order.service.impl;

import com.redick.util.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.transaction.tcc.order.dto.StockDTO;
import org.transaction.tcc.order.entity.Order;
import org.transaction.tcc.order.entity.TxLog;
import org.transaction.tcc.order.mapper.OrderMapper;
import org.transaction.tcc.order.mapper.TxLogMapper;
import org.transaction.tcc.order.remote.StockService;
import org.transaction.tcc.order.service.OrderService;

import java.util.Date;
import java.util.UUID;

/**
 * @author liupenghui
 * @date 2021/12/20 2:20 下午
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private TxLogMapper txLogMapper;
    @Autowired
    private StockService stockService;

    @HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancel")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrder(StockDTO stockDTO) throws Exception {
        log.info(LogUtil.marker(), "保存订单try方法执行");
        String txNo = stockDTO.getTxNo();
        // 检查事务悬挂
        if (isExistTx(txNo, 0)) {
            log.info(LogUtil.marker(txNo), "存在事务");
            return;
        }
        Order order = new Order();
        order.setOrderNo(stockDTO.getOrderNo());
        order.setCreateTime(new Date());
        order.setProductId(stockDTO.getProductId());
        if (orderMapper.saveOrder(order) > 0) {
            log.info(LogUtil.marker(), "记录事务日志");
            saveTxLog(txNo, 0);
        }
        // 扣减库存
        log.info(LogUtil.funcStartMarker(stockDTO), "发起扣减库存");
        String result = stockService.deleteStock(stockDTO);
        log.info(LogUtil.funcEndMarker(result), "扣减库存结果");
        if ("STOCK_ERROR".equals(result)) {
            throw new Exception("STOCK_ERROR");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void confirm(StockDTO stockDTO) {
        log.info(LogUtil.marker(), "保存订单confirm方法执行");
        String txNo = UUID.randomUUID().toString();
        // 检查事务悬挂
        if (isExistTx(txNo, 1)) {
            log.info(LogUtil.marker(txNo), "存在事务");
            return;
        }
        if (orderMapper.confirmOrder(stockDTO.getOrderNo(), stockDTO.getPayCount()) > 0) {
            log.info(LogUtil.marker(), "记录事务日志");
            saveTxLog(txNo, 1);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void cancel(StockDTO stockDTO) {
        log.info(LogUtil.marker(), "保存订单cancel方法执行");
        String txNo = UUID.randomUUID().toString();
        // 检查事务悬挂
        if (isExistTx(txNo, 2)) {
            log.info(LogUtil.marker(txNo), "存在事务");
            return;
        }
        if (orderMapper.deleteByOrderNo(stockDTO.getOrderNo()) > 0) {
            log.info(LogUtil.marker(), "记录事务日志");
            saveTxLog(txNo, 2);
        }
    }

    private boolean isExistTx(String txNo, int txType) {
        int tx = txLogMapper.getCount(txNo, txType);
        if (tx > 0) {
            log.info(LogUtil.marker(txNo), "存在事务");
            return true;
        }
        return false;
    }

    private void saveTxLog(String txNo, int txType) {
        TxLog log = new TxLog();
        log.setCreateTime(new Date());
        log.setTxNo(txNo);
        log.setTxType(txType);
        switch (txType) {
            case 0:
                txLogMapper.saveTryTxLog(log);
                break;
            case 1:
                txLogMapper.saveConfirmTxLog(log);
                break;
            case 2:
                txLogMapper.saveCancelTxLog(log);
                break;
            default:
        }
    }
}
