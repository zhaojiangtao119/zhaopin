package com.labelwall.mall.dao;

import com.labelwall.mall.dto.ShopCartDto;
import com.labelwall.mall.entity.ShopCart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopCartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShopCart record);

    int insertSelective(ShopCart record);

    ShopCart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShopCart record);

    int updateByPrimaryKey(ShopCart record);

    List<ShopCartDto> getCartItemByUserId(Integer userId);

    /**
     * 判断是否全部选中了商品，0 true,>0 false;
     *
     * @param userId
     * @return
     */
    int selectCartProductCheckedStatusByUserId(Integer userId);

    /**
     * 查询商品是否存在
     *
     * @param userId
     * @param productId
     * @return
     */
    ShopCart selectCartByUserIdProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);

    /**
     * 批量删除选中的商品
     *
     * @param userId
     * @param productIdList
     * @return
     */
    int deleteByUserIdProductIds(@Param("userId") Integer userId, @Param("productIdList") List<String> productIdList);

    /**
     * 勾选与取消勾选
     *
     * @param userId
     * @param productId
     * @param checked
     * @return
     */
    int checkedOrUncheckedProduct(@Param("userId") Integer userId, @Param("productId") Integer productId, @Param("checked") Integer checked);

    /**
     * 获取购物中的商品总数
     *
     * @param userId
     * @return
     */
    int selectCartProductCount(Integer userId);

    /**
     * 获取购物车中被选中的商品
     * @param userId
     * @return
     */
    List<ShopCartDto> selectCheckedCartByUserId(Integer userId);
}