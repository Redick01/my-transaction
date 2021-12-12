package org.transaction.stock;

import com.ruubypay.miss.log.annotation.RuubypayLogEnabled;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liupenghui
 * @date 2021/11/24 11:47 上午
 */
@SpringBootApplication
@RuubypayLogEnabled
public class StockApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockApplication.class, args);
    }
}
