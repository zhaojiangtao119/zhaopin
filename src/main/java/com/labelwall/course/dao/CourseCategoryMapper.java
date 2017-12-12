package com.labelwall.course.dao;

import com.labelwall.course.entity.CourseCategory;

public interface CourseCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CourseCategory record);

    int insertSelective(CourseCategory record);

    CourseCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CourseCategory record);

    int updateByPrimaryKey(CourseCategory record);
}