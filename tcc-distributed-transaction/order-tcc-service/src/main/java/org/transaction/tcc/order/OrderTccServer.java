package org.transaction.tcc.order;

import com.ruubypay.miss.log.annotation.RuubypayLogEnabled;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author liupenghui
 * @date 2021/12/7 2:18 下午
 */
@SpringBootApplication
@RuubypayLogEnabled
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = {"org.transaction.tcc"})
public class OrderTccServer {

    public static void main(String[] args) {
        SpringApplication.run(OrderTccServer.class, args);
    }
}