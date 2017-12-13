package com.labelwall.course.dao;

import com.labelwall.course.entity.CourseCollections;

import java.util.List;

public interface CourseCollectionsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CourseCollections record);

    int insertSelective(CourseCollections record);

    CourseCollections selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CourseCollections record);

    int updateByPrimaryKey(CourseCollections record);

    /**
     * 根据userId,classify,objectId查找记录
     *
     * @param courseCollections
     * @return
     */
    List<CourseCollections> selectByCollection(CourseCollections courseCollections);

    /**
     * 取消收藏，物理删除记录
     * @param courseCollections
     * @return
     */
    int delete(CourseCollections courseCollections);
}