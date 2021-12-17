package org.transaction.tcc.stock.entity;

import java.util.Date;

public class Stock {
    private Long id;

    private Long productId;

    private Integer totalCount;

    private Integer freezenCount;

    private Date createTime;

    public Integer getFreezenCount() {
        return freezenCount;
    }

    public void setFreezenCount(Integer freezenCount) {
        this.freezenCount = freezenCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", productId=" + productId +
                ", totalCount=" + totalCount +
                ", freezenCount=" + freezenCount +
                ", createTime=" + createTime +
                '}';
    }
}