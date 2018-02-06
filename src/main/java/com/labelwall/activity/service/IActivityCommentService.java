package com.labelwall.activity.service;

import com.github.pagehelper.PageInfo;
import com.labelwall.common.ResponseObject;

/**
 * Created by Administrator on 2018-02-05.
 */
public interface IActivityCommentService {
    /**
     * 获取指定活动的评论
     *
     * @param activityId
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResponseObject<PageInfo> getCommentList(Integer activityId, Integer pageNum, Integer pageSize);
}
