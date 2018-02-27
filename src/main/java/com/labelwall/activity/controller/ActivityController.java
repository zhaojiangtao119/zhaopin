package com.labelwall.activity.controller;

import com.github.pagehelper.PageInfo;
import com.labelwall.activity.dto.ActivityDto;
import com.labelwall.activity.entity.ActivityInfo;
import com.labelwall.activity.entity.ActivityStyles;
import com.labelwall.activity.entity.ActivityTypes;
import com.labelwall.activity.service.IActivityService;
import com.labelwall.common.Const;
import com.labelwall.common.ResponseObject;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.entity.School;
import com.labelwall.mall.entity.User;
import com.labelwall.mall.service.ISchoolService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2018-02-05.
 */
@RestController
@RequestMapping("/activity/")
public class ActivityController {

    @Autowired
    private IActivityService activityService;
    @Autowired
    private ISchoolService schoolService;

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
     * 用户加入活动
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

    /**
     * 验证用户是否参加了该活动
     *
     * @param userId
     * @param activityId
     * @return
     */
    @RequestMapping(value = "user/{userId}/{activityId}", method = RequestMethod.GET)
    public ResponseObject validateUserJoin(@PathVariable("userId") Integer userId,
                                           @PathVariable("activityId") Integer activityId) {
        return activityService.validateUserJoin(userId, activityId);
    }

    /**
     * 用户放弃参与该活动，不管用户是申请加入还是已通过加入
     *
     * @param userId
     * @param activityId
     * @return
     */
    @RequestMapping(value = "user/{userId}/{activityId}", method = RequestMethod.DELETE)
    public ResponseObject quitAcitivty(@PathVariable("userId") Integer userId,
                                       @PathVariable("activityId") Integer activityId) {
        return activityService.quitActivity(userId, activityId);
    }

    /**
     * 获得所有活动分类名
     */
    @RequestMapping(value = "/typeList", method = RequestMethod.GET)
    public ResponseObject<List<ActivityTypes>> getType() {
        return activityService.getAllTypes();
    }

    /**
     * 获得所有活动形式
     */
    @RequestMapping(value = "/styleList", method = RequestMethod.GET)
    public ResponseObject<List<ActivityStyles>> getStyle() {

        return activityService.getAllStyles();
    }

    /**
     * 通过省份的name来获取该省份下的学校
     *
     * @return
     */
    @RequestMapping(value = "school", method = RequestMethod.GET)
    public ResponseObject<List<School>> getSchoolList(String provinceName) {
        return schoolService.getSchoolList(provinceName);
    }

    /**
     * 创建免费活动
     * 将新建活动id返回回去
     *
     * @param activityInfo
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public ResponseObject<Integer> createActivity(ActivityInfo activityInfo) {
        return activityService.createFreeActivity(activityInfo);
    }

    /**
     * APP创建活动成功后，将图片上传到七牛云，将图片的url存储进来
     *
     * @param userId
     * @param activityId
     * @param posterUrl
     * @return
     */
    @RequestMapping(value = "poster", method = RequestMethod.PUT)
    public ResponseObject updatePosterUrl(Integer userId, Integer activityId, String posterUrl) {
        return activityService.updatePosterUrl(userId, activityId, posterUrl);
    }

    /**
     * 创建收费活动订单之前验证活动基本信息是否满足后台的验证要求
     *
     * @param activityInfo
     * @return
     */
    @RequestMapping(value = "validate", method = RequestMethod.POST)
    public ResponseObject validateActivityInfo(ActivityInfo activityInfo) {
        return activityService.validateActivityInfo(activityInfo);
    }

    /**
     * 加入活动时判断用户的时间与加入活动的时间是否存在冲突
     *
     * @param activityId
     * @param userId
     * @return
     */
    @RequestMapping(value = "validate/join", method = RequestMethod.POST)
    public ResponseObject validateActivityInfoJoin(Integer activityId, Integer userId) {
        return activityService.validateActivityInfoJoin(activityId, userId);
    }
}
