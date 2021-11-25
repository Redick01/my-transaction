package org.transaction.order.entity;

import java.util.Date;

public class TxLog {
    private Long id;

    private String txNo;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTxNo() {
        return txNo;
    }

    public void setTxNo(String txNo) {
        this.txNo = txNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "TxLog{" +
                "id=" + id +
                ", txNo='" + txNo + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}