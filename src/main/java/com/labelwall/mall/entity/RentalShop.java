package com.labelwall.mall.entity;

import java.util.Date;

public class RentalShop {
	private Integer id;

    private Integer userId;

    private String name;

    private String description;

    private Integer status;

    private Integer level;

    private Integer provinceId;
    private Integer cityId;
    private Integer countyId;

    private Date createTime;
    private Date updateTime;
    
    private Integer locationType;// 业务地区 0校园 1社区
    private String locationName; 
    private Integer schoolId  ;// 学校ID                    
    private String schoolName ;//学校名称
    private String phone;
    private String topImgURL;//主题图片
    private Integer rentalType;//租售的产品种类
    
    
	public Integer getRentalType() {
		return rentalType;
	}
	public void setRentalType(Integer rentalType) {
		this.rentalType = rentalType;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
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
	public Integer getLocationType() {
		return locationType;
	}
	public void setLocationType(Integer locationType) {
		this.locationType = locationType;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public Integer getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTopImgURL() {
		return topImgURL;
	}
	public void setTopImgURL(String topImgURL) {
		this.topImgURL = topImgURL;
	}
    
    
    
}
