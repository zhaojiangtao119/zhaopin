package com.labelwall.course.controller;

import com.labelwall.common.Const;
import com.labelwall.common.ResponseObject;
import com.labelwall.course.service.ICourseCollectionsService;
import com.labelwall.mall.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

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
    public ResponseObject<Boolean> isCollection(HttpSession session,
                                                @PathVariable("id") Integer courseId) {
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        return courseCollectionsService.isCollection(userDto.getId(), courseId);
    }

    //点击收藏按钮执行的动作
    @RequestMapping(value = "do_collection/{id}",method = RequestMethod.POST)
    public ResponseObject doCollection(HttpSession session,
                                                @PathVariable("id") Integer courseId){
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        return courseCollectionsService.doCollection(userDto.getId(), courseId);
    }
}
