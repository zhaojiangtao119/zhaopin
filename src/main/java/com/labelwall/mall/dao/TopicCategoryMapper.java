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

    List<TopicCategory> getCategory(@Param("categoryId")Integer categoryId);

    List<TopicCategory> getCategoryChildrenByParentId(Integer categoryId);
}