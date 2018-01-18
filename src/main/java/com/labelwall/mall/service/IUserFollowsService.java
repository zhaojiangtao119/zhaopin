package com.labelwall.mall.service;

import com.github.pagehelper.PageInfo;
import com.labelwall.common.ResponseObject;

/**
 * Created by Administrator on 2017-12-21.
 */
public interface IUserFollowsService {
    /**
     * 判断当前用户是否关注某一个用户
     *
     * @param userId
     * @param followsId
     * @return
     */
    ResponseObject<Boolean> isFollows(Integer userId, Integer followsId);

    /**
     * 点击关注之后执行的动作
     *
     * @param userId
     * @param followsId
     * @return
     */
    ResponseObject doFollows(Integer userId, Integer followsId);

    /**
     * 获取当前用户的关注列表
     *
     * @param userId
     * @return
     */
    ResponseObject<PageInfo> getFollows(Integer userId, Integer pageNum, Integer pageSize);
}
