package com.labelwall.activity.service;

import com.labelwall.activity.entity.ActivityAccountAdd;
import com.labelwall.activity.entity.ActivityAccountOrder;
import com.labelwall.activity.vo.ActivityAccountAddVo;
import com.labelwall.activity.vo.ActivityAccountOrderVo;
import com.labelwall.common.ResponseObject;

import java.util.List;

/**
 * Created by Administrator on 2018-02-22.
 */
public interface IActivityAccountOrderService {

    /**
     * 获取活动的交易记录
     *
     * @param userId
     * @return
     */
    ResponseObject<List<ActivityAccountOrderVo>> getUserAcitivtyOrder(Integer userId);

    /**
     * 获取账户充值的交易记录
     *
     * @param userId
     * @param accountId
     * @return
     */
    ResponseObject<List<ActivityAccountAddVo>> getUserAccountAddList(Integer userId, Integer accountId);

    /**
     * 创建充值订单
     *
     * @param activityAccountAdd
     * @return
     */
    ResponseObject<ActivityAccountAdd> createAccountAddOrder(ActivityAccountAdd activityAccountAdd);
}
