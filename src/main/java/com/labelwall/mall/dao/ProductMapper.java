package com.labelwall.mall.dao;

import com.labelwall.mall.dto.ProductDto;
import com.labelwall.mall.entity.Product;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    ProductDto selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<ProductDto> getProductList(ProductDto productDto);
}