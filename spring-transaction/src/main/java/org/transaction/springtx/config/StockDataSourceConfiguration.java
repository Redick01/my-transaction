package org.transaction.springtx.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.transaction.springtx.util.ResourcesUtil;

import javax.sql.DataSource;

/**
 * @author liupenghui
 * @date 2021/12/1 3:27 下午
 */
@EnableTransactionManagement
@Configuration
@MapperScan(basePackages = "org.transaction.springtx.mapper.stock", sqlSessionTemplateRef = "stockSqlSessionTemplate")
public class StockDataSourceConfiguration {

    @Bean(name = "stockDatasource", initMethod = "init", destroyMethod = "close")
    public DruidDataSource getDataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl("jdbc:mysql://127.0.0.1:3316/tx-stock?useUnicode=true&characterEncoding=UTF-8&useOldAliasMetadataBehavior=true&autoReconnect=true&failOverReadOnly=false&useSSL=false");
        datasource.setUsername("root");
        datasource.setPassword("admin123");
        // 配置初始化大小、最小、最大
        datasource.setInitialSize(10);
        datasource.setMinIdle(5);
        datasource.setMaxActive(20);
        //配置获取链接等待超时的时间
        datasource.setMaxWait(60000);
        //配置间隔多久才能进行一次检测，检测需要关闭的控线连接，单位是毫秒
        datasource.setTimeBetweenEvictionRunsMillis(3600000);
        // 配置一个连接在池中最小生存的时间，单位是毫秒
        datasource.setMinEvictableIdleTimeMillis(3600000);
        // 探测连接是否有效的sql
        datasource.setValidationQuery("select 1 from dual");
        //连接检查 保证安全性
        datasource.setTestWhileIdle(true);
        //申请连接时检查
        datasource.setTestOnBorrow(false);
        //还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
        datasource.setTestOnReturn(false);
        return datasource;
    }

    @Bean(name = "stockSqlSessionFactory")
    @Primary
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("stockDatasource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(ResourcesUtil.resolveMapperLocations("classpath:mapper/stock/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "stockSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sentinelSqlSessionTemplate(@Qualifier("stockSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "stockTransactionManager")
    public DataSourceTransactionManager sentinelTransactionManager(@Qualifier("stockDatasource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
