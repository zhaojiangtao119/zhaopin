package com.labelwall.mall.entity;

public class County {
    private Integer id;

    private Integer cityId;

    private String name;

    private Integer provinceId;

    public County(Integer id, Integer cityId, String name, Integer provinceId) {
        this.id = id;
        this.cityId = cityId;
        this.name = name;
        this.provinceId = provinceId;
    }

    public County() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }
}