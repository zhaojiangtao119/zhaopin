package com.labelwall.mall.service;

import com.github.pagehelper.PageInfo;
import com.labelwall.common.ResponseObject;
import com.labelwall.mall.dto.TopicPostDto;
import com.labelwall.mall.entity.TopicCategory;

import java.util.List;

/**
 * Created by Administrator on 2017-12-05.
 */
public interface ITopicPostService {
    /**
     * 分页获取帖子
     *
     * @param topicPostDto
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResponseObject<PageInfo> getTopicPostList(TopicPostDto topicPostDto, Integer pageNum, Integer pageSize);

    /**
     * 发帖
     *
     * @param topicPostDto
     * @return
     */
    ResponseObject<TopicPostDto> addPublishPost(TopicPostDto topicPostDto);

    /**
     * 点赞
     *
     * @param topicPostId
     * @return
     */
    ResponseObject updatePostLikeDislike(Integer topicPostId, Integer type);

    /**
     * 根据主键查询
     *
     * @param topicPostId
     * @return
     */
    ResponseObject<TopicPostDto> getTopicPostById(Integer topicPostId);

    /**
     * 修改帖子的回复数量
     *
     * @param topicPostId
     * @return
     */
    int updatePostReplyNum(Integer topicPostId);

}
