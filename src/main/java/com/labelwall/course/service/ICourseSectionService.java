package com.labelwall.course.service;

import com.labelwall.common.ResponseObject;
import com.labelwall.course.dto.CourseSectionDto;
import com.labelwall.course.entity.CourseSection;

import java.util.List;

/**
 * Created by Administrator on 2017-12-13.
 */
public interface ICourseSectionService {
    /**
     * 获取课程章节
     *
     * @param courseId
     * @return
     */
    ResponseObject<List<CourseSectionDto>> getSection(Integer courseId);

    /**
     * 根据主键获取对象
     *
     * @param sectionId
     * @return
     */
    CourseSection selectByPrimaryKey(Integer sectionId);

    /**
     * 获取小节的详情
     *
     * @param sectionId
     * @return
     */
    ResponseObject<CourseSection> getSectionDetail(Integer sectionId);
}
