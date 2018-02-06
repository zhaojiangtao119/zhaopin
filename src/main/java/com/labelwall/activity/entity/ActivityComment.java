package com.labelwall.activity.entity;

import java.util.Date;

public class ActivityComment extends BaseBean{
    private Integer id;

    private Integer activityId;

    private Integer userId;

    private String username;

    private String content;

    private String img;

    private Date createTime;

    private Date updateTime;

    public ActivityComment(Integer id, Integer activityId, Integer userId, String username, String content, String img, Date createTime, Date updateTime) {
        this.id = id;
        this.activityId = activityId;
        this.userId = userId;
        this.username = username;
        this.content = content;
        this.img = img;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public ActivityComment() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
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