package com.labelwall.mall.service;

import com.github.pagehelper.PageInfo;
import com.labelwall.common.ResponseObject;
import com.labelwall.mall.dto.ShopDto;

/**
 * Created by Administrator on 2017-12-06.
 */
public interface IShopService {
    /**
     * 批量获取商铺信息
     * @param shopDto
     * @return
     */
    ResponseObject<PageInfo> getShopList(ShopDto shopDto,Integer pageNum,Integer PageSize);

    /**
     * 获取商铺详情
     * @param shopId
     * @return
     */
    ResponseObject<ShopDto> getShopDetail(Integer shopId);
}
