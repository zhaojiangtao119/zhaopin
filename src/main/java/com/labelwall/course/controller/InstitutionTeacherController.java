package com.labelwall.course.controller;

import com.labelwall.common.ResponseObject;
import com.labelwall.course.entity.Course;
import com.labelwall.course.entity.InstitutionTeacher;
import com.labelwall.course.service.ICourseService;
import com.labelwall.course.service.IInstitutionTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2017-12-29.
 */
@RestController
@RequestMapping("/institution/")
public class InstitutionTeacherController {

    @Autowired
    private IInstitutionTeacherService institutionTeacherService;
    @Autowired
    private ICourseService courseService;

    @RequestMapping(value = "get_teacher/{institutionId}", method = RequestMethod.GET)
    public ResponseObject<List<InstitutionTeacher>> getTeacherByInstitutionId(@PathVariable("institutionId") Integer institutionId) {
        return institutionTeacherService.getTeacherByInstitutionId(institutionId);
    }

    @RequestMapping(value = "teacher_course/{teacherId}", method = RequestMethod.GET)
    public ResponseObject<List<Course>> getCoursesByTeacherId(@PathVariable("teacherId") Integer teacherId) {
        return courseService.getCoursesByTeacherId(teacherId);
    }
}
