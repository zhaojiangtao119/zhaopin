package com.labelwall.course.service;

import com.labelwall.common.ResponseObject;
import com.labelwall.course.dto.CourseSectionDto;

import java.util.List;

/**
 * Created by Administrator on 2017-12-13.
 */
public interface ICourseSectionService {
    /**
     * 获取课程章节
     *
     * @param id
     * @return
     */
    ResponseObject<List<CourseSectionDto>> getSection(Integer id);
}
