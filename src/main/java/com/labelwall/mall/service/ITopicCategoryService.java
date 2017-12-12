package com.labelwall.mall.service;

import com.labelwall.common.ResponseObject;
import com.labelwall.mall.dto.TopicCategoryDto;

import java.util.List;

/**
 * Created by Administrator on 2017-12-05.
 */
public interface ITopicCategoryService {

    ResponseObject<List<TopicCategoryDto>> getCategoryList(Integer categoryId);

    ResponseObject<List<Integer>> getCategoryAndChildrenByCategoryId(Integer categoryId);

}
