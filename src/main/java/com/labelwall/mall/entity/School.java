package com.labelwall.mall.entity;

public class School {
    private Integer id;

    private Integer provinceId;

    private String provinceName;

    private String name;

    private String introduction;

    private String url;

    public School(Integer id, Integer provinceId, String provinceName, String name, String introduction, String url) {
        this.id = id;
        this.provinceId = provinceId;
        this.provinceName = provinceName;
        this.name = name;
        this.introduction = introduction;
        this.url = url;
    }

    public School() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName == null ? null : provinceName.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
}