package com.labelwall.course.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.labelwall.common.Const;
import com.labelwall.common.ResponseObject;
import com.labelwall.course.entity.CourseSection;
import com.labelwall.course.entity.UserCourseSection;
import com.labelwall.course.service.IUserCourseSectionService;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.util.CookieUtil;
import com.labelwall.util.JsonUtil;
import com.labelwall.util.RedisPoolUtil;

/**
 * Created by Administrator on 2017-12-14.
 */
@RestController
@RequestMapping("/study/record/")
public class UserCourseSectionController {

    @Autowired
    private IUserCourseSectionService userCourseSectionService;

    /**
     * 获取当前用户的最新学习记录
     *
     * @param session
     * @param courseId
     * @return
     */
    @RequestMapping(value = "get_current_record/{id}", method = RequestMethod.GET)
    public ResponseObject<CourseSection> getCurrentRecord(HttpServletRequest request,
                                                          @PathVariable("id") Integer courseId) {
    	String loginToken=CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json=RedisPoolUtil.get(loginToken);
    	UserDto userDto=JsonUtil.stringToObj(json, UserDto.class);
        return userCourseSectionService.getCurrentRecord(userDto.getId(), courseId);
    }


    /**
     * 记录最新的学习记录
     *
     * @param session
     * @param userCourseSection
     * @return
     */
    @RequestMapping(value = "create_new_record", method = RequestMethod.POST)
    public ResponseObject createNewRecord(HttpServletRequest request, UserCourseSection userCourseSection) {
    	String loginToken=CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json=RedisPoolUtil.get(loginToken);
    	UserDto userDto=JsonUtil.stringToObj(json, UserDto.class);
        return userCourseSectionService.createNewRecord(userDto, userCourseSection);
    }

}
