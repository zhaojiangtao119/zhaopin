package com.labelwall.mall.dao;

import com.labelwall.mall.dto.ProductDto;
import com.labelwall.mall.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    ProductDto selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    /**
     * 获取商品列表
     *
     * @param productDto
     * @return
     */
    List<ProductDto> getProductList(@Param("productDto") ProductDto productDto,
                                    @Param("categoryIds") List<Integer> categoryIds);

    List<ProductDto> getShopProductList(ProductDto productDto);
}