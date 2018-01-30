package com.labelwall.mall.entity;

import java.io.Serializable;
import java.util.Date;

public class TopicPost implements Serializable {
    private Integer id;

    private String title;

    private Integer userId;

    private Integer schoolId;

    private Integer topicId;

    private Integer replyNum;

    private String image;

    private Integer likeNum;

    private Integer dislikeNum;

    private Date createTime;

    private Date updateTime;

    private String content;

    private Integer provinceId;
    private Integer cityId;
    private Integer countyId;

    public TopicPost(Integer id, String title, String content, Integer userId,
                     Integer schoolId, Integer topicId, Integer replyNum,
                     String image, Integer likeNum, Integer dislikeNum,
                     Date createTime, Date updateTime, Integer provinceId, Integer cityId, Integer countyId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.schoolId = schoolId;
        this.topicId = topicId;
        this.replyNum = replyNum;
        this.image = image;
        this.likeNum = likeNum;
        this.dislikeNum = dislikeNum;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.provinceId = provinceId;
        this.cityId = cityId;
        this.countyId = countyId;

    }

    public TopicPost() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public Integer getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(Integer replyNum) {
        this.replyNum = replyNum;
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

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCountyId() {
        return countyId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }
}