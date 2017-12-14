package com.labelwall.course.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.labelwall.common.CourseConst;
import com.labelwall.common.ResponseObject;
import com.labelwall.common.ResponseStatus;
import com.labelwall.course.dao.CourseSectionMapper;
import com.labelwall.course.dto.CourseSectionDto;
import com.labelwall.course.entity.Course;
import com.labelwall.course.entity.CourseSection;
import com.labelwall.course.service.ICourseCategoryService;
import com.labelwall.course.service.ICourseSectionService;
import com.labelwall.course.service.ICourseService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-12-13.
 */
@Service("courseSectionService")
public class CourseSectionServiceImpl implements ICourseSectionService {

    @Autowired
    private CourseSectionMapper courseSectionMapper;
    @Autowired
    private ICourseService courseService;

    @Override
    public ResponseObject<List<CourseSectionDto>> getSection(Integer courseId) {
        Course course = courseService.selectByPrimaryKey(courseId);
        if (course == null) {
            ResponseObject.failStatusMessage(ResponseStatus.ERROR_PARAM.getValue());
        }

        List<CourseSection> courseSectionList = courseSectionMapper.selectByCourseId(course.getId());
        if (CollectionUtils.isEmpty(courseSectionList)) {
            return ResponseObject.failStatusMessage("获取失败");
        }
        Map<Integer, CourseSectionDto> courseSectionDtoMap = Maps.newHashMap();
        List<CourseSectionDto> courseSectionDtoList = Lists.newArrayList();
        if (course.getFree() == CourseConst.CourseType.free) {//免费课程,组装章节
            for (CourseSection courseSection : courseSectionList) {
                if (courseSection.getParentId() == 0) {
                    CourseSectionDto courseSectionDto = new CourseSectionDto();
                    BeanUtils.copyProperties(courseSection, courseSectionDto);
                    courseSectionDtoMap.put(courseSection.getId(), courseSectionDto);
                } else {
                    courseSectionDtoMap.get(courseSection.getParentId()).getCourseSubSectionList().add(courseSection);
                }
            }
        } else if (course.getFree() == CourseConst.CourseType.charges) {
            for (CourseSection courseSection : courseSectionList) {
                if (courseSection.getParentId() == 0) {
                    CourseSectionDto courseSectionDto = new CourseSectionDto();
                    BeanUtils.copyProperties(courseSection, courseSectionDto);
                    courseSectionDtoMap.put(courseSection.getId(), courseSectionDto);
                }
            }
        } else {
            return ResponseObject.failStatusMessage("服务器出错");
        }
        for (Map.Entry<Integer, CourseSectionDto> entry : courseSectionDtoMap.entrySet()) {
            courseSectionDtoList.add(entry.getValue());
        }
        return ResponseObject.successStautsData(courseSectionDtoList);
    }

    @Override
    public CourseSection selectByPrimaryKey(Integer sectionId) {
        return courseSectionMapper.selectByPrimaryKey(sectionId);
    }
}
