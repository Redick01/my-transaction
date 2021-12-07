package org.transaction.tcc.order.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.transaction.tcc.order.dto.SubmitOrderDTO;

/**
 * @author liupenghui
 * @date 2021/12/7 11:39 上午
 */
@FeignClient(name = "stock-tcc-service")
public interface StockService {

    /**
     * 扣减库存
     * @param dto
     * @return
     */
    @PostMapping(value = "/stock/deleteStock")
    String deleteStock(@RequestBody SubmitOrderDTO dto);
}
