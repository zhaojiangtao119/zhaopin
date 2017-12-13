package com.labelwall.course.dto;

import com.labelwall.course.entity.Course;
import com.labelwall.mall.dto.UserDto;

/**
 * Created by Administrator on 2017-12-13.
 */
public class CourseDto extends Course {

    private UserDto userDto;

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }
}
