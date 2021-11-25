package org.transaction.order.consumer;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.transaction.order.consumer.message.TxMessage;
import org.transaction.order.service.OrderService;

/**
 * @author liupenghui
 * @date 2021/11/24 5:39 下午
 */
@Slf4j
@Component
@RocketMQTransactionListener(txProducerGroup = "tx_order_group")
public class OrderTxMessageListener implements RocketMQLocalTransactionListener {

    private final OrderService orderService;

    @Autowired
    public OrderTxMessageListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        try {
            // 执行本地事务
            log.info("开始执行本地事务");
            // 提交订单并保存事务消息
            log.info("提交订单保存事务日志");
            TxMessage txMessage = getTxMessage(message);
            orderService.submitOrderAndSaveTxNo(txMessage);
            // 返回commit
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            e.printStackTrace();
            // rollback
            log.info("执行回滚");
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        // 根据事务号检查是否存在本地事务
        // 存在就commit，否则unknown
        return null;
    }

    private TxMessage getTxMessage(Message message) {
        String messageString = new String((byte[]) message.getPayload());
        return JSONObject.parseObject(messageString, TxMessage.class);
    }
}
