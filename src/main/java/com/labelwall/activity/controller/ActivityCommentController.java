package com.labelwall.activity.controller;

import com.github.pagehelper.PageInfo;
import com.labelwall.activity.service.IActivityCommentService;
import com.labelwall.common.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018-02-05.
 */
@RestController
@RequestMapping("/activity/comment/")
public class ActivityCommentController {

    @Autowired
    private IActivityCommentService activityCommentService;

    /**
     * 获取指定活动的评论
     *
     * @param activityId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/{activityId}/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public ResponseObject<PageInfo> getCommentList(@PathVariable("activityId") Integer activityId,
                                                   @PathVariable("pageNum") Integer pageNum,
                                                   @PathVariable("pageSize") Integer pageSize) {
        return activityCommentService.getCommentList(activityId, pageNum, pageSize);
    }

}
