package com.labelwall.course.controller;

import com.labelwall.common.ResponseObject;
import com.labelwall.course.entity.InstitutionCourseware;
import com.labelwall.course.service.IInstitutionCoursewareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2017-12-29.
 */
@RestController
@RequestMapping("/institution/")
public class InstitutionCoursewareController {

    @Autowired
    private IInstitutionCoursewareService institutionCoursewareService;

    @RequestMapping(value = "get_courseware/{institutionId}", method = RequestMethod.GET)
    public ResponseObject<List<InstitutionCourseware>>
    getCoursewareByInstitutionId(@PathVariable("institutionId") Integer institutionId) {
        return institutionCoursewareService.getCoursewareByInstitutionId(institutionId);
    }

}
