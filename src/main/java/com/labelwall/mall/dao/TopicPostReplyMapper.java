package com.labelwall.mall.dao;

import com.labelwall.mall.dto.TopicPostReplyDto;
import com.labelwall.mall.entity.TopicPostReply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TopicPostReplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TopicPostReply record);

    int insertSelective(TopicPostReply record);

    TopicPostReplyDto selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TopicPostReply record);

    int updateByPrimaryKey(TopicPostReply record);

    /**
     * 获取某一个帖子的回复
     *
     * @param postId
     * @return
     */
    List<TopicPostReplyDto> getTopicReplyByPostId(Integer postId);

    int updatePostReplyLike(@Param("postReplyId") Integer postReplyId);

    int updatePostReplyDislike(@Param("postReplyId") Integer postReplyId);
}