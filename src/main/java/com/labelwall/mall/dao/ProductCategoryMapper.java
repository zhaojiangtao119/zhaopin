package com.labelwall.mall.dao;

import com.labelwall.mall.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductCategory record);

    int insertSelective(ProductCategory record);

    ProductCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductCategory record);

    int updateByPrimaryKey(ProductCategory record);

    List<ProductCategory> getCategory(@Param("categoryId")Integer categoryId);

    List<ProductCategory> getCategoryChildrenByParentId(Integer parentId);
}