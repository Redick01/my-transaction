package org.transaction.tcc.stock.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.transaction.tcc.stock.entity.TxLog;
import org.transaction.tcc.stock.entity.TxLogExample;


import java.util.List;

@Repository
public interface TxLogMapper {
    int countByExample(TxLogExample example);

    int deleteByExample(TxLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TxLog record);

    int insertSelective(TxLog record);

    List<TxLog> selectByExample(TxLogExample example);

    TxLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TxLog record, @Param("example") TxLogExample example);

    int updateByExample(@Param("record") TxLog record, @Param("example") TxLogExample example);

    int updateByPrimaryKeySelective(TxLog record);

    int updateByPrimaryKey(TxLog record);

    int getCount(@Param("txNo") String txNo, @Param("txType") Integer txType);

    int saveTryTxLog(@Param("txLog") TxLog txLog);

    int saveConfirmTxLog(@Param("txLog") TxLog txLog);

    int saveCancelTxLog(@Param("txLog") TxLog txLog);
}