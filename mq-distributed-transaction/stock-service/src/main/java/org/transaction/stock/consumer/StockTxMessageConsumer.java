package org.transaction.stock.consumer;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.transaction.stock.consumer.message.TxMessage;

/**
 * @author liupenghui
 * @date 2021/11/24 6:30 下午
 */
@Component
@Slf4j
@RocketMQMessageListener(consumerGroup = "tx_stock_group", topic = "topic_txmsg")
public class StockTxMessageConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String s) {
        // 根据消息扣减库存
    }

    private TxMessage deserialization(String msg) {
        JSONObject jsonObject = JSONObject.parseObject(msg);
        String txStr = jsonObject.getString("txMessage");
        return JSONObject.parseObject(txStr, TxMessage.class);
    }
}
