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

    /**
     * 获取分类(一级分类和二级分类)
     *
     * @param categoryId
     * @return
     */
    List<ProductCategory> getCategory(@Param("categoryId") Integer categoryId);

    /**
     * 获取分类id(递归获取子分类)
     *
     * @param parentId
     * @return
     */
    List<ProductCategory> getCategoryChildrenByParentId(Integer parentId);
}