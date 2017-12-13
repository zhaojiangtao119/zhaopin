package com.labelwall.course.service.impl;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.labelwall.common.CourseConst;
import com.labelwall.common.ResponseObject;
import com.labelwall.common.ResponseStatus;
import com.labelwall.course.dao.CourseCommentMapper;
import com.labelwall.course.dto.CourseCommentDto;
import com.labelwall.course.entity.CourseComment;
import com.labelwall.course.service.ICourseCommentService;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.service.IUserService;
import com.labelwall.util.DateTimeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017-12-13.
 */
@Service("courseCommentService")
public class CourseCommentServiceImpl implements ICourseCommentService {

    @Autowired
    private CourseCommentMapper courseCommentMapper;
    @Autowired
    private IUserService userService;

    @Override
    public ResponseObject<PageInfo> getComment(CourseCommentDto courseCommentDto, Integer pageNum, Integer pageSize) {
        if (courseCommentDto.getType() == null) {
            return ResponseObject.failStatusMessage(ResponseStatus.ERROR_PARAM.getValue());
        }
        Integer type = courseCommentDto.getType();
        if (type != CourseConst.CommentType.comment && type != CourseConst.CommentType.questions) {
            ResponseObject.failStatusMessage(ResponseStatus.ERROR_PARAM.getValue());
        }
        List<CourseComment> courseCommentList = courseCommentMapper.getComment(courseCommentDto);
        List<CourseCommentDto> courseCommentDtoList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(courseCommentList)) {
            for (CourseComment courseComment : courseCommentList) {
                UserDto userDto = userService.selectByUsername(courseComment.getUsername());
                CourseCommentDto courseCommentDtoNew = new CourseCommentDto();
                BeanUtils.copyProperties(courseComment, courseCommentDtoNew);
                courseCommentDtoNew.setUserDto(userDto);
                courseCommentDtoNew.setCreateTimeStr(DateTimeUtil.dateToStr(courseComment.getCreateTime()));
                courseCommentDtoList.add(courseCommentDtoNew);
            }
        }
        PageInfo pageInfo = new PageInfo(courseCommentDtoList);
        return ResponseObject.successStautsData(pageInfo);
    }

    @Override
    public ResponseObject<CourseCommentDto> publishComment(UserDto userDto, CourseCommentDto courseCommentDto) {
        if (StringUtils.isBlank(courseCommentDto.getContent())) {
            return ResponseObject.failStatusMessage(ResponseStatus.ERROR_PARAM.getValue());
        }
        courseCommentDto.setUsername(userDto.getUsername());
        CourseComment courseComment = new CourseComment();
        BeanUtils.copyProperties(courseCommentDto, courseComment);
        courseComment.setCreateUser(userDto.getUsername());
        courseComment.setUpdateUser(userDto.getUsername());
        int rowCount = courseCommentMapper.insertSelective(courseComment);
        if (rowCount > 0) {
            CourseComment courseCommentNew = courseCommentMapper.selectByPrimaryKey(courseComment.getId());
            CourseCommentDto courseCommentDtoNew = new CourseCommentDto();
            BeanUtils.copyProperties(courseCommentNew, courseCommentDtoNew);
            courseCommentDtoNew.setCreateTimeStr(DateTimeUtil.dateToStr(courseCommentNew.getCreateTime()));
            courseCommentDtoNew.setUserDto(userDto);
            return ResponseObject.successStautsData(courseCommentDtoNew);
        }
        return ResponseObject.failStatusMessage("评论失败");
    }
}