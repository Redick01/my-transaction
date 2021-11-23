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




