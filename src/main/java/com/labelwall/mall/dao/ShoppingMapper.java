package com.labelwall.mall.dao;

import com.labelwall.mall.dto.ShoppingDto;
import com.labelwall.mall.entity.Shop;
import com.labelwall.mall.entity.Shopping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShoppingMapper {
    int deleteByPrimaryKey(@Param("userId") Integer userId, @Param("id") Integer id);

    int insert(Shopping record);

    int insertSelective(Shopping record);

    ShoppingDto selectByPrimaryKey(@Param("userId") Integer userId, @Param("id") Integer id);

    int updateByPrimaryKeySelective(Shopping record);

    int updateByPrimaryKey(Shopping record);

    /**
     * 获取收货地址列表，根据用户id
     *
     * @param userId
     * @return
     */
    List<Shopping> getShoppingByUserId(Integer userId);
}