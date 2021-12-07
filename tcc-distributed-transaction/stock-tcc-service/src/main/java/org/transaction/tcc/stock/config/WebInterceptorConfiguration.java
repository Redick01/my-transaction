package org.transaction.tcc.stock.config;

import com.ruubypay.log.filter.web.WebConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liupenghui
 * @date 2021/12/7 7:24 下午
 */
@Configuration
public class WebInterceptorConfiguration {

    @Bean
    public WebConfiguration getWebConfiguration() {
        return new WebConfiguration();
    }
}
