# my-transaction
Local transaction, distributed transaction 


## mq-distributed-transaction可靠消息最终一致性分布式事务方案demo

### 数据库

- **order表**

id(20), create_time, order_no(64), product_id(20), pay_count

- **stock表**

id, product_id(20), total_count

- **tx_log**

id, tx_no(64), create_time


### 流程

1. order-service服务提供 提交订单 http接口
2. order服务提交订单，发送提交订单的消息
3. order实现本地事务监听，在执行本地事务方法中执行本地事务，提交订单，添加事务日志
4. order本地事务执行完成过后，向rocketmq发送COMMIT，否则ROLLBACK
5. order服务提供检查本地事务状态接口

6. stock服务监听消费组+topic数组，然后扣减库存，记录事务日志

7. stock服务扣减库存异常时，发送回滚消息到mq

8. order服务监听回滚消息，执行回滚




