package com.labelwall.mall.service;

import com.github.pagehelper.PageInfo;
import com.labelwall.common.ResponseObject;
import com.labelwall.mall.dto.TopicPostReplyDto;

/**
 * Created by Administrator on 2017-12-06.
 */
public interface ITopicPostReplyService {
    /**
     * 根据贴子id获取帖子的回复
     *
     * @param postId
     * @return
     */
    ResponseObject<PageInfo> getTopicReplyByPostId(Integer postId, Integer pageNum, Integer pageSize);

    /**
     * 创建帖子的回复
     *
     * @param topicPostReplyDto
     * @return
     */
    ResponseObject<TopicPostReplyDto> publishPostReply(TopicPostReplyDto topicPostReplyDto);

    /**
     * 修改帖子的like dislike
     *
     * @param topicPostReplyId
     * @param type
     * @return
     */
    ResponseObject updatePostReplyLikeDislike(Integer topicPostReplyId, Integer type);
}
