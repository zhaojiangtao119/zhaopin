package com.labelwall.course.entity;

import java.util.Date;

/**
 * 用户关注教师的实体类
 */
public class TeacherFollows {
    private Integer id;
    /**
     * 用户的id
     */
    private Integer userId;
    /**
     * 教师的id
     */
    private Integer followId;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private Integer del;

    public TeacherFollows(Integer id, Integer userId, Integer followId, Date createTime,
                          String createUser, Date updateTime, String updateUser, Integer del) {
        this.id = id;
        this.userId = userId;
        this.followId = followId;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.del = del;
    }

    public TeacherFollows() {
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