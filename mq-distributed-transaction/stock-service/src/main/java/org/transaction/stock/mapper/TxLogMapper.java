package org.transaction.stock.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.transaction.stock.entity.TxLog;
import org.transaction.stock.entity.TxLogExample;

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
}