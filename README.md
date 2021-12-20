# my-transaction
Local transaction, distributed transaction 


## 1 mq-distributed-transaction可靠消息最终一致性分布式事务方案demo

### 1.2 数据库

- **order表**

id(20), create_time, order_no(64), product_id(20), pay_count

- **stock表**

id, product_id(20), total_count

- **tx_log**

id, tx_no(64), create_time


### 1.3 流程

1. order-service服务提供 提交订单 http接口
2. order服务提交订单，发送提交订单的消息
3. order实现本地事务监听，在执行本地事务方法中执行本地事务，提交订单，添加事务日志
4. order本地事务执行完成过后，向rocketmq发送COMMIT，否则ROLLBACK
5. order服务提供检查本地事务状态接口
6. stock服务监听消费组+topic数组，然后扣减库存，记录事务日志
7. stock服务扣减库存异常时，发送回滚消息到mq
8. order服务监听回滚消息，执行回滚

&nbsp; &nbsp;
&nbsp; &nbsp;
&nbsp; &nbsp;

## 2 tcc-distributed-transaction集成Hmily实现TCC分布式事务

### 2.2 数据库

- **tx-order库**

1. order表

```sql
CREATE TABLE `order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_no` varchar(64) DEFAULT NULL COMMENT '订单号',
  `product_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `pay_count` int(11) DEFAULT NULL COMMENT '购买数量',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_no_index` (`order_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=169 DEFAULT CHARSET=utf8;
```

2. tcc_tx_log表

```sql
CREATE TABLE `tcc_tx_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tx_no` varchar(64) DEFAULT NULL COMMENT '事务号',
  `tx_type` smallint(6) DEFAULT NULL COMMENT '事务类型0-try 1-confirm 2-cancel',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `tx_no_idx` (`tx_no`) USING BTREE COMMENT '事务号索引'
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;
```

- **tx-stock库**

1. tcc_stock表

```sql
CREATE TABLE `tcc_stock` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `product_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `total_count` int(11) DEFAULT NULL COMMENT '总数',
  `freezen_count` int(11) DEFAULT NULL COMMENT '冻结总数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `product_id_idx` (`product_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
```

2. tcc_tx_log

```sql
CREATE TABLE `tcc_tx_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tx_no` varchar(64) DEFAULT NULL COMMENT '事务号',
  `tx_type` smallint(6) DEFAULT NULL COMMENT '事务类型0-try 1-confirm 2-cancel',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
```

### 2.3 TCC处理流程

order-tcc-service订单服务

- 提供下单接口/sc-tcc/submitOrder
- 提供下单确认接口org.transaction.tcc.order.service.impl.OrderServiceImpl#confirm
- 提供下单取消接口org.transaction.tcc.order.service.impl.OrderServiceImpl#cancel

stock-tcc-service库存服务

- 提供扣减库存接口/stock/deleteStock
- 提供扣减库存确认接口org.transaction.tcc.stock.service.impl.StockServiceImpl#confirm
- 提供扣减库存取消接口org.transaction.tcc.stock.service.impl.StockServiceImpl#cancel


order-tcc-service订单服务尝试下单，并通知库存服务扣减库存；库存服务尝试扣减库存如果成功调用confirm方法失败调用cancel；如果扣减库存成功订单服务发起confirm方法调用否则调用cancel方法。







