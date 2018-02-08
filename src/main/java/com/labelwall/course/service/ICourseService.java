package com.labelwall.course.service;

import com.github.pagehelper.PageInfo;
import com.labelwall.common.ResponseObject;
import com.labelwall.course.dto.CourseDto;
import com.labelwall.course.dto.CourseQueryDto;
import com.labelwall.course.entity.Course;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017-12-13.
 */
public interface ICourseService {
    /**
     * 获取5门推荐的免费课程
     *
     * @return
     */
    ResponseObject<List<Course>> getCommendCourse(Integer free);

    /**
     * 获取课程
     *
     * @param courseQueryDto 搜索条件
     *                       排序（sortField:最新，最热）
     *                       搜索条件：keyword(关键字)，classify/subClassify,free(免费与否)
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResponseObject<PageInfo> getCourseList(CourseQueryDto courseQueryDto, Integer pageNum, Integer pageSize);

    /**
     * 获取课程详情
     *
     * @param id
     * @return
     */
    ResponseObject<CourseDto> getCourse(Integer id);

    /**
     * 根据主键获取课程信息
     *
     * @param id
     * @return
     */
    Course selectByPrimaryKey(Integer id);

    /**
     * 根据id集合获取课程信息
     *
     * @param courseIdList
     * @return
     */
    List<Course> selectByCourseIds(List<Integer> courseIdList);

    /**
     * 获取机构的课程
     *
     * @param courseQueryDto
     * @return
     */
    ResponseObject<PageInfo> getInstitutionCourse(CourseQueryDto courseQueryDto,
                                                  Integer pageNum, Integer pageSize);

    /**
     * 获取某一个教师的课程
     *
     * @param teacherId
     * @return
     */
    ResponseObject<List<Course>> getCoursesByTeacherId(Integer teacherId);
}
