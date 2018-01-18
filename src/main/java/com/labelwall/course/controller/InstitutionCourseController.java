package com.labelwall.course.controller;

import com.labelwall.common.ResponseObject;
import com.labelwall.course.dto.CourseQueryDto;
import com.labelwall.course.entity.Course;
import com.labelwall.course.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2017-12-29.
 */
@RestController
@RequestMapping("/institution/")
public class InstitutionCourseController {

    @Autowired
    private ICourseService courseService;

    /**
     * 获取课程
     *
     * @param courseQueryDto
     * @return
     */
    @RequestMapping(value = "get_course", method = RequestMethod.POST)
    public ResponseObject<List<Course>> getInstitutionCourse(CourseQueryDto courseQueryDto) {
        return courseService.getInstitutionCourse(courseQueryDto);
    }
}
