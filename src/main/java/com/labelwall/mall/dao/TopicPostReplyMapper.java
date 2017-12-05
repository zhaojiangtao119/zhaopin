package com.labelwall.mall.dao;

import com.labelwall.mall.entity.TopicPostReply;

public interface TopicPostReplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TopicPostReply record);

    int insertSelective(TopicPostReply record);

    TopicPostReply selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TopicPostReply record);

    int updateByPrimaryKeyWithBLOBs(TopicPostReply record);

    int updateByPrimaryKey(TopicPostReply record);
}