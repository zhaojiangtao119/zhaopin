package com.labelwall.course.controller;

import com.labelwall.common.ResponseObject;
import com.labelwall.course.dto.CourseSectionDto;
import com.labelwall.course.service.ICourseSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2017-12-13.
 */
@RestController
@RequestMapping("/section/")
public class CourseSectionController {

    @Autowired
    private ICourseSectionService courseSectionService;

    //组装课程章节，免费课程组装章节，付费课程只组装章
    @RequestMapping(value = "get_section/{id}", method = RequestMethod.GET)
    public ResponseObject<List<CourseSectionDto>> getSection(@PathVariable("id") Integer id) {
        return courseSectionService.getSection(id);
    }

}
