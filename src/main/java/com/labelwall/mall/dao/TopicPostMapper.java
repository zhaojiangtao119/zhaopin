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

    /**
     * 获取帖子列表（存在搜索条件）
     *
     * @param topicPostDto
     * @return
     */
    List<TopicPostDto> getTopicPost(TopicPostDto topicPostDto);

    int updatePostLike(@Param("topicPostId") Integer topicPostId);

    int updatePostDislike(@Param("topicPostId") Integer topicPostId);

    /**
     * 修改帖子回复数量
     *
     * @param topicPostId
     * @return
     */
    int updatePostRelpyNum(Integer topicPostId);
}