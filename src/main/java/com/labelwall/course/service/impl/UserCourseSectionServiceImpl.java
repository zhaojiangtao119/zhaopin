package com.labelwall.course.service.impl;

import com.labelwall.common.ResponseObject;
import com.labelwall.common.ResponseStatus;
import com.labelwall.course.dao.UserCourseSectionMapper;
import com.labelwall.course.entity.CourseSection;
import com.labelwall.course.entity.UserCourseSection;
import com.labelwall.course.service.ICourseSectionService;
import com.labelwall.course.service.IUserCourseSectionService;
import com.labelwall.mall.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017-12-14.
 */
@Service("userCourseSectionService")
public class UserCourseSectionServiceImpl implements IUserCourseSectionService {

    @Autowired
    private UserCourseSectionMapper userCourseSectionMapper;
    @Autowired
    private ICourseSectionService courseSectionService;

    @Override
    public ResponseObject<CourseSection> getCurrentRecord(Integer userId, Integer courseId) {
        if (userId == null || courseId == null) {
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(),
                    ResponseStatus.ERROR_PARAM.getValue());
        }
        UserCourseSection userCourseSection = new UserCourseSection();
        userCourseSection.setUserId(userId);
        userCourseSection.setCourseId(courseId);
        //
        userCourseSection = userCourseSectionMapper.selectLatest(userCourseSection);
        if (userCourseSection == null) {
            //没有该课程的学习记录
            return ResponseObject.successStautsData(null);
        }
        //获取章节对象
        CourseSection courseSection = courseSectionService.selectByPrimaryKey(userCourseSection.getSectionId());
        return ResponseObject.successStautsData(courseSection);
    }

    @Override
    public ResponseObject createNewRecord(UserDto userDto, UserCourseSection userCourseSectionNew) {
        if (userCourseSectionNew.getCourseId() == null || userCourseSectionNew.getSectionId() == null) {
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(),
                    ResponseStatus.ERROR_PARAM.getValue());
        }
        //首先查询是否存在这条学习记录，
        userCourseSectionNew.setUserId(userDto.getId());
        userCourseSectionNew.setCreateUser(userDto.getUsername());
        userCourseSectionNew.setUpdateUser(userDto.getUsername());
        UserCourseSection userCourseSectionOld = userCourseSectionMapper.selectLatest(userCourseSectionNew);
        if (userCourseSectionOld == null) {
            //新建一条学习记录
            userCourseSectionMapper.insertSelective(userCourseSectionNew);
            return ResponseObject.successStatus();
        } else {
            //修改学习记录的时间
            userCourseSectionMapper.updateTimeById(userCourseSectionOld);
            return ResponseObject.successStatus();
        }
    }
}
