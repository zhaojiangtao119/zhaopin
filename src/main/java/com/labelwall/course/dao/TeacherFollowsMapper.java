package com.labelwall.course.dao;

import com.labelwall.course.entity.TeacherFollows;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeacherFollowsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TeacherFollows record);

    int insertSelective(TeacherFollows record);

    TeacherFollows selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TeacherFollows record);

    int updateByPrimaryKey(TeacherFollows record);

    /**
     * 判断当前用户是否关注了当前课程的教师
     *
     * @param currentUserId
     * @param userId
     * @return
     */
    TeacherFollows isFollows(@Param("currentUserId") Integer currentUserId, @Param("userId") Integer userId);

    /**
     * 获取关注的用户id集合
     *
     * @param userId
     * @return
     */
    List<TeacherFollows> selectByUserId(@Param("userId") Integer userId);
}