package com.labelwall.course.service;

import com.github.pagehelper.PageInfo;
import com.labelwall.common.ResponseObject;
import com.labelwall.course.dto.CourseQueryDto;
import com.labelwall.course.entity.Course;

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
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResponseObject<PageInfo> getCourse(CourseQueryDto courseQueryDto, Integer pageNum, Integer pageSize);
}
