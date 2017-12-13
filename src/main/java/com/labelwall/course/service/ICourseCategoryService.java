package com.labelwall.course.service;

import com.labelwall.common.ResponseObject;
import com.labelwall.course.dto.CourseCategoryDto;

import java.util.List;

/**
 * Created by Administrator on 2017-12-12.
 */
public interface ICourseCategoryService {
    /**
     * 获取课程分类
     *
     * @param code
     * @return
     */
    ResponseObject<List<CourseCategoryDto>> getCourseCategory(String code);

    /**
     * 获取课程分类的id(递归获取)
     *
     * @param code
     * @return
     */
    ResponseObject<List<Integer>> getCourseCategoryId(String code);
}
