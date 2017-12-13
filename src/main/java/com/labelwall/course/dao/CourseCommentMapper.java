package com.labelwall.course.dao;

import com.labelwall.course.dto.CourseCommentDto;
import com.labelwall.course.entity.CourseComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CourseComment record);

    int insertSelective(CourseComment record);

    CourseComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CourseComment record);

    int updateByPrimaryKey(CourseComment record);

    /**
     * 获取评论问答，courseId,sectionId,type
     *
     * @param courseCommentDto
     * @return
     */
    List<CourseComment> getComment(CourseCommentDto courseCommentDto);
}