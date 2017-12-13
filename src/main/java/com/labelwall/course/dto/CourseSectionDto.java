package com.labelwall.course.dto;

import com.google.common.collect.Lists;
import com.labelwall.course.entity.CourseSection;

import java.util.List;

/**
 * Created by Administrator on 2017-12-13.
 */
public class CourseSectionDto extends CourseSection {

    List<CourseSection> courseSubSectionList = Lists.newArrayList();//小节的集合

    public List<CourseSection> getCourseSubSectionList() {
        return courseSubSectionList;
    }

    public void setCourseSubSectionList(List<CourseSection> courseSubSectionList) {
        this.courseSubSectionList = courseSubSectionList;
    }
}
