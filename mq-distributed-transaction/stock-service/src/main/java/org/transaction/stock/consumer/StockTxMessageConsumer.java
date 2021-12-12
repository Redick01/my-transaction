package org.transaction.stock.consumer;

import com.alibaba.fastjson.JSONObject;
import com.ruubypay.log.filter.mq.MqWrapperBean;
import com.ruubypay.log.filter.mq.apacherocketmq.MqConsumer;
import com.ruubypay.log.filter.mq.apacherocketmq.MqConsumerProcessor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.transaction.stock.consumer.message.TxMessage;
import org.transaction.stock.service.StockService;

/**
 * @author liupenghui
 * @date 2021/11/24 6:30 下午
 */
@Component
@Slf4j
@RocketMQMessageListener(consumerGroup = "tx_stock_group", topic = "topic_txmsg")
public class StockTxMessageConsumer implements RocketMQListener<String> {

    private final StockService stockService;
    private final RocketMQTemplate rocketMQTemplate;


    @Autowired
    public StockTxMessageConsumer(StockService stockService, RocketMQTemplate rocketMQTemplate) {
        this.stockService = stockService;
        this.rocketMQTemplate = rocketMQTemplate;
    }

    @SneakyThrows
    @Override
    public void onMessage(String s) {
        // 根据消息扣减库存
        log.info("开始扣减库存");
        MqWrapperBean mqWrapperBean = deserialization(s);
        MqConsumerProcessor.process(mqWrapperBean, new MqConsumer() {
            @Override
            public void consume(Object o) {
                try {
                    stockService.deleteStock(getTxMessage(o.toString())) ;
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info("发生异常，通知上游回滚");
                    rollback((TxMessage) o);
                }
            }

            @Override
            public RocketMQLocalTransactionState localTransactionConsume(Object o) {
                return null;
            }
        });


    }

    private void rollback(TxMessage txMessage) {
        String jsonString = JSONObject.toJSONString(txMessage);
        Message<String> msg = MessageBuilder.withPayload(jsonString).build();
        rocketMQTemplate.asyncSend("topic_tx_rollback_msg", msg, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("回滚消息发送结果：[{}]", sendResult.getSendStatus());
            }

            @Override
            public void onException(Throwable throwable) {
                throwable.printStackTrace();
                log.error("回滚消息发送异常");
            }
        });
    }

    private MqWrapperBean deserialization(String msg) {
        return JSONObject.parseObject(msg, MqWrapperBean.class);
    }

    private TxMessage getTxMessage(String message) {
        return JSONObject.parseObject(message, TxMessage.class);
    }
}
