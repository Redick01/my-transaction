package org.transaction.tcc.stock.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.transaction.tcc.stock.entity.Stock;

@Repository
public interface StockMapper {

    Stock getStockByProductId(@Param("productId") Long productId);

    int deleteStock(@Param("productId") Long productId, @Param("payCount") Integer payCount);

    int freezeStock(@Param("productId") Long productId, @Param("freezeCount") Integer freezeCount);

    int unFreezeStock(@Param("productId") Long productId, @Param("freezeCount") Integer freezeCount);
}