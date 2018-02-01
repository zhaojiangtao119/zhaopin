package com.labelwall.mall.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.github.pagehelper.PageInfo;
import com.labelwall.common.AlipayConfig;
import com.labelwall.common.AlipayTradeStatus;
import com.labelwall.common.Const;
import com.labelwall.common.ResponseObject;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.service.IOrderService;
import com.labelwall.mall.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
        return orderService.getUserOrderList(userId, pageNum, pageSize);
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
}
