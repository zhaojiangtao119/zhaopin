package com.labelwall.course.entity;

import java.util.Date;

public class CourseCollections {
    private Integer id;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 收藏的类型
     */
    private Integer classify;
    /**
     * 收藏对象的标识
     */
    private Integer objectId;
    /**
     * 用户收藏的备注
     */
    private String tips;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private Integer del;

    public CourseCollections(Integer id, Integer userId, Integer classify,
                             Integer objectId, String tips, Date createTime,
                             String createUser, Date updateTime, String updateUser, Integer del) {
        this.id = id;
        this.userId = userId;
        this.classify = classify;
        this.objectId = objectId;
        this.tips = tips;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.del = del;
    }

    public CourseCollections() {
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

    public Integer getClassify() {
        return classify;
    }

    public void setClassify(Integer classify) {
        this.classify = classify;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips == null ? null : tips.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Integer getDel() {
        return del;
    }

    public void setDel(Integer del) {
        this.del = del;
    }
}