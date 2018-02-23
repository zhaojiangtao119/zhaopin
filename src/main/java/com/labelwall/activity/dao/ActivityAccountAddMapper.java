package com.labelwall.activity.dao;

import com.labelwall.activity.entity.ActivityAccountAdd;
import com.labelwall.activity.entity.ActivityAccountOrder;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018-02-22.
 */
public interface ActivityAccountAddMapper {
    /**
     * 获取账户充值的交易记录
     *
     * @param userId
     * @param accountId
     * @return
     */
    List<ActivityAccountAdd> getUserAccountAddList(@Param("userId") Integer userId,
                                                   @Param("accountId") Integer accountId);

    /**
     * 获取今天生成的充值订单数
     *
     * @param todayDate
     * @return
     */
    Integer getTodayOrderNum(String todayDate);

    /**
     * 创建充值的订单
     *
     * @param activityAccountAdd
     * @return
     */
    int createAccountAddOrder(ActivityAccountAdd activityAccountAdd);

    /**
     * 修改订单状态
     * @param orderNo
     * @param status
     * @return
     */
    int updateAddOrderStatus(@Param("orderNo") String orderNo,
                             @Param("status") Integer status);
}
