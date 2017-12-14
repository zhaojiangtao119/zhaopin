package com.labelwall.course.service.impl;

import com.labelwall.common.ResponseObject;
import com.labelwall.common.ResponseStatus;
import com.labelwall.course.dao.CourseMapper;
import com.labelwall.course.dao.TeacherFollowsMapper;
import com.labelwall.course.entity.Course;
import com.labelwall.course.entity.TeacherFollows;
import com.labelwall.course.service.ICourseService;
import com.labelwall.course.service.ITeacherFollowsService;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017-12-14.
 */
@Service("teacherFollowsService")
public class TeacherFollowsServiceImpl implements ITeacherFollowsService {

    @Autowired
    private TeacherFollowsMapper teacherFollowsMapper;
    @Autowired
    private ICourseService courseService;
    @Autowired
    private IUserService userService;

    @Override
    public ResponseObject<Boolean> isFollows(Integer currentUserId, Integer courseId) {
        if (courseId == null || currentUserId == null) {
            return ResponseObject.failStatusMessage(ResponseStatus.ERROR_PARAM.getValue());
        }
        Course course = courseService.selectByPrimaryKey(courseId);
        if (course == null) {
            return ResponseObject.failStatusMessage(ResponseStatus.ERROR_PARAM.getValue());
        }
        //获取教师用户对象
        UserDto userDto = userService.selectByUsername(course.getUsername());
        TeacherFollows teacherFollows = teacherFollowsMapper.isFollows(currentUserId, userDto.getId());
        if (teacherFollows == null) {//未关注
            return ResponseObject.successStautsData(false);
        }
        return ResponseObject.successStautsData(true);
    }

    @Override
    public ResponseObject doFollows(UserDto currentUserDto, Integer courseId) {
        Integer currentUserId = currentUserDto.getId();
        if (courseId == null || currentUserId == null) {
            return ResponseObject.failStatusMessage(ResponseStatus.ERROR_PARAM.getValue());
        }
        Course course = courseService.selectByPrimaryKey(courseId);
        if (course == null) {
            return ResponseObject.failStatusMessage(ResponseStatus.ERROR_PARAM.getValue());
        }
        //获取教师用户对象
        UserDto teacherUserDto = userService.selectByUsername(course.getUsername());
        TeacherFollows teacherFollows = teacherFollowsMapper.isFollows(currentUserId, teacherUserDto.getId());
        if (teacherFollows == null) {
            //未关注该教师，点击后关注（插入记录）
            teacherFollows = new TeacherFollows();
            teacherFollows.setUserId(currentUserId);
            teacherFollows.setFollowId(teacherUserDto.getId());
            teacherFollows.setCreateUser(currentUserDto.getUsername());
            teacherFollows.setUpdateUser(currentUserDto.getUsername());
            teacherFollowsMapper.insertSelective(teacherFollows);
            return ResponseObject.successStatusMessage("关注成功");
        } else {
            //已经关注，点击后取消关注（删除记录）
            teacherFollowsMapper.deleteByPrimaryKey(teacherFollows.getId());
            return ResponseObject.successStatusMessage("取消关注成功");
        }
    }
}
