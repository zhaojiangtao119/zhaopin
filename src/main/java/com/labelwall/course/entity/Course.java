package com.labelwall.course.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Course {
    private Integer id;

    private String name;

    private String type;

    private String classify;

    private String classifyName;

    private String subClassify;

    private String subClassifyName;

    private String direction;

    private String username;
    /**
     * 课程级别 1-初级，2-中级，3-高级
     */
    private Integer level;
    /**
     * 是否免费 0-付费，1-免费
     */
    private Integer free;

    private BigDecimal price;

    private String time;
    /**
     * 是否上架 0-未上架，1-已上架
     */
    private Integer onsale;

    private String picture;

    private String brief;
    /**
     * 是否推荐 0-未推荐，1-推荐
     */
    private Integer recommend;

    private Integer weight;

    private Integer studyCount;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;
    /**
     * 逻辑删除
     */
    private Integer del;

    private String courseUrl;

    private Integer provinceId;
    private Integer cityId;
    private Integer countyId;

    private Integer institutionId;
    private Integer teacherId;

    public Course(Integer id, String name, String type, String classify,
                  String classifyName, String subClassify, String subClassifyName,
                  String direction, String username, Integer level, Integer free,
                  BigDecimal price, String time, Integer onsale, String picture,
                  String brief, Integer recommend, Integer weight, Integer studyCount,
                  Date createTime, String createUser, Date updateTime, String updateUser,
                  Integer del, String courseUrl, Integer provinceId, Integer cityId,
                  Integer countyId,Integer institutionId,Integer teacherId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.classify = classify;
        this.classifyName = classifyName;
        this.subClassify = subClassify;
        this.subClassifyName = subClassifyName;
        this.direction = direction;
        this.username = username;
        this.level = level;
        this.free = free;
        this.price = price;
        this.time = time;
        this.onsale = onsale;
        this.picture = picture;
        this.brief = brief;
        this.recommend = recommend;
        this.weight = weight;
        this.studyCount = studyCount;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.del = del;
        this.courseUrl = courseUrl;
        this.provinceId = provinceId;
        this.cityId = cityId;
        this.countyId = countyId;
        this.institutionId = institutionId;
        this.teacherId = teacherId;
    }

    public Course() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify == null ? null : classify.trim();
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName == null ? null : classifyName.trim();
    }

    public String getSubClassify() {
        return subClassify;
    }

    public void setSubClassify(String subClassify) {
        this.subClassify = subClassify == null ? null : subClassify.trim();
    }

    public String getSubClassifyName() {
        return subClassifyName;
    }

    public void setSubClassifyName(String subClassifyName) {
        this.subClassifyName = subClassifyName == null ? null : subClassifyName.trim();
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction == null ? null : direction.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getFree() {
        return free;
    }

    public void setFree(Integer free) {
        this.free = free;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture == null ? null : picture.trim();
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief == null ? null : brief.trim();
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getStudyCount() {
        return studyCount;
    }

    public void setStudyCount(Integer studyCount) {
        this.studyCount = studyCount;
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

    public String getCourseUrl() {
        return courseUrl;
    }

    public void setCourseUrl(String courseUrl) {
        this.courseUrl = courseUrl == null ? null : courseUrl.trim();
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

    public Integer getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(Integer institutionId) {
        this.institutionId = institutionId;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }
}