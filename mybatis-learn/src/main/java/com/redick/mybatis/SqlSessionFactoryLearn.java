package com.redick.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import com.redick.mybatis.mapper.ItpcsConfig;
import com.redick.mybatis.mapper.ItpcsConfigMapper;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author liupenghui
 * @date 2022/2/17 10:14 上午
 */
public class SqlSessionFactoryLearn {

    private static DruidDataSource dataSource;

    private static DruidDataSource buildDataSource() {
        dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://rm-2zeu2qjf0b3nnt0s8.mysql.rds.aliyuncs.com:3306/ruubypayrbps?characterEncoding=UTF8");
        dataSource.setUsername("ruubypaytest");
        dataSource.setPassword("Ruyixing2017");
        // 配置初始化大小、最小、最大
        dataSource.setInitialSize(2);
        dataSource.setMinIdle(2);
        dataSource.setMaxActive(100);
        //配置获取链接等待超时的时间
        dataSource.setMaxWait(60000);
        //配置间隔多久才能进行一次检测，检测需要关闭的控线连接，单位是毫秒
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        // 配置一个连接在池中最小生存的时间，单位是毫秒
        dataSource.setMinEvictableIdleTimeMillis(300000);
        // 探测连接是否有效的sql
        dataSource.setValidationQuery("SELECT 1");
        //连接检查 保证安全性
        dataSource.setTestWhileIdle(true);
        //申请连接时检查
        dataSource.setTestOnBorrow(false);
        //还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
        dataSource.setTestOnReturn(false);
        return dataSource;
    }

    public static void main(String[] args) {
        DruidDataSource dataSource = buildDataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("test", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        //configuration.addMapper(ItpcsConfigMapper.class);
        configuration.addMappers("com/redick/mybatis/mapper", ItpcsConfigMapper.class);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<ItpcsConfig> list = sqlSession.selectList("com.redick.mybatis.mapper.ItpcsConfigMapper.selectAll");
        list.forEach(e -> System.out.println(e.toString()));
    }
}
