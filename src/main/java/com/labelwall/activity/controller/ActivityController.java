package com.labelwall.activity.controller;

import com.github.pagehelper.PageInfo;
import com.labelwall.activity.dto.ActivityDto;
import com.labelwall.activity.entity.ActivityInfo;
import com.labelwall.activity.service.IActivityService;
import com.labelwall.common.Const;
import com.labelwall.common.ResponseObject;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2018-02-05.
 */
@RestController
@RequestMapping("/activity/")
public class ActivityController {

    @Autowired
    private IActivityService activityService;

    /**
     * 获取活动的列表（时间条件的过滤）
     *
     * @param session
     * @param pageNum
     * @param pageSize
     * @param activity
     * @param userId
     * @return
     */
    @RequestMapping(value = "query/{pageNum}/{pageSize}", method = RequestMethod.POST)
    public ResponseObject<PageInfo> query(HttpSession session, @PathVariable("pageNum") Integer pageNum,
                                          @PathVariable("pageSize") Integer pageSize, ActivityDto activity, Integer userId) {
        UserDto userInfo = (UserDto) session.getAttribute(Const.CURRENT_USER);
        if (userInfo != null) {
            userId = userInfo.getId();
        }
        return activityService.query(userId, pageNum, pageSize, activity);
    }

    /**
     * 获取活动详情（活动的信息，创建者的信息，已加入的用户，待加入的用户）
     *
     * @param activityId
     * @return
     */
    @RequestMapping(value = "/{activityId}", method = RequestMethod.GET)
    public ResponseObject<ActivityDto> getActivityInfo(@PathVariable("activityId") Integer activityId) {
        return activityService.getActivityInfo(activityId);
    }

    /**
     * 获取当前/指定用户的相关的活动(发起的活动)，最近的10个
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "user/start/{userId}/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public ResponseObject<PageInfo> getActivityUserStart(@PathVariable("userId") Integer userId,
                                                         @PathVariable("pageNum") Integer pageNum,
                                                         @PathVariable("pageSize") Integer pageSize) {
        return activityService.getActivityUserStart(userId, pageNum, pageSize);
    }

    /**
     * 获取当前/指定用户的相关的活动(参加的活动)
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "user/join/{userId}/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public ResponseObject<PageInfo> getActivityUserJoin(@PathVariable("userId") Integer userId,
                                                        @PathVariable("pageNum") Integer pageNum,
                                                        @PathVariable("pageSize") Integer pageSize) {
        return activityService.getActivityUserJoin(userId, pageNum, pageSize);
    }

    /**
     * 同意申请用户加入活动
     *
     * @param activityId
     * @param startUserId
     * @param joinUserId
     * @return
     */
    @RequestMapping(value = "/agreeJoin/{startUserId}/{activityId}/{joinUserId}", method = RequestMethod.PUT)
    public ResponseObject agreeJoin(@PathVariable("activityId") Integer activityId,
                                    @PathVariable("startUserId") Integer startUserId,
                                    @PathVariable("joinUserId") Integer joinUserId) {
        return activityService.modifyAgreeUserJoin(startUserId, activityId, joinUserId);
    }

    /**
     * 主动加入活动
     *
     * @param activityId
     * @param userId
     * @return
     */
    @RequestMapping(value = "join/{activityId}/{userId}", method = RequestMethod.POST)
    public ResponseObject joinActivity(@PathVariable("activityId") Integer activityId,
                                       @PathVariable("userId") Integer userId) {
        return activityService.saveJoinActivity(activityId, userId);
    }
}
