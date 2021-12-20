package org.transaction.tcc.stock.service.impl;

import com.redick.util.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.transaction.tcc.stock.dto.StockDTO;
import org.transaction.tcc.stock.entity.Stock;
import org.transaction.tcc.stock.entity.TxLog;
import org.transaction.tcc.stock.mapper.StockMapper;
import org.transaction.tcc.stock.mapper.TxLogMapper;
import org.transaction.tcc.stock.service.StockService;

import java.util.Date;

/**
 * @author liupenghui
 * @date 2021/12/20 2:22 下午
 */
@Service
@Slf4j
public class StockServiceImpl implements StockService {

    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private TxLogMapper txLogMapper;

    @HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancel")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void tryDeleteStock(StockDTO dto) throws Exception {
        // 幂等处理
        if (isExistTx(dto.getTxNo(), 0)) {
            log.info(LogUtil.marker(dto.getTxNo()), "try方法已经执行过");
            return;
        }
        // 悬挂处理
        if (isExistTx(dto.getTxNo(), 1) || isExistTx(dto.getTxNo(), 2)) {
            log.info(LogUtil.marker(dto.getTxNo()), "confirm方法或cancel方法已经执行过");
            return;
        }
        // 检查库存
        Stock stock = stockMapper.getStockByProductId(dto.getProductId());
        if (dto.getPayCount() > (stock.getTotalCount() - stock.getFreezenCount())) {
            log.info(LogUtil.marker(dto.getTxNo()), "库存不足");
            throw new Exception("库存不足");
        }
        // 冻结库存
        stockMapper.freezeStock(dto.getProductId(), dto.getPayCount());
        // 保存事务日志
        saveTxLog(dto.getTxNo(), 0);
    }

    @Transactional(rollbackFor = Exception.class)
    public void confirm(StockDTO dto) {
        // 幂等处理
        if (isExistTx(dto.getTxNo(), 1)) {
            log.info(LogUtil.marker(dto.getTxNo()), "try方法已经执行过");
            return;
        }
        stockMapper.deleteStock(dto.getProductId(), dto.getPayCount());
        saveTxLog(dto.getTxNo(), 1);
    }

    @Transactional(rollbackFor = Exception.class)
    public void cancel(StockDTO dto) {
        // 幂等处理
        if (isExistTx(dto.getTxNo(), 2)) {
            log.info(LogUtil.marker(dto.getTxNo()), "try方法已经执行过");
            return;
        }
        // 库存解冻
        stockMapper.unFreezeStock(dto.getProductId(), dto.getPayCount());
        saveTxLog(dto.getTxNo(), 2);
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
