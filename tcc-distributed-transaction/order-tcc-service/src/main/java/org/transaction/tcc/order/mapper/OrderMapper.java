package org.transaction.tcc.order.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.transaction.tcc.order.entity.Order;

@Repository
public interface OrderMapper {

    Order selectByOrderNo(@Param("orderNo") String orderNo);

    int saveOrder(@Param("order") Order order);

    int deleteByOrderNo(@Param("orderNo") String orderNo);

    int confirmOrder(@Param("orderNo") String orderNo, @Param("count") int count);
}