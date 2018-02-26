package com.labelwall.mall.service;

import com.labelwall.common.ResponseObject;
import com.labelwall.mall.dto.ShopDto;
import com.labelwall.mall.dto.UserDto;
import com.labelwall.mall.entity.Shop;
import com.labelwall.mall.entity.ShopServices;
import com.labelwall.mall.entity.TradeType;

import java.util.List;

/**
 * Created by Administrator on 2017-12-06.
 */
public interface IShopService {
    /**
     * 批量获取商铺信息
     *
     * @param shopDto
     * @param shopIdListOnProduct 
     * @param shopIdListOnService 
     * @return
     */
	List<ShopDto> getShopList(ShopDto shopDto, List<Integer> shopIdListOnService, List<Integer> shopIdListOnProduct);

    /**
     * 获取商铺详情
     *
     * @param shopId
     * @return
     */
    ResponseObject<ShopDto> getShopDetail(Integer shopId);

    //获取当前用户的商铺
	ShopDto getShopDetailByUserId(Integer id);
	//获取商铺的地址
	String getlocation(Integer provinceId, Integer cityId, Integer countyId);
	//开 服务类型商铺
	int openServiceShop(Shop shop, ShopServices service, UserDto user);
	//开 交易类型商铺
	int openTradeShop(Shop shop, UserDto user);
	//拿到提供指定服务的所有商店ID
	List<Integer> getShopOnServiceId(Integer serviceId);
	//拿到出售指定商品的所有商店ID
	List<Integer> getShopOnProductId(Integer productId);
	//添加服务
	int insertShopService(ShopServices service);
	//删除商铺中的某件商品
	ResponseObject<String> deletePro(ShopDto shopDetailByUserId, Integer productId);
	//删除商铺中某项服务
	ResponseObject<String> deleteService(ShopDto shopDetailByUserId, Integer serviceId);
	//获得所有交易类型商铺的可选  类别（比如：书店、小卖部、、、、）
	List<TradeType> getAllTradeType();
}
