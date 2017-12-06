package com.labelwall.mall.controller;

import com.github.pagehelper.PageInfo;
import com.labelwall.mall.common.ResponseObject;
import com.labelwall.mall.dto.ShopDto;
import com.labelwall.mall.service.IShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @RequestMapping(value = "get_shop_list", method = RequestMethod.POST)
    public ResponseObject<PageInfo> getShopList(ShopDto shopDto,
                                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return shopService.getShopList(shopDto, pageNum, pageSize);
    }

    /**
     * 根据id获取商铺详情
     *
     * @param shopId
     * @return
     */
    @RequestMapping(value = "get_shop_detail", method = RequestMethod.GET)
    public ResponseObject<ShopDto> getShopDetail(@RequestParam(value = "shopId") Integer shopId) {
        return shopService.getShopDetail(shopId);
    }

}
