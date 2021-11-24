package org.transaction.order.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @author liupenghui
 * @date 2021/11/24 5:39 下午
 */
@Slf4j
@Component
@RocketMQTransactionListener(txProducerGroup = "tx_order_group")
public class OrderTxMessageListener implements RocketMQLocalTransactionListener {

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {

        // 执行本地事务

        // 提交订单并保存事务消息

        // 返回commit或rollback

        return null;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        // 根据事务号检查是否存在本地事务
        // 存在就commit，否则unknown
        return null;
    }
}
