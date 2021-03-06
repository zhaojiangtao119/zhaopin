package com.labelwall.course.entity;

import java.util.Date;

public class CourseSection {
    private Integer id;

    private Integer courseId;

    private Integer parentId;

    private String name;

    private Integer sort;

    private String time;
    /**
     * 未上架（0）、上架（1）
     **/
    private Integer onsale;

    private String videoUrl;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private Integer del;

    private String sectionDesc;

    public CourseSection(Integer id, Integer courseId, Integer parentId, String name, Integer sort, String time, Integer onsale, String videoUrl, Date createTime, String createUser, Date updateTime, String updateUser, Integer del, String sectionDesc) {
        this.id = id;
        this.courseId = courseId;
        this.parentId = parentId;
        this.name = name;
        this.sort = sort;
        this.time = time;
        this.onsale = onsale;
        this.videoUrl = videoUrl;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.del = del;
        this.sectionDesc = sectionDesc;
    }

    public CourseSection() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

    public Integer getOnsale() {
        return onsale;
    }

    public void setOnsale(Integer onsale) {
        this.onsale = onsale;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl == null ? null : videoUrl.trim();
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

    public String getSectionDesc() {
        return sectionDesc;
    }

    public void setSectionDesc(String sectionDesc) {
        this.sectionDesc = sectionDesc == null ? null : sectionDesc.trim();
    }
}