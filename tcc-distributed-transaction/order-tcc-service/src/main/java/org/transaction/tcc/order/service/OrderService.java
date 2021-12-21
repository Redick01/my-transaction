package org.transaction.tcc.order.service;

import org.dromara.hmily.annotation.Hmily;
import org.transaction.tcc.order.dto.StockDTO;

/**
 * @author liupenghui
 * @date 2021/12/16 8:07 下午
 */
public interface OrderService {


    @Hmily
    void saveOrder(StockDTO stockDTO) throws Exception;
}
