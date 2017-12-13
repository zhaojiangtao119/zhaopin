package com.labelwall.course.controller;

import com.github.pagehelper.PageInfo;
import com.labelwall.common.Const;
import com.labelwall.common.ResponseObject;
import com.labelwall.course.dto.CourseCommentDto;
import com.labelwall.course.service.ICourseCommentService;
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
@RequestMapping("/comment/")
public class CourseCommentController {

    @Autowired
    private ICourseCommentService courseCommentService;

    /**
     * 根据课程id/章节id获取评论或问答
     *
     * @param courseCommentDto
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "get_comment/{pageNum}/{pageSize}", method = RequestMethod.POST)
    public ResponseObject<PageInfo> getComment(CourseCommentDto courseCommentDto,
                                               @PathVariable("pageNum") Integer pageNum,
                                               @PathVariable("pageSize") Integer pageSize) {
        return courseCommentService.getComment(courseCommentDto, pageNum, pageSize);
    }

    /**
     * 发表评论或问答
     *
     * @param session
     * @param courseCommentDto
     * @return
     */
    @RequestMapping(value = "publish_comment", method = RequestMethod.POST)
    public ResponseObject<CourseCommentDto> publishComment(HttpSession session, CourseCommentDto courseCommentDto) {
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        return courseCommentService.publishComment(userDto, courseCommentDto);
    }

}
