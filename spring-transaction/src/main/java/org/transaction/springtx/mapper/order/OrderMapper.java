package org.transaction.springtx.mapper.order;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.transaction.springtx.entity.order.Order;

@Repository
public interface OrderMapper {

    Order selectByOrderNo(@Param("orderNo") String orderNo);

    int saveOrder(@Param("order") Order order);

    int deleteByOrderNo(@Param("orderNo") String orderNo);
}