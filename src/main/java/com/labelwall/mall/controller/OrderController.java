package com.labelwall.mall.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.labelwall.common.Const;
import com.labelwall.common.ResponseObject;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.service.IOrderService;
import com.labelwall.mall.vo.OrderProductVo;
import com.labelwall.mall.vo.OrderVo;
import com.labelwall.util.CookieUtil;
import com.labelwall.util.JsonUtil;
import com.labelwall.util.RedisPoolUtil;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2017-12-08.
 */
@RestController
@RequestMapping("/order/")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private IOrderService orderService;

    /**
     * 创建订单
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "create_order", method = RequestMethod.POST)
    public ResponseObject<OrderVo> createOrder(HttpServletRequest request) {
//        UserDto userDto = (UserDto) session.getAttribute(Const.CURRENT_USER);
    	String loginToken=CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json=RedisPoolUtil.get(loginToken);
    	UserDto userDto=JsonUtil.stringToObj(json, UserDto.class);
        return orderService.createOrder(userDto.getId());
    }

    /**
     * 立即购买某一个商品
     *
     * @param request
     * @param productId
     * @param quantity
     * @return
     */
    @RequestMapping(value = "buy", method = RequestMethod.POST)
    public ResponseObject<OrderVo> buyProduct(HttpServletRequest request, Integer productId, Integer quantity) {
    	String loginToken=CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json=RedisPoolUtil.get(loginToken);
    	UserDto userDto=JsonUtil.stringToObj(json, UserDto.class);
        return orderService.buyProduct(userDto.getId(), productId, quantity);
    }

    /**
     * 取消订单
     *
     * @param request
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "cancel_order/{orderNo}", method = RequestMethod.PUT)
    public ResponseObject cancelOrder(HttpServletRequest request,
                                      @PathVariable("orderNo") Long orderNo) {
    	String loginToken=CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json=RedisPoolUtil.get(loginToken);
    	UserDto userDto=JsonUtil.stringToObj(json, UserDto.class);
        return orderService.cancelOrder(userDto.getId(), orderNo);
    }

    /**
     * 获取订单列表
     *
     * @param request
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "user_order_list/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public ResponseObject<PageInfo> userOrderList(HttpServletRequest request,
                                                  @PathVariable(value = "pageNum") Integer pageNum,
                                                  @PathVariable(value = "pageSize") Integer pageSize) {
    	String loginToken=CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json=RedisPoolUtil.get(loginToken);
    	UserDto userDto=JsonUtil.stringToObj(json, UserDto.class);
        return orderService.userOrderList(userDto.getId(), pageNum, pageSize);
    }

    /**
     * 获取订单详情
     *
     * @param request
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "get_order_detail/{orderNo}", method = RequestMethod.GET)
    public ResponseObject<OrderVo> getOrderDetail(HttpServletRequest request,
                                                  @PathVariable("orderNo") Long orderNo) {
    	String loginToken=CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json=RedisPoolUtil.get(loginToken);
    	UserDto userDto=JsonUtil.stringToObj(json, UserDto.class);
        return orderService.getOrderDetail(userDto.getId(), orderNo);
    }

    /**
     * 获取购物车中已经选中的商品详情
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "get_order_cart_product", method = RequestMethod.GET)
    public ResponseObject<OrderProductVo> getOrderCartProduct(HttpServletRequest request) {
    	String loginToken=CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json=RedisPoolUtil.get(loginToken);
    	UserDto userDto=JsonUtil.stringToObj(json, UserDto.class);
        return orderService.getOrderCartProduct(userDto.getId());
    }

    /**
     * 订单支付
     *
     * @param request
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "order_pay")
    public ResponseObject orderPay(HttpServletRequest request,HttpSession session, Long orderNo) {
    	String loginToken=CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json=RedisPoolUtil.get(loginToken);
    	UserDto userDto=JsonUtil.stringToObj(json, UserDto.class);
        String path = session.getServletContext().getRealPath("upload");
        return orderService.orderPay(orderNo, userDto.getId(), path);
    }

    /**
     * 支付宝的回调，扫码付款成功后由支付宝调用，验证回调参数是否正确
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "alipay_callback",method = RequestMethod.POST)
    @ResponseBody
    public Object alipayCallback(HttpServletRequest request) {
        Map<String, String> params = Maps.newHashMap();
        //支付宝返回的值都在request中
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            //获取Key
            String name = (String) iter.next();
            //获取value
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
            //logger.info("支付宝回调，sign:{},trade_status:{},参数:{}", params.get("sign"), params.get("trade_status"), params.toString());
            //logger.info("支付宝回调，sign:{},trade_status:{},参数:{}", params.get("sign"), params.get("trade_status"), params.toString());
        }
        //验证回调的正确性，是不是支付宝发的，并且避免重复通知
        params.remove("sign_type");
        try {
            boolean alipayRSACheckedV2 = AlipaySignature.rsaCheckV2(params, Configs.getAlipayPublicKey(), "utf-8", Configs.getSignType());
            if (!alipayRSACheckedV2) {
                return ResponseObject.failStatusMessage("非法请求，回调验证不通过！");
            }
        } catch (AlipayApiException e) {
            logger.error("支付宝验证回调异常", e);
        }
        //TODO 验证回调的参数是否正确

        ResponseObject responseObject = orderService.aliCallback(params);
        if (responseObject.isSuccess()) {
            return Const.AlipayCallback.RESPONSE_SUCCESS;
        }
        return Const.AlipayCallback.RESPONSE_FAILED;
    }

    /**
     * 查询订单支付状态
     *
     * @param request
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "query_order_pay_status/{orderNo}", method = RequestMethod.GET)
    public ResponseObject<Boolean> queryOrderPayStatus(HttpServletRequest request,
                                                       @PathVariable("orderNo") Long orderNo) {
    	String loginToken=CookieUtil.readLoginToken(request);
    	if(StringUtils.isEmpty(loginToken)){
    		return ResponseObject.failStatusMessage("用户未登录，无法获取用户信息");
    	}
    	String json=RedisPoolUtil.get(loginToken);
    	UserDto userDto=JsonUtil.stringToObj(json, UserDto.class);
        ResponseObject responseObject = orderService.queryOrderPayStatus(userDto.getId(), orderNo);
        if (responseObject.isSuccess()) {
            return ResponseObject.successStautsData(true);
        }
        return responseObject;
    }
}
