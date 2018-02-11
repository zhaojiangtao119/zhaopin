package com.labelwall.activity.service;

import com.github.pagehelper.PageInfo;
import com.labelwall.activity.dto.ActivityDto;
import com.labelwall.activity.entity.ActivityStyles;
import com.labelwall.activity.entity.ActivityTypes;
import com.labelwall.common.ResponseObject;

import java.util.List;


public interface IActivityService {

	/*List<ActivityInfo> selectByStartUser(User user);

	int add(User user, ActivityInfo info, ActivityPayInfo order);

	Integer startActivity(String date);

	void updateTaskTim(int i, String date);

	int endActivity(String date);

	int deleteActivity(String date);

	ActivityDto selectById(ActivityInfo dto);

	Integer joinActivity(User user, Integer integer, ActivityPayInfo order);

	ActivityListDto selectByTheme(ActivityInfo tmp);

	*//**
     * �����û���Ϣ��ѯ���л���飬usertype 0 Ϊ������ 1Ϊ������
     *
     *//*
    // List<ActivityDto> selectByUser(int userType, User user);

	void deleteUser(String activity_id, String user_id);

	// List<UserInfo> updateJoinNum(User user);---1.24

	Page<ActivityInfo> selectPager(ActivityInfo dto, Page<ActivityInfo> page, User user);

	int addOrder(ActivityInfo info, ActivityPayInfo order, Integer userId);

	Integer selectCountOrders();

	List<ActivityInfo> selectByjoinUser(User currUser);

	void insertActivity(ActivityInfo info);

	List<UserInfo> getJoinUserNoCheck(ActivityInfo info);



	int join(User user, ActivityInfo info, ActivityPayInfo order);

	List<ActivityInfo> selectNearJoin(User user);

	List<String> getAllTypes();

	List<String> getAllStyles();

	List<School> getSchoolByCity(Integer province_id);

	List<City> getAllCity();

	List<ActivityInfo> selectByhasjoin(User user);*/

    // List<ActivityDto> selectStarttByUser(User user);

    // void quit(ActivityInfo info);
    // -------------------------------------------------------------------

    /**
     * 查询活动列表（都是可参加的活动）
     *
     * @param pageNum
     * @param pageSize
     * @param activity
     * @return
     */
    ResponseObject<PageInfo> query(Integer userId, Integer pageNum, Integer pageSize, ActivityDto activity);

    /**
     * 获取指定的活动信息
     *
     * @param activityId
     * @return
     */
    ResponseObject<ActivityDto> getActivityInfo(Integer activityId);

    /**
     * 获取指定用户的创建的活动
     *
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResponseObject<PageInfo> getActivityUserStart(Integer userId, Integer pageNum, Integer pageSize);

    /**
     * 获取指定用户的参加的活动
     *
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResponseObject<PageInfo> getActivityUserJoin(Integer userId, Integer pageNum, Integer pageSize);


    /**
     * 同意申请用户加入活动
     *
     * @param startUserId
     * @param activityId
     * @param joinUserId
     * @return
     */
    ResponseObject modifyAgreeUserJoin(Integer startUserId, Integer activityId, Integer joinUserId);
    //    int agreeJoin(ActivityInfo activity, UserInfo user);


    /**
     * 加入某一个活动
     *
     * @param activityId
     * @param userId
     * @return
     */
    ResponseObject saveJoinActivity(Integer activityId, Integer userId);

    /**
     * 验证用户是否加入了该活动
     *
     * @param userId
     * @param activityId
     * @return
     */
    ResponseObject validateUserJoin(Integer userId, Integer activityId);

    /**
     * 用户主动退出活动
     *
     * @param userId
     * @param activityId
     * @return
     */
    ResponseObject quitActivity(Integer userId, Integer activityId);

    /**
     * 获取活动的类型
     *
     * @return
     */
    ResponseObject<List<ActivityTypes>> getAllTypes();

    /**
     * 获取分类的形式
     *
     * @return
     */
    ResponseObject<List<ActivityStyles>> getAllStyles();
}
