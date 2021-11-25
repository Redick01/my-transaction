package org.transaction.order.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.transaction.order.entity.Order;

@Repository
public interface OrderMapper {

    Order selectByOrderNo(@Param("orderNo") String orderNo);

    int saveOrder(@Param("order") Order order);
}