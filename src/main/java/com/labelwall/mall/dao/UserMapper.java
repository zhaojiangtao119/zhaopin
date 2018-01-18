package com.labelwall.mall.dao;

import com.labelwall.mall.dto.UserDto;
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

    /**
     * 检测用户名的唯一性
     *
     * @param username
     * @return
     */
    int checkUsername(String username);

    /**
     * 登录
     *
     * @param username
     * @param md5Password
     * @return
     */
    User login(@Param("username") String username, @Param("password") String md5Password);

    /**
     * 检测用户邮箱的唯一性
     *
     * @param str
     * @return
     */
    int checkEmail(String str);

    /**
     * 重置密码
     *
     * @param id
     * @param passwordNew
     * @return
     */
    int restPassword(@Param("userId") Integer id, @Param("passwordNew") String passwordNew);

    /**
     * 通过用户名获取用户对象
     *
     * @param username
     * @return
     */
    User selectByUsername(@Param("username") String username);

    /**
     * id集合获取对象
     *
     * @param userIdList
     * @return
     */
    List<User> selectByUserIds(@Param("userIdList") List<Integer> userIdList);

    /**
     * 搜索页面
     *
     * @return
     */
    List<User> selectUser(UserDto userDto);

    /**
     * 使用邮箱登录
     * @param email
     * @param md5Password
     * @return
     */
    User loginEmail(@Param("email") String email, @Param("password") String md5Password);
}