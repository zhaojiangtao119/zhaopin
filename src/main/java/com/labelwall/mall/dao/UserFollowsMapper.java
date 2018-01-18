package com.labelwall.mall.dao;

import com.labelwall.mall.entity.UserFollows;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserFollowsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserFollows record);

    int insertSelective(UserFollows record);

    UserFollows selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserFollows record);

    int updateByPrimaryKey(UserFollows record);

    /**
     * 通过userId，followsId获取对象
     *
     * @param userId
     * @param followsId
     * @return
     */
    UserFollows selectByUserIdFollowsId(@Param("userId") Integer userId,
                                        @Param("followsId") Integer followsId);

    /**
     * 通过userId获取对象
     *
     * @param userId
     * @return
     */
    List<UserFollows> selectByUserId(Integer userId);
}