package org.transaction.tcc.stock.controller;

import com.ruubypay.log.annotation.LogMarker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.transaction.tcc.stock.dto.SubmitOrderDTO;

/**
 * @author liupenghui
 * @date 2021/12/7 3:18 下午
 */
@RestController
@Slf4j
public class StockController {

    @LogMarker(businessDescription = "扣减库存", interfaceName = "/stock/deleteStock")
    @PostMapping(value = "/stock/deleteStock")
    public String deleteStock(@RequestBody SubmitOrderDTO dto) {
        return "SUCCESS";
    }
}
