package com.labelwall.mall.entity;

import java.util.Date;

public class TopicPostReply {
    private Integer id;

    private Integer userId;

    private Integer topicPostId;

    private String image;

    private Integer likeNum;

    private Integer dislikeNum;

    private Date createTime;

    private Date updateTime;

    private String content;

    public TopicPostReply(Integer id, Integer userId, Integer topicPostId, String image, Integer likeNum, Integer dislikeNum, Date createTime, Date updateTime, String content) {
        this.id = id;
        this.userId = userId;
        this.topicPostId = topicPostId;
        this.image = image;
        this.likeNum = likeNum;
        this.dislikeNum = dislikeNum;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.content = content;
    }

    public TopicPostReply() {
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

    public Integer getTopicPostId() {
        return topicPostId;
    }

    public void setTopicPostId(Integer topicPostId) {
        this.topicPostId = topicPostId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Integer getDislikeNum() {
        return dislikeNum;
    }

    public void setDislikeNum(Integer dislikeNum) {
        this.dislikeNum = dislikeNum;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}