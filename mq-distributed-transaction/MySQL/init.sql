-- 订单表
CREATE TABLE `order` (
                         id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                         order_no VARCHAR(64) COMMENT '订单号',
                         product_id BIGINT(20) COMMENT '商品id',
                         pay_count int(11) COMMENT '购买数量',
                         create_time datetime  COMMENT '创建时间',
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `order_no_index` (`order_no`) USING BTREE
);

-- 库存表
CREATE TABLE `stock` (
                         id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                         product_id BIGINT(20) COMMENT '商品id',
                         total_count int(11) COMMENT '总数',
                         create_time datetime  COMMENT '创建时间',
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `product_id_idx` (`product_id`) USING BTREE
);

-- 事务日志
CREATE TABLE `tx_log` (
                          id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                          tx_no VARCHAR(64) COMMENT '事务号',
                          create_time datetime  COMMENT '创建时间',
                          PRIMARY KEY (`id`)
);