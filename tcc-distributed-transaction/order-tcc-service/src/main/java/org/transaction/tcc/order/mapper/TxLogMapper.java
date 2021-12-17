package org.transaction.tcc.order.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.transaction.tcc.order.entity.TxLog;

@Repository
public interface TxLogMapper {

    int getCount(@Param("txNo") String txNo, @Param("txType") Integer txType);

    int saveTryTxLog(@Param("txLog") TxLog txLog);

    int saveConfirmTxLog(@Param("txLog") TxLog txLog);

    int saveCancelTxLog(@Param("txLog") TxLog txLog);
}