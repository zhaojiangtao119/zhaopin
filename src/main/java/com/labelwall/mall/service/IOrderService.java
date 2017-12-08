package com.labelwall.mall.service;

import com.github.pagehelper.PageInfo;
import com.labelwall.mall.common.ResponseObject;
import com.labelwall.mall.vo.OrderProductVo;
import com.labelwall.mall.vo.OrderVo;

/**
 * Created by Administrator on 2017-12-08.
 */
public interface IOrderService {
    /**
     * 创建一个订单
     *
     * @param id
     * @param shoppingId
     * @return
     */
    ResponseObject createOrder(Integer id, Integer shoppingId);

    /**
     * 取消订单，取消未支付的订单
     * @param id
     * @param orderNo
     * @return
     */
    ResponseObject cancelOrder(Integer id, Long orderNo);

    /**
     * 订单列表
     * @param id
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResponseObject<PageInfo> userOrderList(Integer id, Integer pageNum, Integer pageSize);

    /**
     * 获取订单详情
     * @param id
     * @param orderNo
     * @return
     */
    ResponseObject<OrderVo> getOrderDetail(Integer id, Long orderNo);

    /**
     * 获取订单中的商品明细
     * @param id
     * @return
     */
    ResponseObject<OrderProductVo> getOrderCartProduct(Integer id);
}
