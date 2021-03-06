package com.labelwall.mall.service;

import com.github.pagehelper.PageInfo;
import com.labelwall.common.ResponseObject;
import com.labelwall.mall.entity.Order;
import com.labelwall.mall.vo.OrderProductVo;
import com.labelwall.mall.vo.OrderVo;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-12-08.
 */
public interface IOrderService {
    /**
     * 创建一个订单
     *
     * @param id
     * @return
     */
    ResponseObject createOrder(Integer id);

    /**
     * 取消订单，取消未支付的订单
     *
     * @param id
     * @param orderNo
     * @return
     */
    ResponseObject cancelOrder(Integer id, Long orderNo);

    /**
     * 订单列表
     *
     * @param id
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResponseObject<PageInfo> userOrderList(Integer id, Integer pageNum, Integer pageSize);

    /**
     * 获取订单详情
     *
     * @param id
     * @param orderNo
     * @return
     */
    ResponseObject<OrderVo> getOrderDetail(Integer id, Long orderNo);

    /**
     * 获取订单中的商品明细
     *
     * @param id
     * @return
     */
    ResponseObject<OrderProductVo> getOrderCartProduct(Integer id);

    /**
     * 订单支付
     *
     * @param orderNo
     * @param id
     * @param path
     * @return
     */
    ResponseObject orderPay(Long orderNo, Integer id, String path);

    /**
     * 支付宝回调验证回调参数是否正确，并修改订单的状态，添加支付信息
     *
     * @param params
     * @return
     */
    ResponseObject aliCallback(Map<String, String> params);

    /**
     * 查询订单是否支付
     *
     * @param id
     * @param orderNo
     * @return
     */
    ResponseObject queryOrderPayStatus(Integer id, Long orderNo);

    /**
     * 通过订单号获获取支付宝的订单签名
     * 加签过程
     *
     * @param orderNo
     * @param userId
     * @return
     */
    String appOrderSign(Long orderNo, Integer userId);

    /**
     * 支付宝的异步回调
     * 验签过程
     *
     * @param parameterMap
     * @return
     */
    String alipayCallback(Map parameterMap);

    /**
     * 修改订单的配送信息
     *
     * @param orderNo
     * @param userId
     * @return
     */
    int updateOrderShopping(Long orderNo, Integer userId, Integer shoppingId);

    /**
     * app创建订单
     *
     * @param userId
     * @return
     */
    ResponseObject<OrderVo> createAppOrder(Integer userId);

    /**
     * 立即购买该商品
     *
     * @param id
     * @param productId
     * @param quantity
     * @return
     */
    ResponseObject<OrderVo> buyProduct(Integer id, Integer productId, Integer quantity);

    /**
     * 用户获取订单列表时修改订单状态
     *
     * @param orderList
     * @return
     */
    List<Order> modifyOrderStatus(List<Order> orderList, Integer userId);
}
