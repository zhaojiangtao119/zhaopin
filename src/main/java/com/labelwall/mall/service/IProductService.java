package com.labelwall.mall.service;

import com.github.pagehelper.PageInfo;
import com.labelwall.common.ResponseObject;
import com.labelwall.mall.dto.ProductDto;

/**
 * Created by Administrator on 2017-12-06.
 */
public interface IProductService {

    ResponseObject<PageInfo> getProductList(ProductDto productDto, Integer pageNum, Integer pageSize);

    ResponseObject<ProductDto> getProductDetail(Integer productId);
}
