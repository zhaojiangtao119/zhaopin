package com.labelwall.course.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.labelwall.common.Const;
import com.labelwall.common.ResponseObject;
import com.labelwall.course.service.ICourseCollectionsService;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.util.CookieUtil;
import com.labelwall.util.JsonUtil;
import com.labelwall.util.RedisPoolUtil;

/**
 * Created by Administrator on 2017-12-13.
 */
@RestController
@RequestMapping("/collections/")
public class CourseCollectionsController {

    @Autowired
    private ICourseCollectionsService courseCollectionsService;

    /**
     * 判断当前用户是否对该课程收藏，若用户未登录，另作处理
     *
     * @param session
     * @param courseId
     * @return
     */
    @RequestMapping(value = "is_collection/{id}", method = RequestMethod.GET)
    public ResponseObject<Boolean> isCollection(HttpServletRequest request,
                                                @PathVariable("id") Integer courseId) {
    	String loginToken=CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json=RedisPoolUtil.get(loginToken);
    	UserDto userDto=JsonUtil.stringToObj(json, UserDto.class);
        return courseCollectionsService.isCollection(userDto.getId(), courseId);
    }

    /**
     * 点击收藏按钮执行的动作
     *
     * @param session
     * @param courseId
     * @return
     */
    @RequestMapping(value = "do_collection/{id}", method = RequestMethod.POST)
    public ResponseObject doCollection(HttpServletRequest request,
                                       @PathVariable("id") Integer courseId) {
    	String loginToken=CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json=RedisPoolUtil.get(loginToken);
    	UserDto userDto=JsonUtil.stringToObj(json, UserDto.class);
        return courseCollectionsService.doCollection(userDto, courseId);
    }

    /**
     * 获取用户收藏的课程
     *
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "get_course/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public ResponseObject<PageInfo> getCourse(HttpServletRequest request,
                                              @PathVariable("pageNum") Integer pageNum,
                                              @PathVariable("pageSize") Integer pageSize) {
    	String loginToken=CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json=RedisPoolUtil.get(loginToken);
    	UserDto userDto=JsonUtil.stringToObj(json, UserDto.class);
        return courseCollectionsService.getCourse(userDto.getId(), pageNum, pageSize);
    }
}
