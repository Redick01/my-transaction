package org.transaction.tcc.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author liupenghui
 * @date 2021/11/25 11:13 上午
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
