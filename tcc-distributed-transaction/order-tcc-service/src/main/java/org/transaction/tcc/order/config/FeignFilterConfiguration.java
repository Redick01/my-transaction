package org.transaction.tcc.order.config;

import com.ruubypay.log.filter.feign.FeignFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liupenghui
 * @date 2021/12/7 7:24 下午
 */
@Configuration
public class FeignFilterConfiguration {

    @Bean
    public FeignFilter getFeignFilter() {
        return new FeignFilter();
    }
}
