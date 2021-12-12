package org.transaction.order;

import com.ruubypay.miss.log.annotation.RuubypayLogEnabled;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author liupenghui
 * @date 2021/11/22 1:33 下午
 */
@SpringBootApplication
@RuubypayLogEnabled
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
