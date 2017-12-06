package com.labelwall.mall.service;

import com.labelwall.mall.common.ResponseObject;
import com.labelwall.mall.dto.ProductDto;

import java.util.List;

/**
 * Created by Administrator on 2017-12-06.
 */
public interface IProductService {

    ResponseObject<List<ProductDto>> getProductList(ProductDto productDto, Integer pageNum, Integer pageSize);

}
