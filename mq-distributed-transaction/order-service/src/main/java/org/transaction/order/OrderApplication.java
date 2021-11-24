package org.transaction.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author liupenghui
 * @date 2021/11/22 1:33 下午
 */
@SpringBootApplication
public class OrderApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configApplicationContext = SpringApplication.run(OrderApplication.class, args);
    }
}
