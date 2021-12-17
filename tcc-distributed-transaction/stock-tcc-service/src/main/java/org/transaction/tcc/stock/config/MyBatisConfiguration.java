package org.transaction.tcc.stock.config;

import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author liupenghui
 * @date 2021/11/24 1:31 下午
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan(value = {"org.transaction.tcc.stock.mapper"})
public class MyBatisConfiguration {

    @Bean
    public PaginationInnerInterceptor paginationInterceptor() {
        return new PaginationInnerInterceptor();
    }
}
