package com.labelwall.mall.controller;

import com.labelwall.common.ResponseObject;
import com.labelwall.mall.dto.TopicPostDto;
import com.labelwall.mall.dto.TopicPostReplyDto;
import com.labelwall.mall.service.ITopicPostReplyService;
import com.labelwall.mall.service.ITopicPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018-01-29.
 */
@RestController
@RequestMapping("/app/topic/")
public class AppTopicController {

    @Autowired
    private ITopicPostService topicPostService;
    @Autowired
    private ITopicPostReplyService topicPostReplyService;

    /**
     * APP发表帖子
     *
     * @param topicPostDto
     * @return
     */
    @RequestMapping(value = "publish_post", method = RequestMethod.POST)
    public ResponseObject<TopicPostDto> appPublishPost(TopicPostDto topicPostDto) {
        //TODO 创建帖子的，用户登录
        return topicPostService.publishPost(topicPostDto);
    }

    /**
     * APP发表帖子的回复
     *
     * @param topicPostReplyDto
     * @return
     */
    @RequestMapping(value = "publish_post_reply", method = RequestMethod.POST)
    public ResponseObject<TopicPostReplyDto> appPublishPostReply(TopicPostReplyDto topicPostReplyDto) {
        return topicPostReplyService.publishPostReply(topicPostReplyDto);
    }
}
