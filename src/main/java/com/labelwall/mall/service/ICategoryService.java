package com.labelwall.mall.service;

import com.labelwall.mall.common.ResponseObject;
import com.labelwall.mall.dto.CategoryDto;
import com.labelwall.mall.entity.Category;

import java.util.List;

/**
 * Created by Administrator on 2017-12-04.
 */
public interface ICategoryService {

    ResponseObject<List<CategoryDto>> getCategoryList(Integer categoryId);

    ResponseObject<List<Integer>> getCategoryAndChildrenByCategoryId(Integer categoryId);

}
