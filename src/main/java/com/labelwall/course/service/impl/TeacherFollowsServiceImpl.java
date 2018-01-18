package com.labelwall.course.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.labelwall.common.Const;
import com.labelwall.common.ResponseObject;
import com.labelwall.common.ResponseStatus;
import com.labelwall.course.dao.CourseMapper;
import com.labelwall.course.dao.TeacherFollowsMapper;
import com.labelwall.course.entity.Course;
import com.labelwall.course.entity.InstitutionTeacher;
import com.labelwall.course.entity.TeacherFollows;
import com.labelwall.course.service.ICourseService;
import com.labelwall.course.service.IInstitutionTeacherService;
import com.labelwall.course.service.ITeacherFollowsService;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.entity.User;
import com.labelwall.mall.service.IUserService;
import com.labelwall.util.storage.QiniuStorage;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    private IInstitutionTeacherService institutionTeacherService;

    @Override
    public ResponseObject<Boolean> isFollows(Integer currentUserId, Integer courseId) {
        if (courseId == null || currentUserId == null) {
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(),
                    ResponseStatus.ERROR_PARAM.getValue());
        }
        Course course = courseService.selectByPrimaryKey(courseId);
        if (course == null) {
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(),
                    ResponseStatus.ERROR_PARAM.getValue());
        }
        //获取教师用户对象
        InstitutionTeacher institutionTeacher = institutionTeacherService.selectByPrimaryKey(course.getTeacherId());
        TeacherFollows teacherFollows = teacherFollowsMapper.isFollows(currentUserId, institutionTeacher.getId().intValue());
        if (teacherFollows == null) {//未关注
            return ResponseObject.successStautsData(false);
        }
        return ResponseObject.successStautsData(true);
    }

    @Override
    public ResponseObject doFollows(UserDto currentUserDto, Integer courseId) {
        Integer currentUserId = currentUserDto.getId();
        if (courseId == null || currentUserId == null) {
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(),
                    ResponseStatus.ERROR_PARAM.getValue());
        }
        Course course = courseService.selectByPrimaryKey(courseId);
        if (course == null) {
            return ResponseObject.fail(ResponseStatus.ERROR_PARAM.getCode(),
                    ResponseStatus.ERROR_PARAM.getValue());
        }
        //获取教师用户对象
        InstitutionTeacher institutionTeacher = institutionTeacherService.selectByPrimaryKey(course.getTeacherId());
        TeacherFollows teacherFollows = teacherFollowsMapper.isFollows(currentUserId, institutionTeacher.getId().intValue());
        if (teacherFollows == null) {
            //未关注该教师，点击后关注（插入记录）
            teacherFollows = new TeacherFollows();
            teacherFollows.setUserId(currentUserId);
            teacherFollows.setFollowId(institutionTeacher.getId().intValue());
            teacherFollows.setCreateUser(currentUserDto.getUsername());
            teacherFollows.setUpdateUser(currentUserDto.getUsername());
            teacherFollowsMapper.insertSelective(teacherFollows);
            return ResponseObject.successStatus();
        } else {
            //已经关注，点击后取消关注（删除记录）
            teacherFollowsMapper.deleteByPrimaryKey(teacherFollows.getId());
            return ResponseObject.successStatus();
        }
    }

    @Override
    public ResponseObject<PageInfo> getTeacher(Integer userId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        //获取当前用户的关注的id集合
        List<TeacherFollows> teacherFollowsList = teacherFollowsMapper.selectByUserId(userId);
        List<Integer> userIdList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(teacherFollowsList)) {
            return ResponseObject.successStautsData(null);
        }
        for (TeacherFollows item : teacherFollowsList) {
            userIdList.add(item.getFollowId());
        }
        //获取关注教师集合信息
        List<InstitutionTeacher> institutionTeacherList = institutionTeacherService.selectByUserIds(userIdList);
        //加载用户头像
        /*for (InstitutionTeacher item : institutionTeacherList) {
            if (StringUtils.isBlank(user.getHead())) {
                user.setHead(QiniuStorage.getUserHeadUrl(Const.DEFAULT_USER_HEAD));
            } else {
                user.setHead(QiniuStorage.getUserHeadUrl(user.getHead()));
            }
        }*/
        PageInfo pageInfo = new PageInfo(institutionTeacherList);
        return ResponseObject.successStautsData(pageInfo);
    }

}
