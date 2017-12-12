package com.labelwall.mall.service;

import com.labelwall.common.ResponseObject;
import com.labelwall.mall.dto.ProductCategoryDto;

import java.util.List;

/**
 * Created by Administrator on 2017-12-04.
 */
public interface IProductCategoryService {
    /**
     * 获取分类，一级分类与二级分类，
     * @param categoryId
     * @return
     */
    ResponseObject<List<ProductCategoryDto>> getCategoryList(Integer categoryId);

    /**
     * 递归获取该品类下的所有子类的id
     * @param categoryId
     * @return
     */
    ResponseObject<List<Integer>> getCategoryAndChildrenByCategoryId(Integer categoryId);

}
