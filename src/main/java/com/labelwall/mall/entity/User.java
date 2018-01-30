package com.labelwall.mall.entity;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private Integer id;

    private String username;

    private String password;

    private String head;

    private String email;

    private String phone;

    private Integer role;

    private Date schoolDate;

    private String schoolName;

    private String locationProvince;

    private String locationCity;

    private String locationCounty;

    private Integer provinceId;

    private Integer cityId;

    private Integer countyId;

    private Integer schoolId;

    private Date createTime;

    private Date updateTime;

    private Integer gender;

    private String birthday;

    public User(Integer id, String username, String password,
                String head, String email, String phone, Integer role,
                Date schoolDate, String schoolName, String locationProvince,
                String locationCity, String locationCounty, Integer provinceId,
                Integer cityId, Integer countyId, Integer schoolId, Date createTime, Date updateTime,
                Integer gender, String birthday) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.head = head;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.schoolDate = schoolDate;
        this.schoolName = schoolName;
        this.locationProvince = locationProvince;
        this.locationCity = locationCity;
        this.locationCounty = locationCounty;
        this.provinceId = provinceId;
        this.cityId = cityId;
        this.countyId = countyId;
        this.schoolId = schoolId;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.gender = gender;
        this.birthday = birthday;
    }

    public User() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Date getSchoolDate() {
        return schoolDate;
    }

    public void setSchoolDate(Date schoolDate) {
        this.schoolDate = schoolDate;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName == null ? null : schoolName.trim();
    }

    public String getLocationProvince() {
        return locationProvince;
    }

    public void setLocationProvince(String locationProvince) {
        this.locationProvince = locationProvince == null ? null : locationProvince.trim();
    }

    public String getLocationCity() {
        return locationCity;
    }

    public void setLocationCity(String locationCity) {
        this.locationCity = locationCity == null ? null : locationCity.trim();
    }

    public String getLocationCounty() {
        return locationCounty;
    }

    public void setLocationCounty(String locationCounty) {
        this.locationCounty = locationCounty;
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

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
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

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}