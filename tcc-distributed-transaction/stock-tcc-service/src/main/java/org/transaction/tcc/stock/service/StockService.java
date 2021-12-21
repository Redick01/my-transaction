package org.transaction.tcc.stock.service;

import org.dromara.hmily.annotation.Hmily;
import org.transaction.tcc.stock.dto.StockDTO;


import java.util.Date;

/**
 * @author liupenghui
 * @date 2021/12/17 3:48 下午
 */
public interface StockService {


    @Hmily
    String tryDeleteStock(StockDTO dto) throws Exception;
}
