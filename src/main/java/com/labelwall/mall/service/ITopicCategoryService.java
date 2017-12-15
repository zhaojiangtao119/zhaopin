package com.labelwall.mall.service;

import com.labelwall.common.ResponseObject;
import com.labelwall.mall.dto.TopicCategoryDto;

import java.util.List;

/**
 * Created by Administrator on 2017-12-05.
 */
public interface ITopicCategoryService {
    /**
     * 获取帖子的分类（一级分类和二级分类）
     *
     * @param categoryId
     * @return
     */
    ResponseObject<List<TopicCategoryDto>> getCategoryList(Integer categoryId);

    /**
     * 递归获取分类的id（包括其子分类id）
     *
     * @param categoryId
     * @return
     */
    ResponseObject<List<Integer>> getCategoryAndChildrenByCategoryId(Integer categoryId);

}
