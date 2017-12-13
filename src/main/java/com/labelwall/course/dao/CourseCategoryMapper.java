package com.labelwall.course.dao;

import com.labelwall.course.entity.CourseCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CourseCategory record);

    int insertSelective(CourseCategory record);

    CourseCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CourseCategory record);

    int updateByPrimaryKey(CourseCategory record);

    /**
     * 获取课程分类
     *
     * @param code
     * @return
     */
    List<CourseCategory> getCourseCategory(@Param("code") String code);

    /**
     * 根据code获取课程分类对象
     *
     * @param code
     * @return
     */
    CourseCategory selectByCode(@Param("code") String code);

    /**
     * 通过parentCode获取分类集合
     *
     * @param code
     * @return
     */
    List<CourseCategory> selectByParentCode(@Param("code") String code);
}