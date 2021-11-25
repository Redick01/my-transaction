package org.transaction.order.consumer;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.transaction.order.consumer.message.TxMessage;
import org.transaction.order.mapper.OrderMapper;

/**
 * @author Redick
 * @date 2021/11/25 11:47 下午
 */
@RocketMQMessageListener(consumerGroup = "tx_order_rollback_group", topic = "topic_tx_rollback_msg")
@Component
@Slf4j
public class OrderRollbackListener implements RocketMQListener<String> {

    private final OrderMapper orderMapper;

    @Autowired
    public OrderRollbackListener(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public void onMessage(String s) {
        try {
            TxMessage txMessage = deserialization(s);
            log.info("开始订单回滚");
            // 回滚订单，删除或者更新订单状态，这里直接删除订单
            orderMapper.deleteByOrderNo(txMessage.getOrderNo());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("回滚订单异常");
        }
    }

    private TxMessage deserialization(String msg) {
        return JSONObject.parseObject(msg, TxMessage.class);
    }
}
