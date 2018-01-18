package com.labelwall.course.entity;

import com.labelwall.course.entity.base.BaseEntity;

/**
 * Created by Administrator on 2017-12-26.机构信息实体
 */
public class InstitutionCourseware extends BaseEntity {

    private String name;
    private Integer institutionId;
    private String description;
    private String downloadUrl;
    private String classify;
    private String classifyName;
    private String subClassify;
    private String subClassifyName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(Integer institutionId) {
        this.institutionId = institutionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public String getSubClassify() {
        return subClassify;
    }

    public void setSubClassify(String subClassify) {
        this.subClassify = subClassify;
    }

    public String getSubClassifyName() {
        return subClassifyName;
    }

    public void setSubClassifyName(String subClassifyName) {
        this.subClassifyName = subClassifyName;
    }
}
