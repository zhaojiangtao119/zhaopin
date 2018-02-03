package com.labelwall.mall.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.labelwall.common.AlipayConfig;
import com.labelwall.common.AlipayTradeStatus;
import com.labelwall.common.Const;
import com.labelwall.common.ResponseObject;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.service.IOrderService;
import com.labelwall.mall.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2018-01-24.
 */
@RestController
@RequestMapping("/app/order/")
public class AppOrderController {

    @Autowired
    private IOrderService orderService;

    /**
     * APP创建订单，返回订单号
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseObject<OrderVo> createOrder(Integer userId) {
        ResponseObject<OrderVo> orderVoInfo = orderService.createAppOrder(userId);
        return orderVoInfo;
    }

    /**
     * 立即购买某一个商品
     *
     * @param productId
     * @param quantity
     * @return
     */
    @RequestMapping(value = "buy", method = RequestMethod.POST)
    public ResponseObject<OrderVo> buyProduct(Integer userId, Integer productId, Integer quantity) {
        return orderService.buyProduct(userId, productId, quantity);
    }

    /**
     * 根据orderNo，生成订单签名，将签名返回给app客户端
     *
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "sign", method = RequestMethod.POST)
    public ResponseObject<String> appCreateOrder(Long orderNo, Integer userId) {
        String signOrder = orderService.appOrderSign(orderNo, userId);
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
        return orderService.alipayCallback(request.getParameterMap());

    }

    /**
     * APP取消订单
     *
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "cancel", method = RequestMethod.PUT)
    public ResponseObject cancelOrder(Integer userId, Long orderNo) {
        return orderService.cancelOrder(userId, orderNo);
    }

    /**
     * APP获取用户的所有订单
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "user_order_list/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public ResponseObject<PageInfo> userOrderList(Integer userId,
                                                  @PathVariable(value = "pageNum") Integer pageNum,
                                                  @PathVariable(value = "pageSize") Integer pageSize) {
        return orderService.userOrderList(userId, pageNum, pageSize);
    }

    /**
     * 获取订单详情
     *
     * @param userId
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public ResponseObject<OrderVo> getOrderDetail(Integer userId, Long orderNo) {
        return orderService.getOrderDetail(userId, orderNo);
    }

    /**
     * 网页支付
     *
     * @param session
     * @param userId
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "pay", method = RequestMethod.POST)
    public ResponseObject pay(HttpSession session, Integer userId, Long orderNo) {
        String path = session.getServletContext().getRealPath("upload");
        return orderService.orderPay(orderNo, userId, path);
    }

    /**
     * 支付宝的回调，扫码付款成功后由支付宝调用，验证回调参数是否正确
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "alipay_callback")
    @ResponseBody
    public Object alipayCallback(HttpServletRequest request) {
        Map<String, String> params = Maps.newHashMap();
        //支付宝返回的值都在request中
        Map requestParams = request.getParameterMap();
        for (Iterator iterator = requestParams.keySet().iterator(); iterator.hasNext(); ) {
            String name = (String) iterator.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            int size = values.length;
            for (int i = 0; i < size; i++) {
                valueStr = (i == size - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }

        //验证回调的正确性，是不是支付宝发的，并且避免重复通知
        params.remove("sign_type");
        try {
            boolean alipayRSACheckedV2 = AlipaySignature.rsaCheckV2(params, Configs.getAlipayPublicKey(), "utf-8", Configs.getSignType());
            if (!alipayRSACheckedV2) {
                return ResponseObject.failStatusMessage("非法请求，回调验证不通过！");
            }
        } catch (AlipayApiException e) {

        }
        //TODO 验证回调的参数是否正确

        ResponseObject responseObject = orderService.aliCallback(params);
        if (responseObject.isSuccess()) {
            return Const.AlipayCallback.RESPONSE_SUCCESS;
        }
        return Const.AlipayCallback.RESPONSE_FAILED;
    }
}
