package com.labelwall.mall.entity;

import java.io.Serializable;
import java.util.Date;

public class Shop implements Serializable {
    private Integer id;

    private Integer userId;

    private String name;

    private String description;

    private Integer productCategoryId;

    private Integer status;

    private Integer level;

    private Integer provinceId;
    private Integer cityId;
    private Integer countyId;

    private Date createTime;

    private Date updateTime;


    public Shop(Integer id, Integer userId, String name, String description,
                Integer productCategoryId, Integer status, Integer level,
                Integer provinceId, Integer cityId, Integer countyId,
                Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.productCategoryId = productCategoryId;
        this.status = status;
        this.level = level;
        this.provinceId = provinceId;
        this.cityId = cityId;
        this.countyId = countyId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Shop() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Integer productCategoryId) {
        this.productCategoryId = productCategoryId;
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
}