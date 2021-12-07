package org.transaction.tcc.order.dto;

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
public class SubmitOrderDTO implements Serializable {

    private Long productId;

    private Integer payCount;
}
