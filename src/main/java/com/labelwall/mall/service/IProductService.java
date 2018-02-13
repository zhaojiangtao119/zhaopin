package com.labelwall.mall.service;

import com.github.pagehelper.PageInfo;
import com.labelwall.common.ResponseObject;
import com.labelwall.mall.dto.ProductDto;

/**
 * Created by Administrator on 2017-12-06.
 */
public interface IProductService {
    /**
     * 获取商品列表（搜索条件：keyword/classify/subClassify/price...）
     *
     * @param productDto
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResponseObject<PageInfo> getProductList(ProductDto productDto, Integer pageNum, Integer pageSize);

    /**
     * 获取产品详情
     *
     * @param productId
     * @return
     */
    ResponseObject<ProductDto> getProductDetail(Integer productId);

    int add(ProductDto dto);
}
