package org.transaction.stock.consumer;

import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
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
        TxMessage txMessage = deserialization(s);
        try {
            stockService.deleteStock(txMessage);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("发生异常，通知上游回滚");
            rollback(txMessage);
        }

    }

    private TxMessage deserialization(String msg) {
        return JSONObject.parseObject(msg, TxMessage.class);
    }

    private void rollback(TxMessage txMessage) {
        String jsonString = JSONObject.toJSONString(txMessage);
        Message<String> msg = MessageBuilder.withPayload(jsonString).build();
        rocketMQTemplate.sendMessageInTransaction("tx_order_group", "topic_txmsg", msg, null);
    }
}
