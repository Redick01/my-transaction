package org.transaction.tcc.stock.controller;

import com.redick.annotation.LogMarker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.transaction.tcc.stock.dto.StockDTO;
import org.transaction.tcc.stock.service.StockService;

/**
 * @author liupenghui
 * @date 2021/12/7 3:18 下午
 */
@RestController
@Slf4j
public class StockController {

    @Autowired
    private StockService stockService;

    @LogMarker(businessDescription = "扣减库存", interfaceName = "/stock/deleteStock")
    @PostMapping(value = "/stock/deleteStock")
    public String deleteStock(@RequestBody StockDTO dto) throws Exception {
        return stockService.tryDeleteStock(dto);
    }
}
