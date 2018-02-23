package com.labelwall.activity.entity;

import java.util.Date;

/**
 * Created by Administrator on 2018-02-23.
 */
public class ActivityAccountTradeHistory extends BaseBean {

    private Integer accountId;
    private Integer tradeType;
    private Integer jindouNum;
    private Date createTime;
    private Integer orderId;
    private Integer userId;
    private Integer orderType;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    public Integer getJindouNum() {
        return jindouNum;
    }

    public void setJindouNum(Integer jindouNum) {
        this.jindouNum = jindouNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }
}
