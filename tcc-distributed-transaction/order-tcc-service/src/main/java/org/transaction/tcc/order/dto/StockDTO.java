package org.transaction.tcc.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author liupenghui
 * @date 2021/12/17 4:37 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDTO implements Serializable {

    private String orderNo;

    private Long productId;

    private Integer payCount;

    private String txNo;
}

