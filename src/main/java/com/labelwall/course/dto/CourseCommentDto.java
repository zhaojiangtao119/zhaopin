package com.labelwall.course.dto;

import com.labelwall.course.entity.CourseComment;
import com.labelwall.mall.dto.UserDto;

/**
 * Created by Administrator on 2017-12-13.
 */
public class CourseCommentDto extends CourseComment {

    private UserDto userDto;

    private String createTimeStr;

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }
}
