package com.labelwall.course.entity;

import java.util.Date;

public class CourseComment {
    private Integer id;

    private String username;

    private String toUsername;

    private Integer courseId;

    private Integer sectionId;

    private String sectionTitle;

    private String content;

    private Integer refId;

    private String refContent;
    /**
     * 类型，0-评论 1-问答
     */
    private Integer type;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private Integer del;

    public CourseComment(Integer id, String username, String toUsername,
                         Integer courseId, Integer sectionId, String sectionTitle,
                         String content, Integer refId, String refContent, Integer type,
                         Date createTime, String createUser, Date updateTime,
                         String updateUser, Integer del) {
        this.id = id;
        this.username = username;
        this.toUsername = toUsername;
        this.courseId = courseId;
        this.sectionId = sectionId;
        this.sectionTitle = sectionTitle;
        this.content = content;
        this.refId = refId;
        this.refContent = refContent;
        this.type = type;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.del = del;
    }

    public CourseComment() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getToUsername() {
        return toUsername;
    }

    public void setToUsername(String toUsername) {
        this.toUsername = toUsername == null ? null : toUsername.trim();
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle == null ? null : sectionTitle.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getRefId() {
        return refId;
    }

    public void setRefId(Integer refId) {
        this.refId = refId;
    }

    public String getRefContent() {
        return refContent;
    }

    public void setRefContent(String refContent) {
        this.refContent = refContent == null ? null : refContent.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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