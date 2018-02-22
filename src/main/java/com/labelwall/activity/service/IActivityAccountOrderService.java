package com.labelwall.activity.service;

import com.labelwall.activity.entity.ActivityAccountOrder;
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
}
