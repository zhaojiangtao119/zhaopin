package com.labelwall.course.dao;

import com.labelwall.course.entity.UserCourseSection;

public interface UserCourseSectionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserCourseSection record);

    int insertSelective(UserCourseSection record);

    UserCourseSection selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserCourseSection record);

    int updateByPrimaryKey(UserCourseSection record);

    /**
     * 获取用户对某一课程的最新学习记录
     *
     * @param userCourseSection
     * @return
     */
    UserCourseSection selectLatest(UserCourseSection userCourseSection);

    /**
     * 修改更新时间
     *
     * @param userCourseSectionOld
     * @return
     */
    int updateTimeById(UserCourseSection userCourseSectionOld);
}