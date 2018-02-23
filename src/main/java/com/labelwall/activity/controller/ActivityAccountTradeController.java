package com.labelwall.activity.controller;

import com.labelwall.activity.entity.ActivityAccountAdd;
import com.labelwall.activity.service.IActivityAccountOrderService;
import com.labelwall.activity.vo.ActivityAccountAddVo;
import com.labelwall.activity.vo.ActivityAccountOrderVo;
import com.labelwall.common.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
    @RequestMapping(value = "{userId}", method = RequestMethod.GET)
    public ResponseObject<List<ActivityAccountOrderVo>> getUserActivityOrder(@PathVariable("userId") Integer userId) {
        return activityAccountOrderService.getUserAcitivtyOrder(userId);
    }

    /**
     * 获取用户账户充值的交易记录
     *
     * @param userId
     * @param accountId
     * @return
     */
    @RequestMapping(value = "{userId}/{accountId}", method = RequestMethod.GET)
    public ResponseObject<List<ActivityAccountAddVo>>
    getUserAccountAddList(@PathVariable("userId") Integer userId, @PathVariable("accountId") Integer accountId) {
        return activityAccountOrderService.getUserAccountAddList(userId, accountId);
    }

    /**
     * 创建充值金豆的订单
     *
     * @param activityAccountAdd
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseObject<ActivityAccountAddVo> createAccountAddOrder(ActivityAccountAdd activityAccountAdd) {
        return activityAccountOrderService.createAccountAddOrder(activityAccountAdd);
    }

    /**
     * 根据orderNo，生成订单签名，将签名返回给app客户端
     *
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "sign", method = RequestMethod.POST)
    public ResponseObject<String> appCreateOrder(Long orderNo, Integer userId) {
        String signOrder = activityAccountOrderService.activityAccountOrderSign(orderNo, userId);
        return ResponseObject.successStautsData(signOrder);
    }

    /**
     * 支付宝支付完成后的回调接口  POST请求
     * 支付宝支付结果的通知，一个Map,异步的通知参数
     * 验签的过程
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "callbacks", method = RequestMethod.POST)
    public String callbacks(HttpServletRequest request) {
        return activityAccountOrderService.activityAccountAlipayCallback(request.getParameterMap());

    }
}
