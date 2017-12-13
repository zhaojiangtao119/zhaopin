package com.labelwall.course.dao;

import com.labelwall.course.entity.CourseSection;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseSectionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CourseSection record);

    int insertSelective(CourseSection record);

    CourseSection selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CourseSection record);

    int updateByPrimaryKey(CourseSection record);

    /**
     * 根据课程id获取章节
     *
     * @param courseId
     * @return
     */
    List<CourseSection> selectByCourseId(@Param("courseId") Integer courseId);
}