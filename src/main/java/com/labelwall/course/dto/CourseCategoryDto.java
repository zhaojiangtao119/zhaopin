package com.labelwall.course.dto;

import com.google.common.collect.Lists;
import com.labelwall.course.entity.CourseCategory;

import java.util.List;

/**
 * Created by Administrator on 2017-12-12.
 */
public class CourseCategoryDto extends CourseCategory {

    List<CourseCategory> subCourseCategory = Lists.newArrayList();

    public List<CourseCategory> getSubCourseCategory() {
        return subCourseCategory;
    }

    public void setSubCourseCategory(List<CourseCategory> subCourseCategory) {
        this.subCourseCategory = subCourseCategory;
    }
}
