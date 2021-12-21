package org.transaction.tcc.order;

import com.alibaba.edas.ribbon.MigrationRibbonConfiguration;
import com.redick.boot.annotation.LogHelperEnable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author liupenghui
 * @date 2021/12/7 2:18 下午
 */
@SpringBootApplication
@LogHelperEnable
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = {"org.transaction.tcc"})
@RibbonClients(defaultConfiguration = MigrationRibbonConfiguration.class)
public class OrderTccServer {

    public static void main(String[] args) {
        SpringApplication.run(OrderTccServer.class, args);
    }
}
