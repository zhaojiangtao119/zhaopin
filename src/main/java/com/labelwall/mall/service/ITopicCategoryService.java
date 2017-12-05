package com.labelwall.mall.service;

import com.labelwall.mall.common.ResponseObject;
import com.labelwall.mall.dto.ProductCategoryDto;
import com.labelwall.mall.dto.TopicCategoryDto;
import com.labelwall.mall.entity.TopicCategory;

import java.util.List;

/**
 * Created by Administrator on 2017-12-05.
 */
public interface ITopicCategoryService {

    ResponseObject<List<TopicCategoryDto>> getCategoryList(Integer categoryId);

    ResponseObject<List<Integer>> getCategoryAndChildrenByCategoryId(Integer categoryId);

}
