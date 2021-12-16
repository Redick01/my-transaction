package org.transaction.tcc.stock.mapper;

import org.apache.ibatis.annotations.Param;
import org.transaction.tcc.stock.entity.Stock;
import org.transaction.tcc.stock.entity.StockExample;

import java.util.List;

public interface StockMapper {
    int countByExample(StockExample example);

    int deleteByExample(StockExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Stock record);

    int insertSelective(Stock record);

    List<Stock> selectByExample(StockExample example);

    Stock selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Stock record, @Param("example") StockExample example);

    int updateByExample(@Param("record") Stock record, @Param("example") StockExample example);

    int updateByPrimaryKeySelective(Stock record);

    int updateByPrimaryKey(Stock record);

    Stock getStockByProductId(@Param("productId") Long productId);

    int updateStock(@Param("productId") Long productId, @Param("payCount") Integer payCount);
}