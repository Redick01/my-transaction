package org.transaction.tcc.stock;

import com.alibaba.edas.ribbon.MigrationRibbonConfiguration;
import com.redick.boot.annotation.LogHelperEnable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author liupenghui
 * @date 2021/12/7 3:13 下午
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@LogHelperEnable
@RibbonClients(defaultConfiguration = MigrationRibbonConfiguration.class)
public class StockTccServer {

    public static void main(String[] args) {
        SpringApplication.run(StockTccServer.class, args);
    }
}
