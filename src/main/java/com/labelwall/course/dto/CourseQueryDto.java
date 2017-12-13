package com.labelwall.course.dto;

import com.labelwall.course.entity.Course;

/**
 * Created by Administrator on 2017-12-13.
 */
public class CourseQueryDto extends Course {
    /**
     * 排序字段
     */
    private String sortField;
    /**
     * 课程搜索关键字
     */
    private String keyword;

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
