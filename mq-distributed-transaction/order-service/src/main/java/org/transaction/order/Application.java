package org.transaction.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author liupenghui
 * @date 2021/11/22 1:33 下午
 */
@SpringBootApplication
@EnableTransactionManagement
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext configApplicationContext = SpringApplication.run(Application.class, args);
    }
}
