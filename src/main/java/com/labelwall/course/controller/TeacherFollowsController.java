package com.labelwall.course.controller;

import com.github.pagehelper.PageInfo;
import com.labelwall.common.Const;
import com.labelwall.common.ResponseObject;
import com.labelwall.course.service.ITeacherFollowsService;
import com.labelwall.mall.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017-12-14.
 */
@RestController
@RequestMapping("/teacherfollows/")
public class TeacherFollowsController {

    @Autowired
    private ITeacherFollowsService teacherFollowsService;

    /**
     * 判断当前用户是否关注了该课程的讲师
     *
     * @param session
     * @param courseId
     * @return
     */
    @RequestMapping(value = "is_follows/{id}", method = RequestMethod.GET)
    public ResponseObject<Boolean> isFollows(HttpSession session,
                                             @PathVariable("id") Integer courseId) {
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        return teacherFollowsService.isFollows(userDto.getId(), courseId);
    }

    /**
     * 点击关注按钮执行的动作
     *
     * @param session
     * @param courseId
     * @return
     */
    @RequestMapping(value = "do_follows/{id}", method = RequestMethod.POST)
    public ResponseObject doFollows(HttpSession session,
                                    @PathVariable("id") Integer courseId) {
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        return teacherFollowsService.doFollows(userDto, courseId);
    }

    /**
     * 获取用户关注的教师信息
     *
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "get_teacher/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public ResponseObject<PageInfo> getTeacher(HttpSession session,
                                               @PathVariable("pageNum") Integer pageNum,
                                               @PathVariable("pageSize") Integer pageSize) {
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        return teacherFollowsService.getTeacher(userDto.getId(), pageNum, pageSize);
    }
}
