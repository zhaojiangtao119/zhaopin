package com.labelwall.course.service;

import com.labelwall.common.ResponseObject;
import com.labelwall.mall.dto.UserDto;

/**
 * Created by Administrator on 2017-12-14.
 */
public interface ITeacherFollowsService {
    /**
     * 判断用户是否关注了课程讲师
     *
     * @param userId
     * @param courseId
     * @return
     */
    ResponseObject<Boolean> isFollows(Integer userId, Integer courseId);

    /**
     * 点击“关注”执行的动作
     * @param currentUserDto
     * @param courseId
     * @return
     */
    ResponseObject doFollows(UserDto currentUserDto, Integer courseId);
}
