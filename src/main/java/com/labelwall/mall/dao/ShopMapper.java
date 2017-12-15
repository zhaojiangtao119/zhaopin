package com.labelwall.mall.dao;

import com.labelwall.mall.dto.ShopDto;
import com.labelwall.mall.entity.Shop;

import java.util.List;

public interface ShopMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shop record);

    int insertSelective(Shop record);

    ShopDto selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shop record);

    int updateByPrimaryKey(Shop record);

    /**
     * 获取商铺列表
     *
     * @param shopDto
     * @return
     */
    List<ShopDto> getShopList(ShopDto shopDto);
}