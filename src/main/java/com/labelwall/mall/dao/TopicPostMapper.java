package com.labelwall.mall.dao;

import com.labelwall.mall.dto.TopicPostDto;
import com.labelwall.mall.entity.TopicPost;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TopicPostMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TopicPost record);

    int insertSelective(TopicPost record);

    TopicPostDto selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TopicPost record);

    int updateByPrimaryKey(TopicPost record);

    List<TopicPostDto> getTopicPost(TopicPostDto topicPostDto);

    int updatePostLike(@Param("topicPostId") Integer topicPostId);

    int updatePostDislike(@Param("topicPostId") Integer topicPostId);

    int updatePostRelpyNum(Integer topicPostId);
}