package org.transaction.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ruubypay.log.filter.mq.MqWrapperBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.transaction.order.consumer.message.TxMessage;
import org.transaction.order.entity.Order;
import org.transaction.order.entity.TxLog;
import org.transaction.order.mapper.OrderMapper;
import org.transaction.order.mapper.TxLogMapper;
import org.transaction.order.service.OrderService;

import java.util.Date;
import java.util.UUID;

/**
 * @author Redick
 * @date 2021/11/25 12:23 上午
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final RocketMQTemplate rocketMQTemplate;
    private final TxLogMapper txLogMapper;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderServiceImpl(RocketMQTemplate rocketMQTemplate, TxLogMapper txLogMapper, OrderMapper orderMapper) {
        this.rocketMQTemplate = rocketMQTemplate;
        this.txLogMapper = txLogMapper;
        this.orderMapper = orderMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void submitOrderAndSaveTxNo(TxMessage txMessage) {
        // 根据事务号检查订单是否存在
        int count = txLogMapper.getCount(txMessage.getTxNo());
        if (0 != count) {
            log.info("存在事务：[{}]", txMessage.getTxNo());
            return;
        }
        Date date = new Date();
        // 生成订单
        Order order = new Order();
        order.setOrderNo(txMessage.getOrderNo());
        order.setCreateTime(date);
        order.setPayCount(txMessage.getPayCount());
        order.setProductId(txMessage.getProductId());
        log.info("开始存储订单：[{}]", order.toString());
        int res = orderMapper.saveOrder(order);
        if (res > 0) {
            // 记录事务日志
            TxLog txLog = new TxLog();
            txLog.setTxNo(txMessage.getTxNo());
            txLog.setCreateTime(date);
            log.info("开始记录事务日志：[{}]", txLog.toString());
            txLogMapper.saveTxLog(txLog);
        }
    }

    @Override
    public void submitOrder(Long productId, Integer payCount) {
        // 发送事务消息
        TxMessage txMessage = new TxMessage();
        // 全局事务编号
        String txNo = UUID.randomUUID().toString();
        txMessage.setProductId(productId);
        txMessage.setPayCount(payCount);
        txMessage.setTxNo(txNo);
        txMessage.setOrderNo(UUID.randomUUID().toString());
        MqWrapperBean<TxMessage> mqWrapperBean = new MqWrapperBean<>(txMessage);
        String jsonString = JSONObject.toJSONString(mqWrapperBean);
        Message<String> msg = MessageBuilder.withPayload(jsonString).build();
        rocketMQTemplate.sendMessageInTransaction("tx_order_group", "topic_txmsg", msg, null);
    }
}
