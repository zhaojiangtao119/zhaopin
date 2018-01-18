package com.labelwall.course.dao;

import com.labelwall.common.ResponseObject;
import com.labelwall.course.dto.CourseQueryDto;
import com.labelwall.course.entity.Course;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Course record);

    int insertSelective(Course record);

    Course selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);

    /**
     * 获取推荐的免费课程
     *
     * @return
     */
    List<Course> getCommendCourse(@Param("free") Integer free);

    /**
     * 获取课程（排序：sortFiel(最新：update_time,最热：study_count)，搜索条件：keyword，category(一级分类、二级分类)）
     *
     * @param courseQueryDto
     * @return
     */
    List<Course> getCourseList(CourseQueryDto courseQueryDto);

    /**
     * 获取课程信息，通过主键集合ids
     *
     * @param courseIdList
     * @return
     */
    List<Course> selectByCourseIds(@Param("courseIdList") List<Integer> courseIdList);

    /**
     * 获取机构的课程
     *
     * @param courseQueryDto
     * @return
     */
    List<Course> getInstitutionCourse(CourseQueryDto courseQueryDto);

    /**
     * 获取某一个教师的课程
     *
     * @param teacherId
     * @return
     */
    List<Course> getCourseByTeacherId(Integer teacherId);
}