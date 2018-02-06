package com.labelwall.mall.dao;

import com.labelwall.mall.entity.TopicCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TopicCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TopicCategory record);

    int insertSelective(TopicCategory record);

    TopicCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TopicCategory record);

    int updateByPrimaryKey(TopicCategory record);

    /**
     * 获取帖子分类(一级分类和二级分类)
     *
     * @param categoryId
     * @return
     */
    List<TopicCategory> getCategory(@Param("categoryId") Integer categoryId);

    /**
     * 根据分类id获取其子类（递归子类）的列表
     *
     * @param categoryId
     * @return
     */
    List<TopicCategory> getCategoryChildrenByParentId(Integer categoryId);

    /**
     * 获取app的首页分类
     *
     * @return
     */
    List<TopicCategory> getAppCategory();
}