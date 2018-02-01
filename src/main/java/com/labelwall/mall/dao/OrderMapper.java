package com.labelwall.mall.dao;

import com.labelwall.mall.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    /**
     * 根据userId,orderNo获取订单
     *
     * @param userId
     * @param orderNo
     * @return
     */
    Order selectByUserIdOrderNo(@Param("userId") Integer userId, @Param("orderNo") Long orderNo);

    /**
     * 获取用户的订单列表
     *
     * @param userId
     * @return
     */
    List<Order> userOrderList(Integer userId);

    /**
     * 通过订单号获取该订单
     *
     * @param orderNo
     * @return
     */
    Order selectByOrderNo(Long orderNo);

    int updateOrderShopping(@Param("orderNo") Long orderNo,
                            @Param("userId") Integer userId,
                            @Param("shoppingId") Integer shoppingId);

    Order validateOrderParam(Long orderNo);
}