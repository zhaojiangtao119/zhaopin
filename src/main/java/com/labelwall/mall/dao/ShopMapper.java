package com.labelwall.mall.dao;

import com.labelwall.mall.dto.ShopDto;
import com.labelwall.mall.entity.Shop;
import com.labelwall.mall.entity.ShopServices;
import com.labelwall.mall.entity.TradeType;
import org.apache.ibatis.annotations.Param;

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
     * @param shopIdListOnProduct 
     * @param shopIdListOnService 
     * @return
     */
    List<ShopDto> getShopList(@Param("dto") ShopDto shopDto, @Param("shopIdListOnService") List<Integer> shopIdListOnService, @Param("shopIdListOnProduct") List<Integer> shopIdListOnProduct);

	List<Integer> getShopOnServiceId(Integer serviceId);

	List<Integer> getShopOnProductId(Integer productId);

	int addService(ShopServices service);

	List<TradeType> getAllTradeType();

}