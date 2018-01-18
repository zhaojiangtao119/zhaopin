package com.labelwall.course.dto;

import com.labelwall.course.entity.Course;
import com.labelwall.course.entity.InstitutionTeacher;
import com.labelwall.mall.dto.UserDto;

/**
 * Created by Administrator on 2017-12-13.
 */
public class CourseDto extends Course {

    private InstitutionTeacher institutionTeacher;

    public InstitutionTeacher getInstitutionTeacher() {
        return institutionTeacher;
    }

    public void setInstitutionTeacher(InstitutionTeacher institutionTeacher) {
        this.institutionTeacher = institutionTeacher;
    }
}
