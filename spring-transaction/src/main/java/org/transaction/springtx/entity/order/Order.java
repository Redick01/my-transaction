package org.transaction.springtx.entity.order;

import java.util.Date;

/**
 * @author liupenghui
 * @date 2021/12/1 3:31 下午
 */
public class Order {

    private Long id;

    private String orderNo;

    private Long productId;

    private Integer payCount;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getPayCount() {
        return payCount;
    }

    public void setPayCount(Integer payCount) {
        this.payCount = payCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderNo='" + orderNo + '\'' +
                ", productId=" + productId +
                ", payCount=" + payCount +
                ", createTime=" + createTime +
                '}';
    }
}
