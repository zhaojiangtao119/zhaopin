package com.labelwall.course.service;

import com.labelwall.common.ResponseObject;
import com.labelwall.course.entity.CourseSection;
import com.labelwall.course.entity.UserCourseSection;
import com.labelwall.mall.dto.UserDto;

/**
 * Created by Administrator on 2017-12-14.
 */
public interface IUserCourseSectionService {
    /**
     * 获取用户的对该课程的学习记录
     *
     * @param userId
     * @param courseId
     * @return
     */
    ResponseObject<CourseSection> getCurrentRecord(Integer userId, Integer courseId);

    /**
     * 记录新的学习记录
     *
     * @param userDto
     * @param userCourseSection
     * @return
     */
    ResponseObject createNewRecord(UserDto userDto, UserCourseSection userCourseSection);
}
