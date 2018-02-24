package com.labelwall.activity.service;

import com.labelwall.activity.entity.ActivityAccountAdd;
import com.labelwall.activity.entity.ActivityAccountOrder;
import com.labelwall.activity.entity.ActivityInfo;
import com.labelwall.activity.vo.ActivityAccountAddVo;
import com.labelwall.activity.vo.ActivityAccountOrderVo;
import com.labelwall.common.ResponseObject;

import java.util.List;
import java.util.Map;

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
    ResponseObject<ActivityAccountAddVo> createAccountAddOrder(ActivityAccountAdd activityAccountAdd);

    /**
     * 充值订单的支付订单签名
     *
     * @param orderNo
     * @param userId
     * @return
     */
    String activityAccountOrderSign(Long orderNo, Integer userId);

    /**
     * 支付订单的支付宝回调
     *
     * @param parameterMap
     * @return
     */
    String activityAccountAlipayCallback(Map parameterMap);

    /**
     * 获取订单详情
     *
     * @param userId
     * @param orderNo
     * @return
     */
    ActivityAccountAdd getAccountOrderDetail(Integer userId, Long orderNo);

    /**
     * 创建活动时生成的订单
     *
     * @param activityAccountOrder
     * @return
     */
    ResponseObject<ActivityAccountOrderVo> createAccountOrder(ActivityAccountOrder activityAccountOrder);

    /**
     * 支付创建活动的订单
     *
     * @param orderNo
     * @param activityInfo
     * @return
     */
    ResponseObject modifyPayActivityOrder(String orderNo, ActivityInfo activityInfo);
}
