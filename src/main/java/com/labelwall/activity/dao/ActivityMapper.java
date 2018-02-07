package com.labelwall.activity.dao;

import java.util.List;

import com.labelwall.activity.dto.ActivityDto;
import com.labelwall.activity.entity.ActivityInfo;
import com.labelwall.mall.entity.User;
import org.apache.ibatis.annotations.Param;


public interface ActivityMapper {

	/*List<ActivityInfo> selectByStartUser(User user);



	Integer insertActivity(ActivityInfo info);




	Integer startActivity(String date);


	void updateTaskTim(Map map);


	int endActivity(String date);


	int deleteActivity(String date);


	ActivityInfo select(ActivityInfo info);


	Integer joinActivity(Map<String, Object> map);


	List<ActivityInfo> selectByUser(@Param("type") int userType, @Param("user") User user);


	void deleteUser(Map<String, String> map);


//	List<UserInfo> selectJoinUser(User user);1.24



	List<ActivityInfo> selectLikeByPage(Map<String, Object> map);



	int selectCountByPage(Map map);


	int addOrder(@Param("info") ActivityInfo info, @Param("order") ActivityPayInfo order, @Param("userId") Integer userId);


	void updateOrder(@Param("trade_no") String wiDout_trade_no, @Param("user_id") Integer id, @Param("activityId") Integer activityId);


	Integer selectCountOrders();


	int cutAccount(@Param("id") Integer id, @Param("num") BigDecimal wiDtotal_fee);


	Long selectAccountByuser(Integer id);


	Long selectOrder(ActivityPayInfo order);


	int insertTradeHistory(AccountTradeInfo trade);


	ActivityInfo selectByInfo(@Param("info") ActivityInfo info, @Param("user_id") Integer id);


	Integer insertStartUser(Map<String, String> map);


	List<ActivityDto> selectStartByUser(User user);


	List<ActivityInfo> selectByjoinUser(User user);



	List<UserInfo> getJoinUserNoCheck(Integer id);


	int agreeJoin(@Param("activityId") Integer id, @Param("userId") Integer userId);


	Integer insertJoinUser(Map<String, String> map);


	List<ActivityInfo> selectNearJoin(User user);


	int addAccount(@Param("id") Integer userId, @Param("num") BigDecimal wiDtotal_fee);


	List<String> getAllTypes();


	List<String> getAllStyles();


	List<City> getAllCity();


	List<School> getSchoolByCity(Integer province_id);


	List<ActivityInfo> selectHasJoin(User user);*/


//	void quit(Map<String, Integer> map);

    List<ActivityInfo> selectByStartUserId(@Param("userId") Integer userId);

    List<ActivityInfo> selectByjoinUserId(@Param("userId") Integer userId);

    List<ActivityInfo> selectActivityByPage(@Param("activityDto") ActivityDto activityDto,
                                            @Param("activityIds") List<Integer> activityIds);

    /**
     * 根据主键获取活动信息
     *
     * @param activityId
     * @return
     */
    ActivityInfo selectByPrimaryKey(Integer activityId);

    List<Integer> selectIdsByActivityId(@Param("activityId") Integer activityId,
                                        @Param("status") Integer status);

    /**
     * 获取指定用户发起的活动
     *
     * @param userId
     * @return
     */
    List<ActivityInfo> selectUserStartByUserId(Integer userId);

    /**
     * 获取指定用户参与的活动
     *
     * @param userId
     * @return
     */
    List<ActivityInfo> selectUserJoinByUserId(Integer userId);
}
