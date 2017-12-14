package com.labelwall.mall.dao;

import com.labelwall.mall.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkUsername(String username);

    User login(@Param("username") String username, @Param("password") String md5Password);

    int checkEmail(String str);

    int restPassword(@Param("userId") Integer id, @Param("passwordNew") String passwordNew);

    User selectByUsername(@Param("username") String username);

    /**
     * id集合获取对象
     *
     * @param userIdList
     * @return
     */
    List<User> selectByUserIds(@Param("userIdList") List<Integer> userIdList);
}