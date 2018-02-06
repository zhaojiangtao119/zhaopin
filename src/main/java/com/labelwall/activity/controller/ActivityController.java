package com.labelwall.activity.controller;

import com.github.pagehelper.PageInfo;
import com.labelwall.activity.dto.ActivityDto;
import com.labelwall.activity.service.IActivityService;
import com.labelwall.common.Const;
import com.labelwall.common.ResponseObject;
import com.labelwall.mall.dto.UserDto;
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
     * @return
     */
    @RequestMapping(value = "query/{pageNum}/{pageSize}", method = RequestMethod.POST)
    public ResponseObject<PageInfo> query(HttpSession session, @PathVariable("pageNum") Integer pageNum,
                                          @PathVariable("pageSize") Integer pageSize, ActivityDto activity) {
        UserDto userInfo = (UserDto) session.getAttribute(Const.CURRENT_USER);
        Integer userId = null;
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
     * 获取当前/指定用户的相关的活动(发起的活动)
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "user/start/{userId}/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public ResponseObject<PageInfo> getActivityUserStart(@PathVariable("userId") Integer userId,
                                                         @PathVariable("pageNum")Integer pageNum,
                                                         @PathVariable("pageSize")Integer pageSize) {
        return activityService.getActivityUserStart(userId,pageNum,pageSize);
    }
    /**
     * 获取当前/指定用户的相关的活动(参加的活动)
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "user/join/{userId}/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public ResponseObject<PageInfo> getActivityUserJoin(@PathVariable("userId") Integer userId,
                                                         @PathVariable("pageNum")Integer pageNum,
                                                         @PathVariable("pageSize")Integer pageSize) {
        return activityService.getActivityUserJoin(userId,pageNum,pageSize);
    }
}
