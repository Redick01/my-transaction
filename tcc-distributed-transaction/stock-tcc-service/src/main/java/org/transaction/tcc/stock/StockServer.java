package org.transaction.tcc.stock;

import com.ruubypay.miss.log.annotation.RuubypayLogEnabled;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author liupenghui
 * @date 2021/12/7 3:13 下午
 */
@SpringBootApplication
@RuubypayLogEnabled
@EnableDiscoveryClient
@EnableFeignClients
public class StockServer {

    public static void main(String[] args) {
        SpringApplication.run(StockServer.class, args);
    }
}
