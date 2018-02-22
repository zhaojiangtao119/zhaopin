package com.labelwall.activity.controller;

import com.labelwall.activity.entity.ActivityAccountOrder;
import com.labelwall.activity.service.IActivityAccountOrderService;
import com.labelwall.activity.vo.ActivityAccountOrderVo;
import com.labelwall.common.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2018-02-22.
 */
@RestController
@RequestMapping("/activity/account/trade/")
public class ActivityAccountTradeController {

    @Autowired
    private IActivityAccountOrderService activityAccountOrderService;

    /**
     * 获取用户的活动交易记录
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseObject<List<ActivityAccountOrderVo>> getUserActivityOrder(@PathVariable("userId") Integer userId) {
        return activityAccountOrderService.getUserAcitivtyOrder(userId);
    }
}
