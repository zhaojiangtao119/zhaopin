package com.labelwall.mall.entity;

import java.util.Date;

public class UserFollows {
    private Integer id;

    private Integer userId;

    private Integer followId;

    private Date createTime;

    private Date updateTime;

    public UserFollows(Integer id, Integer userId, Integer followId, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.followId = followId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public UserFollows() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFollowId() {
        return followId;
    }

    public void setFollowId(Integer followId) {
        this.followId = followId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}