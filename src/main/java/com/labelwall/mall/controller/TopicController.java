package com.labelwall.mall.controller;

import com.github.pagehelper.PageInfo;
import com.labelwall.mall.common.Const;
import com.labelwall.mall.common.ResponseObject;
import com.labelwall.mall.common.ResponseStatus;
import com.labelwall.mall.dto.TopicCategoryDto;
import com.labelwall.mall.dto.TopicPostDto;
import com.labelwall.mall.dto.TopicPostReplyDto;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.entity.TopicPost;
import com.labelwall.mall.message.UserResponseMessage;
import com.labelwall.mall.service.ITopicCategoryService;
import com.labelwall.mall.service.ITopicPostReplyService;
import com.labelwall.mall.service.ITopicPostService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2017-12-05.
 */
@RestController
@RequestMapping("/topic/")
public class TopicController {

    @Autowired
    private ITopicCategoryService topicCategoryService;
    @Autowired
    private ITopicPostService topicPostService;
    @Autowired
    private ITopicPostReplyService topicPostReplyService;

    /**
     * 获取帖子的分类
     *
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "get_category", method = RequestMethod.GET)
    public ResponseObject<List<TopicCategoryDto>>
    getCategory(@RequestParam(value = "categoryId", required = false) Integer categoryId) {
        return topicCategoryService.getCategoryList(categoryId);
    }

    /**
     * 获取某一类下的所有子类的id
     *
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "get_category_id", method = RequestMethod.GET)
    public ResponseObject<List<Integer>>
    getCategoryId(@RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        return topicCategoryService.getCategoryAndChildrenByCategoryId(categoryId);
    }

    /**
     * 分页查询帖子（schoolId,userId,topicCategoryId,keyword为查询条件）
     *
     * @param topicPostDto
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "get_topic_post_list", method = RequestMethod.POST)
    public ResponseObject<PageInfo> getTopicPostList(TopicPostDto topicPostDto,
                                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return topicPostService.getTopicPostList(topicPostDto, pageNum, pageSize);
    }

    /**
     * 发表帖子
     *
     * @param session
     * @param topicPostDto
     * @return
     */
    @RequestMapping(value = "pulish_post", method = RequestMethod.POST)
    public ResponseObject<TopicPostDto> publishPost(HttpSession session, TopicPostDto topicPostDto) {
        //TODO 创建帖子的，用户登录
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        if (userDto == null) {
            return ResponseObject.failStatusMessage(UserResponseMessage.NOT_LOGIN.getValue());
        }
        if (topicPostDto == null || StringUtils.isBlank(topicPostDto.getTitle())) {
            return ResponseObject.failStatusMessage(ResponseStatus.ERROR_PARAM.getValue());
        }
        topicPostDto.setUserId(userDto.getUserId());
        return topicPostService.publishPost(topicPostDto);
    }

    /**
     * 修改帖子的like OR dislike
     *
     * @param topicPostId
     * @param type
     * @param session
     * @return
     */
    @RequestMapping(value = "update_post_like_dislike", method = RequestMethod.GET)
    public ResponseObject updatePostLikeDislike(@RequestParam("topicPostId") Integer topicPostId,
                                                @RequestParam("clickType") Integer type,
                                                HttpSession session) {
        //TODO 帖子的like/dislike 判断是否登录
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        if (userDto == null) {
            return ResponseObject.failStatusMessage(UserResponseMessage.NOT_LOGIN.getValue());
        }
        return topicPostService.updatePostLikeDislike(topicPostId, type);
    }

    /**
     * 根据id获取帖子详情
     *
     * @param topicPostId
     * @return
     */
    @RequestMapping(value = "get_topic_post_by_id", method = RequestMethod.GET)
    public ResponseObject<TopicPostDto> getTopicPostById(@RequestParam(value = "topicPostId") Integer topicPostId) {
        return topicPostService.getTopicPostById(topicPostId);
    }

    /**
     * 根据帖子id，分页获取该帖子的回复
     *
     * @param topicPostId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "get_topic_reply_by_post_id", method = RequestMethod.GET)
    public ResponseObject<PageInfo> getTopicReplyByPostId(@RequestParam(value = "topicPostId") Integer topicPostId,
                                                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                          @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return topicPostReplyService.getTopicReplyByPostId(topicPostId, pageNum, pageSize);
    }

    /**
     * 帖子的回复
     *
     * @param session
     * @param topicPostReplyDto
     * @return
     */
    @RequestMapping(value = "pulish_post_reply", method = RequestMethod.POST)
    public ResponseObject<TopicPostReplyDto> pulishPostReply(HttpSession session, TopicPostReplyDto topicPostReplyDto) {
        //TODO 帖子的回复 判断是否登录
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        if (userDto == null) {
            return ResponseObject.failStatusMessage(UserResponseMessage.NOT_LOGIN.getValue());
        }
        if (StringUtils.isBlank(topicPostReplyDto.getImage()) && StringUtils.isBlank(topicPostReplyDto.getContent())) {
            return ResponseObject.failStatusMessage(ResponseStatus.ERROR_PARAM.getValue());
        }
        topicPostReplyDto.setUserId(userDto.getId());
        return topicPostReplyService.pulishPostReply(topicPostReplyDto);
    }

    /**
     * 修改回复的like OR dislike
     *
     * @param topicPostReplyId
     * @param type
     * @param session
     * @return
     */
    @RequestMapping(value = "update_post_reply_like_dislike", method = RequestMethod.GET)
    public ResponseObject updatePostReplyLikeDislike(@RequestParam("topicPostReplyId") Integer topicPostReplyId,
                                                     @RequestParam("clickType") Integer type,
                                                     HttpSession session) {
        //TODO 回复的like/dislike 判断是否登录
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        if (userDto == null) {
            return ResponseObject.failStatusMessage(UserResponseMessage.NOT_LOGIN.getValue());
        }
        return topicPostReplyService.updatePostReplyLikeDislike(topicPostReplyId, type);
    }
}
