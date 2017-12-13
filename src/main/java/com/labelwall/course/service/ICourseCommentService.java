package com.labelwall.course.service;

import com.github.pagehelper.PageInfo;
import com.labelwall.common.ResponseObject;
import com.labelwall.course.dto.CourseCommentDto;
import com.labelwall.mall.dto.UserDto;

import java.util.List;

/**
 * Created by Administrator on 2017-12-13.
 */
public interface ICourseCommentService {

    /**
     * 获取课程评论
     *
     * @param courseCommentDto
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResponseObject<PageInfo> getComment(CourseCommentDto courseCommentDto, Integer pageNum, Integer pageSize);

    /**
     * 发表评论/问答
     *
     * @param userDto
     * @param courseCommentDto
     * @return
     */
    ResponseObject<CourseCommentDto> publishComment(UserDto userDto, CourseCommentDto courseCommentDto);
}
