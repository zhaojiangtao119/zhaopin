package com.labelwall.course.controller;

import com.github.pagehelper.PageInfo;
import com.labelwall.common.ResponseObject;
import com.labelwall.course.dto.CourseCategoryDto;
import com.labelwall.course.dto.CourseDto;
import com.labelwall.course.dto.CourseQueryDto;
import com.labelwall.course.entity.Course;
import com.labelwall.course.service.ICourseCategoryService;
import com.labelwall.course.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2017-12-12.
 */
@RestController()
@RequestMapping("/course/")
public class CourseController {

    @Autowired
    private ICourseCategoryService courseCategoryService;
    @Autowired
    private ICourseService courseService;

    @RequestMapping(value = "get_category", method = RequestMethod.GET)
    public ResponseObject<List<CourseCategoryDto>>
    getCategory(@RequestParam(value = "code", required = false) String code) {
        return courseCategoryService.getCourseCategory(code);
    }

    @RequestMapping(value = "get_category_id", method = RequestMethod.GET)
    public ResponseObject<List<Integer>>
    getCategoryId(@RequestParam(value = "code", defaultValue = "0") String code) {
        return courseCategoryService.getCourseCategoryId(code);
    }

    /**
     * 获取课程首页推荐课程，实战课程，免费课程
     *
     * @param free
     * @return
     */
    @RequestMapping(value = "get_commend_course/{free}", method = RequestMethod.GET)
    public ResponseObject<List<Course>> getCommendCourse(@PathVariable("free") Integer free) {
        return courseService.getCommendCourse(free);
    }

    /**
     * 获取全部课程，默认使用最新排序
     * @param courseQueryDto 搜索条件
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "get_course_list/{pageNum}/{pageSize}", method = RequestMethod.POST)
    public ResponseObject<PageInfo> getCourseList(CourseQueryDto courseQueryDto,
                                              @PathVariable("pageNum") Integer pageNum,
                                              @PathVariable("pageSize") Integer pageSize) {
        return courseService.getCourseList(courseQueryDto, pageNum, pageSize);
    }

    /**
     * 课程详情（课程描述，讲师信息）
     * @param id
     * @return
     */
    @RequestMapping(value = "get_course/{id}",method = RequestMethod.GET)
    public ResponseObject<CourseDto> getCourse(@PathVariable("id")Integer id){
        return courseService.getCourse(id);
    }
}
