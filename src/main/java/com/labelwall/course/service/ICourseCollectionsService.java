package com.labelwall.course.service;

import com.labelwall.common.ResponseObject;
import com.labelwall.mall.dto.UserDto;

/**
 * Created by Administrator on 2017-12-13.
 */
public interface ICourseCollectionsService {
    /**
     * 判断当前用户是否收藏了该课程
     *
     * @param userId
     * @param courseId
     * @return
     */
    ResponseObject<Boolean> isCollection(Integer userId, Integer courseId);

    /**
     * 点击收藏按钮执行的动作
     *
     * @param userDto
     * @param courseId
     * @return
     */
    ResponseObject doCollection(UserDto userDto, Integer courseId);
}
