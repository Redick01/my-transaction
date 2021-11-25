package org.transaction.order.consumer.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author liupenghui
 * @date 2021/11/24 5:41 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TxMessage implements Serializable {

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 商品购买数量
     */
    private Integer payCount;

    /**
     * 全局事务编号
     */
    private String txNo;

    /**
     * 订单号
     */
    private String orderNo;
}
