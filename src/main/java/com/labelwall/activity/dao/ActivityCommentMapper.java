package com.labelwall.activity.dao;

import com.labelwall.activity.dto.ActivityCommentDto;
import com.labelwall.activity.entity.ActivityComment;

import java.util.List;

public interface ActivityCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ActivityComment record);

    int insertSelective(ActivityComment record);

    ActivityComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ActivityComment record);

    int updateByPrimaryKey(ActivityComment record);

    List<ActivityCommentDto> getCommentMapper(Integer activityId);
}