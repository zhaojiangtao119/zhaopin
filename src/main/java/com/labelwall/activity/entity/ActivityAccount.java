package com.labelwall.activity.entity;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018-02-22.
 */
public class ActivityAccount extends BaseBean {
    private Long id;//  账户ID
    private Long userId;//   用户ID
    private BigDecimal balance;//账户余额
    private String createTime;//创建时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
