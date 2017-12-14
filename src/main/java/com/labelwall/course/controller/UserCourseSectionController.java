package com.labelwall.course.controller;

import com.labelwall.common.Const;
import com.labelwall.common.ResponseObject;
import com.labelwall.course.entity.CourseSection;
import com.labelwall.course.entity.UserCourseSection;
import com.labelwall.course.service.IUserCourseSectionService;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.entity.User;
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
    public ResponseObject<CourseSection> getCurrentRecord(HttpSession session,
                                                          @PathVariable("id") Integer courseId) {
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
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
    public ResponseObject createNewRecord(HttpSession session, UserCourseSection userCourseSection) {
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        return userCourseSectionService.createNewRecord(userDto, userCourseSection);
    }

}
