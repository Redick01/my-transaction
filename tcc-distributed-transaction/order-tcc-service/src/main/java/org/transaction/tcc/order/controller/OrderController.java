package org.transaction.tcc.order.controller;

import com.ruubypay.log.annotation.LogMarker;
import com.ruubypay.log.util.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.transaction.tcc.order.dto.SubmitOrderDTO;
import org.transaction.tcc.order.remote.StockService;

/**
 * @author liupenghui
 * @date 2021/12/7 2:51 下午
 */
@RestController
@Slf4j
public class OrderController {

    private final StockService stockService;

    @Autowired
    public OrderController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping("/sc-tcc/submitOrder")
    @LogMarker(businessDescription = "提交订单", interfaceName = "/sc-tcc/submitOrder")
    public String submitOrder(@RequestBody SubmitOrderDTO dto) {
        log.info(LogUtil.funcStartMarker(dto), "调用库存服务扣减库存");
        String result = stockService.deleteStock(dto);
        log.info(LogUtil.funcEndMarker(result), "库存服务返回结果");
        return result;
    }
}
