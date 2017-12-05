package com.labelwall.mall.controller;

import com.github.pagehelper.PageInfo;
import com.labelwall.mall.common.Const;
import com.labelwall.mall.common.ResponseObject;
import com.labelwall.mall.common.ResponseStatus;
import com.labelwall.mall.dto.TopicCategoryDto;
import com.labelwall.mall.dto.TopicPostDto;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.entity.TopicPost;
import com.labelwall.mall.message.UserResponseMessage;
import com.labelwall.mall.service.ITopicCategoryService;
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

    @RequestMapping(value = "get_category", method = RequestMethod.GET)
    public ResponseObject<List<TopicCategoryDto>>
    getCategory(@RequestParam(value = "categoryId", required = false) Integer categoryId) {
        return topicCategoryService.getCategoryList(categoryId);
    }

    @RequestMapping(value = "get_category_id", method = RequestMethod.GET)
    public ResponseObject<List<Integer>>
    getCategoryId(@RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        return topicCategoryService.getCategoryAndChildrenByCategoryId(categoryId);
    }

    @RequestMapping(value = "get_topic_post_list", method = RequestMethod.POST)
    public ResponseObject<PageInfo> getTopicPostList(TopicPostDto topicPostDto,
                                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return topicPostService.getTopicPostList(topicPostDto, pageNum, pageSize);
    }

    @RequestMapping(value = "pulish_post", method = RequestMethod.POST)
    public ResponseObject<TopicPostDto> publishPost(HttpSession session, TopicPostDto topicPostDto) {
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

    @RequestMapping(value = "update_post_like_dislike", method = RequestMethod.GET)
    public ResponseObject updatePostLikeDislike(@RequestParam("topicPostId") Integer topicPostId,
                                                @RequestParam("clickType") Integer type,
                                                HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
        if (userDto == null) {
            return ResponseObject.failStatusMessage(UserResponseMessage.NOT_LOGIN.getValue());
        }
        return topicPostService.updatePostLikeDislike(topicPostId, type);
    }

    @RequestMapping(value = "get_topic_post_by_id",method = RequestMethod.GET)
    public ResponseObject<TopicPostDto> getTopicPostById(@RequestParam(value = "topicPostId")Integer topicPostId){
        return topicPostService.getTopicPostById(topicPostId);
    }
}
