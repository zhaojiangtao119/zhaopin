package com.labelwall.mall.controller;

import com.github.pagehelper.PageInfo;
import com.labelwall.mall.common.ResponseObject;
import com.labelwall.mall.dto.ShopDto;
import com.labelwall.mall.service.IShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2017-12-06.
 */
@RestController
@RequestMapping("/shop/")
public class ShopController {

    @Autowired
    private IShopService shopService;

    /**
     * 获取商铺列表，查询条件（keyword,user_id,category_id,level）
     *
     * @param shopDto
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "get_shop_list/{pageNum}/{pageSize}", method = RequestMethod.POST)
    public ResponseObject<PageInfo> getShopList(ShopDto shopDto,
                                                @PathVariable(value = "pageNum") Integer pageNum,
                                                @PathVariable(value = "pageSize") Integer pageSize) {
        return shopService.getShopList(shopDto, pageNum, pageSize);
    }

    /**
     * 根据id获取商铺详情
     *
     * @param shopId
     * @return
     */
    @RequestMapping(value = "get_shop_detail/{shopId}", method = RequestMethod.GET)
    public ResponseObject<ShopDto> getShopDetail(@PathVariable(value = "shopId") Integer shopId) {
        return shopService.getShopDetail(shopId);
    }
}
