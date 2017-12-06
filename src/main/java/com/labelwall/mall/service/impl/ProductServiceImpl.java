package com.labelwall.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.labelwall.mall.common.ResponseObject;
import com.labelwall.mall.dao.ProductMapper;
import com.labelwall.mall.dto.ProductDto;
import com.labelwall.mall.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017-12-06.
 */
@Service("productService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ResponseObject<List<ProductDto>> getProductList(ProductDto productDto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<ProductDto> productDtoList = productMapper.getProductList(productDto);
        return null;
    }
}
